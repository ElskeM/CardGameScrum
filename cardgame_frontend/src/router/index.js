import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import NewCard from '../views/NewCard.vue'
import AuthService from '../services/auth.service'
import axios from 'axios'
//import axios from "axios";

Vue.use(VueRouter)


const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/newcard',
    name: 'New card',
    component: NewCard
  },
  {
    path: '/game',
    name: 'Game',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Game.vue')
  },
  {
    path: '/game/:id',
    name: 'GameId',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Game.vue')
  },
  {
    path: '/singlecard/:url',
    name: 'SingleCard',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/SingleCardView.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/ForgotPassword',
    name: 'Forgot Password',
    component: () => import('../views/ForgotPassword.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
    beforeEnter:(to, from, next) => {
      if(from.name != "Login" && AuthService.isLoggedIn()){
        AuthService.logout();
      }
      next();
    }
  },
  {
    path: '/forgot-password',
    name: 'forgot-password',
    component: () => import('../views/ForgotPassword.vue')
  },
  {
    path: '/server-down',
    name: 'server-down',
    component: () => import('../views/ServerDown.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

/*
router.beforeEach((to,from,next) => {
  fetch('http://localhost:8080/status')
  .then(res => {
    if(res.status === 200) {
      next()}
    })
  .catch(() => {
    next({path: '/server-down'})
})
})
*/

router.beforeEach((to, from, next) => {
  axios.get('http://localhost:8080/status')
  .then(res => {
    /*if(res.status === 200) {
      next()}*/
      console.log(res)
      next()
    })
  .catch(() => {
    next({path: '/server-down'})
})})

/*
router.beforeEach((to,from,next) => {
  hej(to, from, next)
}) 
*/

/*
async function hej(to, from , next) {
  await axios
  .get('http://localhost:8080/status')
  .catch(error => {
    next({path: '/server-down'})
    console.log(error)
    }
  )

  next()
} 
*/

export default router
