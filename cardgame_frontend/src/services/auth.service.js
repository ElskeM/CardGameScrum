//https://bezkoder.com/jwt-vue-vuex-authentication/#Flow_for_User_Registration_and_User_Login

import axios from "axios";
import backend from "./backend";
import authHeader from "./auth-header";
import Vue from "vue";
import store from "../store";

const LOGIN_URL = backend.ROOT_URL + "/authenticate";
const REGISTER_URL = backend.ROOT_URL + "/newPlayer";
const USER_URL = backend.ROOT_URL + "/user";

class AuthService {
  async fetchLoggedInUser() {
    try {
      const res = await axios
        .get(USER_URL, {
          headers: authHeader(),
        });
      store.commit("addUser", { username: res.data.username });
    } catch (err) {
      if (err.response.status === 403) {
        this.logout();
        throw Error("Unauthorized");
      }
    }
  }

  isLoggedIn() {
    let user = JSON.parse(localStorage.getItem("user"));
    if (user && user.token) {
      return true;
    }
    return false;
  }

  login(user) {
    return axios
      .post(LOGIN_URL, {
        username: user.username,
        password: user.password,
      })
      .then((resp) => {
        console.log(resp);
        if (resp.data.token) {
          localStorage.setItem("user", JSON.stringify(resp.data));
          store.commit("addUser", resp.data.user);
        }
        return resp.data;
      });
  }

  logout() {
    localStorage.removeItem("user");
    Vue.toasted.success("Du är utloggad!");
  }

  register(user) {
    return axios.post(REGISTER_URL, user).then((resp) => {
      if (resp.status === 201) {
        return "SUCCESS";
      } else {
        throw new Error("REGISTRATION FAILURE");
      }
    });
  }
}

export default new AuthService();
