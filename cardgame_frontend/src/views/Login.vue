<template>
<div class="center-text center-div">
<div id="login">
    <h1>Login</h1>
    <input type="text" name="username" v-model="user.username" placeholder="Username" />
    <input type="password" name="password" v-model="user.password" placeholder="Password" />
    <button type="button" v-on:click="login()">Login</button>
    <button type="button" onclick="window.location.href='/ForgotPassword'">Forgot Password?</button>
</div></div>
</template>

<script>
import AuthService from '../services/auth.service';
import User from '../models/User';

export default {
    name: 'Login',
    data() {
        return {
            user: new User("","")
        }
    },
    methods: {
        login() {
            AuthService.login(this.user).then(() => this.$router.push("/"));
        }
    }
    
}
</script>

<style scoped>


</style>