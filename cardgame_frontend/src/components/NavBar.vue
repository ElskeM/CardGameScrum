<template>
  <div>
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link> |
      <router-link to="/game">Game</router-link> |
      <span v-if="!isLoggedIn()">
        <router-link to="/register">Register</router-link> |
        <router-link to="/login">Login</router-link>
      </span>
      <router-link v-else to="/login">Inloggad som {{user.username}} </router-link>
    </div>
    <router-view />
  </div>
</template>

<script>
import AuthService from "../services/auth.service";
import { mapGetters } from "vuex";



export default {

  computed: mapGetters(["user"]),
  methods: {
    isLoggedIn() {
      return AuthService.isLoggedIn();
    }
  }
};
</script>
