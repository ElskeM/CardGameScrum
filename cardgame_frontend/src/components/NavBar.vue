<template>
  <div>
    <div id="nav" v-show="!hidden">
      <router-link to="/">Hem</router-link> |
      <router-link to="/about">Om Klimatkoll</router-link> |
      <router-link to="/game">Spel</router-link> |
      <span v-if="!isLoggedIn()">
        <router-link to="/register">Registera</router-link> |
        <router-link to="/login">Logga in</router-link>
      </span>
      <router-link v-else to="/login">
        Inloggad som {{ user.username }} (logga ut)
      </router-link>
    </div>
    <router-view @hide="$emit('hide')" />
  </div>
</template>

<script>
import AuthService from "../services/auth.service";
import { mapGetters } from "vuex";

export default {
  computed: mapGetters(["user"]),
  props: {
    hidden: Boolean
  },
  methods: {
    isLoggedIn() {
      return AuthService.isLoggedIn();
    },
  },
};
</script>
