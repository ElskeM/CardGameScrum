<template>
<div>
  <div id="nav">
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link> |
    <router-link to="/game">Game</router-link> |
    <router-link to="/newcard">New Card</router-link> |
    <router-link to="/register">Register</router-link> |
    <router-link v-if="!isLoggedIn()" to="/login">Login</router-link>
    <router-link v-else to="/login">Logout</router-link>
  </div>
  <router-view />
</div>
</template>

<script>
import AuthService from '../services/auth.service';

export default {
  methods: {
    isLoggedIn (){
      return AuthService.isLoggedIn();
    },
    update(){
      this.$forceUpdate();
    }
  }
}
</script>