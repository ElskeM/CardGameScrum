


const state = {
    user: { 
  
    } 
    
}

const getters = {
    user: (state) => state.user
}

const actions = {

    setUser({commit, user}) {
          commit('addUser', user)

    } 


}

const mutations = {
    addUser(state, user) {
        state.user = user
    }

}

export default {
    state,
    getters,
    actions,
    mutations,
}