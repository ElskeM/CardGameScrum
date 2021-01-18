<template>
<div class="register">
    <form>
        <h1>Sign Up</h1>
        <div class="form">
            <label>Username</label><br>
            <input type="text">
        </div>
        <div class="form">
            <label>Email address</label><br>
            <input type="email">
        </div>
        <div class="form">
            <label>Password</label><br>
            <input type="password">
        </div><br>
        <button type="submit">Sign Up</button>
        
        <p class="forgot-password text-right">
            Already registered
            <router-link to="/login">sign in?</router-link>
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
* {
    box-sizing: border-box;
    border-radius: 5px;
    color: black;
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

</style>