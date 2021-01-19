import axios from 'axios'


const state = {
    fullDeck: [] 
    
}

const getters = {
    wholeCollection: (state) => state.fullDeck,   
    
}

const actions = {


    async fetchFullDeck({commit}) {
        const response = await axios.get('http://localhost:8080/allCards')
       commit('fillFullDeck', response.data.cards)
         console.log(response.data) 
    },


}

const mutations = {
    fillFullDeck(state, deck) {
        state.fullDeck = deck
    },

}

export default {
    state,
    getters,
    actions,
    mutations
}