import Vue from "vue";
import VueRouter from "vue-router";
import AuthService from "../services/auth.service";
import axios from "axios";
import authHeader from "../services/auth-header";
import store from '../store/index'




Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import(/* webpackChunkName: "card" */ "../views/Home.vue"),
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
  {
    path: "/newcard",
    name: "New card",
    component: () =>
      import(/* webpackChunkName: "admin" */ "../views/NewCard.vue"),
  },
  {
    path: "/game",
    name: "Game",
    component: () => import(/* webpackChunkName: "game" */ "../views/Game.vue"),
    beforeEnter: (to, from, next) => authenticate(to,from,next),
  },
  {
    path: "/game/:id",
    name: "GameId",
    component: () => import(/* webpackChunkName: "game" */ "../views/Game.vue"),
    beforeEnter: (to, from, next) => authenticate(to,from,next),
  },
  {
    path: "/singlecard/:url",
    name: "SingleCard",
    component: () =>
      import(/* webpackChunkName: "card" */ "../views/SingleCardView.vue"),
  },
  {
    path: "/register",
    name: "Register",
    component: () =>
      import(/* webpackChunkName: "register" */ "../views/Register.vue"),
  },
  {
    path: "/login",
    name: "Login",
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/Login.vue"),
    beforeEnter: (to, from, next) => {
      if (from.name != "Login" && AuthService.isLoggedIn()) {
        AuthService.logout();
        Vue.toasted.success("You have been logged out!");
      }
      next();
    },
  },
  {
    path: "/forgot-password",
    name: "forgot-password",
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/ForgotPassword.vue"),
  },
  {
    path: "/server-down",
    name: "server-down",
    component: () =>
      import(/* webpackChunkName: "error" */ "../views/ServerDown.vue"),
  },
  {
    path: "*",
    name: "not found",
    component: () => import("../views/PageNotFound.vue"),
  }


];

const router = new VueRouter({
  mode: "history",
  routes,
});

router.beforeEach((to, from, next) => {
  Vue.toasted.clear();
  isServerUp(to, from, next)
  .then(
    setUserName
  );
});

async function isServerUp(to, from, next) {
  if (to.name != "server-down") {
    await axios
      .get("http://localhost:8080/status", { timeout: 1000 })
      .catch(() => {
        next({ path: "/server-down" });
      });
  }

  next();
}

function authenticate(to,from,next) {
  if (!AuthService.isLoggedIn()) {
    next("/login");
    Vue.toasted.info("Please log in");
  } else {
    next();
  }
}

//hämtar username ifrån vårt api och sätter detta i vuex store
async function setUserName() {
  await axios.get("http://localhost:8080/user", {
    headers: authHeader(),
  })
  .then(res => {
    store.commit('addUser', {username: res.data.username})
  })
}

export default router;
