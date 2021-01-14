<template>
<div id="login">
    <h1>Login</h1>
    <input type="text" name="username" v-model="user.username" placeholder="Username" />
    <input type="password" name="password" v-model="user.password" placeholder="Password" />
    <button type="button" v-on:click="login()">Login</button>
</div>
</template>

<script>
import AuthService from '../services/auth.service';
import User from '../models/User';
import NavBar from '../components/NavBar';

export default {
    name: 'Login',
    data() {
        return {
            user: new User("","")
        }
    },
    methods: {
        login() {
            AuthService.login(this.user);
            this.$router.push("/");
            NavBar.update();
        }
    }
    
}
</script>

<style scoped>


</style>