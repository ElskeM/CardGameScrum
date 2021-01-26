<template>
  <div class="overall">
    <div class="register">
      <div class="status">
        <span v-if="showStatus">{{ status }}</span>
      </div>
      <ValidationObserver v-slot="{ invalid }">
        <form @submit.prevent="register">
          <fieldset>
            <legend>Registera</legend>
            <div>
              <ValidationProvider
                v-slot="{ classes, errors }"
                rules="required"
                :customMessages="messages"
              >
                <label>Användarnamn</label><br />
                <input
                  name="username"
                  type="text"
                  placeholder="Användarnamn"
                  v-model="user.userName"
                  :class="classes"
                />
                <div class="error">{{ errors[0] }}</div>
              </ValidationProvider>
            </div>
            <div>
              <ValidationProvider
                mode="eager"
                v-slot="{ classes, errors }"
                rules="required|email"
                :customMessages="messages"
              >
                <label>Mejladress</label><br />
                <input
                  name="email"
                  type="email"
                  placeholder="Mejladress"
                  v-model="user.email"
                  :class="classes"
                />
                <div class="error">{{ errors[0] }}</div>
              </ValidationProvider>
            </div>
            <div>
              <ValidationProvider
                mode="eager"
                v-slot="{ classes, errors }"
                rules="required|min:8"
                :customMessages="messages"
              >
                <label>Lösenord</label><br />
                <input
                  name="password"
                  type="password"
                  placeholder="Lösenord"
                  v-model="user.password"
                  :class="classes"
                />
                <div class="error">{{ errors[0] }}</div>
              </ValidationProvider>
            </div>
            <br />
            <button type="submit" :disabled="invalid || isProcessing">
              Registrera
            </button>

            <p class="forgot-password text-right">
              Redan registrerad - 
              <router-link to="/login">logga in?</router-link>
            </p>
          </fieldset>
        </form>
      </ValidationObserver>
    </div>
  </div>
</template>

<script>
import User from "../models/User";
import AuthService from "../services/auth.service";
import {ValidationObserver, ValidationProvider} from 'vee-validate';
import { extend } from "vee-validate";
import { required, email,min } from "vee-validate/dist/rules";

extend('required', required);
extend('email', email);
extend('min', min);

export default {
  name: "register",
  components: {
    ValidationObserver,
    ValidationProvider
  },
  data() {
    return {
      user: new User("", ""),
      showStatus: false,
      status: "",
      isProcessing: false,
      messages: {required: "Fältet är obligatoriskt",
                 email: "Ogiltig mejladress", 
                 min: "Lösenordet måste bestå av minst 8 tecken"},
    }
  },
  methods: {
    register() {
      this.isProcessing = true;
      this.status = "Registrerar...";
      this.showStatus = true;
      AuthService.register(this.user)
        .then(() => {
          this.status = "Klar. Du kan nu logga in";
        })
        .catch(() => {
          this.status = "Registreringen misslyckades";
          setTimeout(this.reset, 4000);
        });
    },
    reset() {
      this.showStatus = false;
      this.isProcessing = false;
    }
  }
};
</script>

<style scoped>
.overall {
  margin-top: 20px;
}
legend {
  font-size: 18px;
}
input {
  width: 97%;
  padding: 5px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
  resize: vertical;
  align-content: center;
}
label {
  padding: 12px 12px 12px 0;
  display: inline-block;
}
label:after {
  content: "";
  display: table;
  clear: both;
}
input[type="submit"]:hover {
  background-color: #45a049;
}

button {
  font-size: inherit;
  padding: 5px;
}

.register {
  box-sizing: border-box;
  -moz-box-sizing: border-box;
  -webkit-box-sizing: border-box;
  align-content: center;
  border-radius: 4px;
  padding: 30px;
  width: 25%;
  margin: auto;
}

.status {
  box-sizing: border-box;
  width: 100%;
  height: 1rem;
}

input.valid {
  color: #045929;
  border: 1px solid #045929;
}
input.invalid {
  color: #eb0600;
  border: 1px solid #eb0600;
}
.error {
  display: block;
  height: 1rem;
  color: crimson;
}
</style>
