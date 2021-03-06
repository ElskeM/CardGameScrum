import Vue from "vue";
import VueRouter from "vue-router";
import AuthService from "../services/auth.service";
import backend from "../services/backend";

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
    beforeEnter: (to, from, next) => authenticate(to, from, next),
  },
  {
    path: "/game/:id",
    name: "GameId",
    component: () => import(/* webpackChunkName: "game" */ "../views/Game.vue"),
    beforeEnter: (to, from, next) => authenticate(to, from, next),
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
  },
];

const router = new VueRouter({
  mode: "history",
  routes,
});

router.beforeEach((to, from, next) => {
  Vue.toasted.clear();
  isServerUp(to, from, next)
    .then(setUserName)
    .catch(() => {
      if (AuthService.isLoggedIn()) {
        AuthService.logout();
        next("/");
      }
    });
});

async function isServerUp(to, from, next) {
  if (to.name != "server-down") {
    let connected = await backend.isConnected();
    if (!connected) {
      next({ path: "/server-down" });
      return;
    }
  }

  next();
}

function authenticate(to, from, next) {
  if (!AuthService.isLoggedIn()) {
    next({ name: "Login", query: { from: to.path } });
    Vue.toasted.info("Var god logga in först");
  } else {
    next();
  }
}

//hämtar username ifrån vårt api och sätter detta i vuex store
async function setUserName() {
  //if user is not logged in, don't request the username from backend
  if (!AuthService.isLoggedIn()) {
    return;
  }
  return await AuthService.fetchLoggedInUser();
}

export default router;
