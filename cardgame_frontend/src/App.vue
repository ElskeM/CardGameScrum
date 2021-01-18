<template>
  <div id="app">
    <div id="header">
      <a href="/">
      <img src="./assets/header.png">
      </a>
    </div>
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link> |
      <router-link to="/game">Game</router-link> |
      <router-link to="/newcard">New Card</router-link> |
      <router-link to="/register">Register</router-link> |
      <router-link to="/login">Login</router-link>
    </div>
    <router-view />
  </div>
</template>

<style>
#header {
  text-align: center;
}
#header img {
  width: 20em;
}
#nav {
  font-family: Arial, Helvetica, sans-serif;
  background: #1d1f48;
  color: #f4efec;
  text-align: center;
}
a {
  color: #f4efec;
}
#app {
  color: #f4efec;
}
html {
  height: 100%;
  min-height: 100%;
  background: #f4efec;
}
body {
  min-height: 98%;
  background: #1d1f48;
}
/*
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}*/
</style>
