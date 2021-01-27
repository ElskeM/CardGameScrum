<template>
  <div id="app">
    <div id="header" v-bind:class="{ invisibility: hideNavBar }">
      <a href="/">
        <img src="./assets/header.png"/>
      </a>
    </div>
    <NavBar v-bind:class="{ invisibility: hideNavBar }"/>
     <router-view @hide="switchVisibility"/>
  </div>
</template>
<script>
import NavBar from "@/components/NavBar.vue";

export default {
  name: "test",
  components: {
    NavBar,
  },

  data() {
    return {
      hideNavBar: false
    }
  },
 methods: {
    switchVisibility() {
      this.hideNavBar = !this.hideNavBar
    }

  }
};
</script>
<style>
* {
  font-family: Arial, Helvetica, sans-serif;
}

#header {
  text-align: center;
  padding-top: 1em;
  padding-bottom: 1em;
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
.center-text{
  text-align: center;
}
.center-div {
  display: flex;
  justify-content: center;
  margin-top: 2em;

}

.invisibility {
  display: none
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
