import cardService from "../../services/card.service";

const state = {
  fullDeck: [],
};

const getters = {
  wholeCollection: (state) => state.fullDeck
};

const actions = {
  fetchFullDeck({ commit }) {
    cardService.fetchFullDeck().then((fullDeck) => {
      commit("fillFullDeck", fullDeck);
    });
  },
};

const mutations = {
  fillFullDeck(state, deck) {
    state.fullDeck = deck;
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
