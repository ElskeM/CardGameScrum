<template>
  <div id="app">
    <div v-show="showNavBar">
      <div id="header">
        <a href="/">
          <img src="./assets/header.png" />
        </a>
      </div>
      <NavBar />
    </div>
    <router-view @hide="switchVisibility" />
  </div>
</template>
<script>
import NavBar from "@/components/NavBar.vue";

export default {
  name: "app",
  components: {
    NavBar,
  },

  data() {
    return {
      showNavBar: true,
    };
  },
  methods: {
    switchVisibility() {
      this.showNavBar = !this.showNavBar;
    },
  },
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
.center-text {
  text-align: center;
}
.center-div {
  display: flex;
  justify-content: center;
  margin-top: 2em;
}
</style>
