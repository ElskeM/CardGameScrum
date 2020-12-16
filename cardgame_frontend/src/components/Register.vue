<template>
<div class="register">
    <form>
        <h3>Sign Up</h3>
        <div class="form">
            <label>Username</label>
            <input type="text">
        </div>
        <div class="form">
            <label>Email address</label>
            <input type="email">
        </div>
        <div class="form">
            <label>Password</label>
            <input type="password">
        </div>
        <button type="submit">Sign Up</button>
        
        <p class="forgot-password text-right">
            Already registered
            <router-link :to="{name: 'login}'}">sign in?</router-link>
        </p>
    </form>
</div>
</template>

<script>
export default {
    name: 'register',
    components: {

    }
}
</script>

<style scoped>

</style>