<template>
  <div class="overall">
    <div id="login">
      <form @submit="login">
        <fieldset>
          <legend>Login</legend>
          <span>
            <ValidationProvider v-slot="v" rules="required">
              <input
                type="text"
                name="username"
                v-model="user.username"
                placeholder="Username"
                autocomplete="username"
              />
              <div class="error">{{ v.errors[0] }}</div>
            </ValidationProvider>
          </span>
          <span>
            <ValidationProvider v-slot="v" rules="required">
              <input
                type="password"
                name="password"
                v-model="user.password"
                placeholder="Password"
                autocomplete="current-password"
              />
              <div class="error">{{ v.errors[0] }}</div>
            </ValidationProvider>
          </span>
          <button type="submit">Login</button>
          <router-link to="/forgot-password">
            <button type="button">
              Forgot Password?
            </button>
          </router-link>
        </fieldset>
      </form>
    </div>
  </div>
</template>

<script>
import AuthService from "../services/auth.service";
import User from "../models/User";
import { ValidationProvider } from "vee-validate";
import { extend } from "vee-validate";
import { required } from "vee-validate/dist/rules";

extend("required", required);

export default {
  name: "Login",
  components: {
    ValidationProvider,
  },
  data() {
    return {
      user: new User("", ""),
    };
  },
  methods: {
    login: function(e) {
      AuthService.login(this.user)
        .then((res) => {
          this.$store.commit(
            "addUser",
            res.user
            //{username: res.user.username, email: res.user.email}
          );
          this.$router.push("/");
          this.$toasted.success("You have been logged in!");
        })
        .catch(() =>
          this.$toasted.error("Login failed. Are your credentails correct?")
        );
      e.preventDefault();
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
  padding: 30px;
}
.error {
  display: block;
  height: 1rem;
  color: crimson;
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
