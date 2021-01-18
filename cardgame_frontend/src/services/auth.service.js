//https://bezkoder.com/jwt-vue-vuex-authentication/#Flow_for_User_Registration_and_User_Login

import axios from "axios";

const LOGIN_URL = "http://localhost:8080/authenticate";
const REGISTER_URL = "http://localhost:8080/newPlayer";

class AuthService {
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
        if (resp.data.token) {
          localStorage.setItem("user", JSON.stringify(resp.data));
        }
        return resp.data;
      });
  }

  logout() {
    localStorage.removeItem("user");
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
