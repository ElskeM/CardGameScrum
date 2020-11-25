import Vue from 'vue'
import Vuex from 'vuex'
import cardCollections from './modules/cardCollections'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    cardCollections
}
})
