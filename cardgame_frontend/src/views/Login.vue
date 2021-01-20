<template>
  <div class="overall">
    <div id="login">
      <form>
        <fieldset>
          <legend>Login</legend>
          <input
            type="text"
            name="username"
            v-model="user.username"
            placeholder="Username"
            autocomplete="username"
          />
          <input
            type="password"
            name="password"
            v-model="user.password"
            placeholder="Password"
            autocomplete="current-password"
          />
          <button type="button" v-on:click="login()">Login</button>
          <button
            type="button"
            onclick="window.location.href='/ForgotPassword'"
          >
            Forgot Password?
          </button>
        </fieldset>
      </form>
    </div>
  </div>
</template>

<script>
import AuthService from "../services/auth.service";
import User from "../models/User";

export default {
  name: "Login",
  data() {
    return {
      user: new User("", ""),
    };
  },
  methods: {
    login() {
        AuthService.login(this.user).then((res) => {
        this.$store.commit('addUser', {username: res.user.username, email: res.user.email})
        this.$router.push("/")
          });
          
          
    },
  },
};
</script>

<style scoped>
.overall {
  margin-top: 30px;
}
legend {
  font-size: 18px;
}
form {
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: Arial, Helvetica, sans-serif;
  padding: 30px;
}
input,
button {
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin: 2px;
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>