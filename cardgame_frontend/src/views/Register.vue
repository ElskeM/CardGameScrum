<template>
<div class="register">
  <div class="status">
    <span v-if="showStatus">{{status}}</span>
  </div>
    <form @submit.prevent = "register">
        <h1>Sign Up</h1>
        <div class="form">
            <label>Username</label><br>
            <input type="text" v-model="user.userName">
        </div>
        <div class="form">
            <label>Email address</label><br>
            <input type="email" v-model="user.email">
        </div>
        <div class="form">
            <label>Password</label><br>
            <input type="password" v-model="user.password">
        </div><br>
        <button :disabled="isProcessing" type="submit">Sign Up</button>
        
        <p class="forgot-password text-right">
            Already registered
            <router-link to="/login">sign in?</router-link>
        </p>
    </form>
</div>
</template>

<script>
import User from '../models/User';
import AuthService from '../services/auth.service';

export default {
    name: 'register',
    data() {
        return {
            user: new User("",""),
            showStatus: false,
            status: "",
            isProcessing: false
        }
    },
    methods: {
      register(){

        this.isProcessing = true;
        this.status = "Registrerar...";
        this.showStatus = true;
        AuthService.register(this.user).then(()=>{
          this.status = "Klar. Du kan nu logga in"
        })
        .catch(()=>{
          this.status = "Registreringen misslyckades";
          setTimeout(this.reset, 4000);
        });
      },
      reset(){
        this.showStatus = false;
        this.isProcessing = false;
      }
    }
}
</script>

<style scoped>
* {
    box-sizing: border-box;
    border-radius: 5px;
}
input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  resize: vertical;
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
input[type=submit]:hover {
  background-color: #45a049;
}

.register {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
  width: 25%;
}

.status {
  box-sizing: border-box;
  width: 100%;
  height: 1rem;
}

</style>