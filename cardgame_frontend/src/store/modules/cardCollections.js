import cardService from "../../services/card.service";

const state = {
  fullDeck: [],
};

const getters = {
  wholeCollection: (state) => state.fullDeck,
};

const actions = {
  async fetchFullDeck({ commit }) {
    return await cardService.fetchFullDeck().then((fullDeck) => {
      if (fullDeck.length != 0) {
        commit("fillFullDeck", fullDeck);
      }
      return fullDeck;
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
