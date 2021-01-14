//https://bezkoder.com/jwt-vue-vuex-authentication/#Flow_for_User_Registration_and_User_Login

import axios from "axios";

const API_URL = "http://localhost:8080/authenticate";

class AuthService {

    isLoggedIn(){
        let user = JSON.parse(localStorage.getItem('user'));
        if (user && user.token){
            return true;
        }
        return false;
    }

    login(user) {
        return axios
            .post(API_URL, {
                username: user.username,
                password: user.password
            })
            .then(resp => {
                if (resp.data.token) {
                    localStorage.setItem("user", JSON.stringify(resp.data));
                }
                return resp.data;
            });
    }

    logout() {
        localStorage.removeItem("user");
    }
}

export default new AuthService();