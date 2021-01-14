<template>
<div>
  <div id="nav">
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link> |
    <router-link to="/game">Game</router-link> |
    <router-link to="/newcard">New Card</router-link> |
    <router-link to="/register">Register</router-link> |
    <router-link to="/login">{{loginLinkText}}</router-link>
  </div>
  <router-view />
</div>
</template>

<script>
import AuthService from '../services/auth.service';

export default {
  computed: {
      loginLinkText: function () {
          if(AuthService.isLoggedIn()){
              return "Logout";
          }
          return "Login";
      }
  },
  methods: {
    update (){
      this.$forceUpdate();
    }
  }
}
</script>