
const state = {
    user: { 
        username: "Pontus",
        email: "bollpiska@hotmail.com"
    } 
    
}

const getters = {
    user: (state) => state.user
}

const actions = {
    /*async fetchFullDeck({commit}) {
        const response = await axios.get('http://localhost:8080/allCards')
       commit('fillFullDeck', response.data.cards)
         console.log(response.data) 
         */
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
    mutations
}