<template>
  <div>
    <div id="nav">
      <router-link class="link" to="/">HEM</router-link> 
      <router-link  class="link" to="/about">OM KLIMATKOLL</router-link> 
      <router-link  class="link" to="/game">SPELA</router-link> 
      <span v-if="!isLoggedIn()" class="link">
        <router-link class="link-inside"  to="/register">REGISTRERA</router-link> 
        <router-link class="link-inside" to="/login">LOGGA IN</router-link>
      </span>
      <router-link  class="link" v-else to="/login">INLOGGAD SOM {{user.username}} </router-link>
    </div>
  </div>
</template>

<script>
import AuthService from "../services/auth.service";
import { mapGetters } from "vuex";



export default {

  computed: mapGetters(["user"]),
  methods: {
    isLoggedIn() {
      return AuthService.isLoggedIn();
    }
  }
};
</script>


<style scoped>

#nav {
  display: flex;
  justify-content: center;
  background-color: #3e4172
}


.link{
   padding-top: 1rem;
   padding-bottom: 1rem;
   margin-right: 4rem;
   margin-left: 4rem;   
   font-weight: bolder;   
}

.link-inside {
   margin-right: 6rem; 
   margin-left: 2rem
}

[class*="link"]:hover {
  color: rgb(204, 200, 200)
}




</style>