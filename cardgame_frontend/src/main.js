import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VueSimpleAlert from "vue-simple-alert";
import Toasted from "vue-toasted";

Vue.use(VueSimpleAlert);
Vue.use(Toasted, {
  position: "bottom-center",
  duration: 5000,
  type: "info",
  fullWidth: true,
  action: {
    text: 'CLOSE',
    onClick: (e, toastObject) => {
      toastObject.goAway(0);
    }
  }
});

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
