<template>
  <div class="overall">
    <div id="login">
      <ValidationObserver v-slot="{invalid}">
      <form @submit="login">
        <fieldset>
          <legend>Login</legend>
          <div class="flex">
            <span>
              <ValidationProvider v-slot="{classes, errors}" rules="required">
                <input
                  type="text"
                  :class="classes"
                  name="username"
                  v-model="user.username"
                  placeholder="Username"
                  autocomplete="username"
                />
                <div class="error">{{ errors[0] }}</div>
              </ValidationProvider>
            </span>
            <span>
              <ValidationProvider v-slot="{classes, errors}" rules="required">
                <input
                  type="password"
                  :class="classes"
                  name="password"
                  v-model="user.password"
                  placeholder="Password"
                  autocomplete="current-password"
                />
                <div class="error">{{ errors[0] }}</div>
              </ValidationProvider>
            </span>
            <span>
              <button type="submit" :disabled="invalid">Login</button>
            </span>
            <router-link to="/forgot-password">
              <button type="button">
                Forgot Password?
              </button>
            </router-link>
          </div>
        </fieldset>
      </form>
      </ValidationObserver>
    </div>
  </div>
</template>

<script>
import AuthService from "../services/auth.service";
import User from "../models/User";
import { ValidationProvider, ValidationObserver } from "vee-validate";
import { extend } from "vee-validate";
import { required } from "vee-validate/dist/rules";

extend("required", required);

export default {
  name: "Login",
  components: {
    ValidationProvider,
    ValidationObserver
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
          if(this.$route.query.from){
            this.$router.replace(this.$route.query.from);
          } else {
            this.$router.replace("/");
          }
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
  margin: auto;
  width: fit-content;
  justify-content: center;
  padding: 30px;
}
.flex {
  display: flex;
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
  margin: 5px;
  margin-top: 10px;
  margin-bottom: 10px;
}
input.valid {
  color: #045929;
  border: 1px solid #045929;
}
input.invalid {
  color: #EB0600;
  border: 1px solid #EB0600;
}
</style>
