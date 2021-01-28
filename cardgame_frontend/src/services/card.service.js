import axios from "axios";
import backend from "./backend";
import Vue from "vue";

const ALL_CARDS_URL = backend.ROOT_URL + "/allCards";
const NEW_CARD_URL = backend.ROOT_URL + "/newCard";
const CATEGORIES_URL = backend.ROOT_URL + "/categories";

class CardService {
  fetchAllCategories() {
    axios.get(CATEGORIES_URL).then((response) => {
      return response.data.categories;
    });
  }

  createNewCard(card) {
    axios
      .post(NEW_CARD_URL, card)
      .then((res) => {
        if (res.status === 201) {
          Vue.toasted.success("Kortet har sparats!");
        } else {
          throw new Error();
        }
      })
      .catch(() => this.showError("Kunde inte skapa kortet"));
  }

  fetchFullDeck() {
    return axios
      .get(ALL_CARDS_URL)
      .then((response) => {
        if (response.data && response.data.cards) {
          return response.data.cards;
        } else {
          this.showError(
            "Varning: Inga kort i databasen<br>Är servern konfigurerad korrekt?"
          );
          return [];
        }
      })
      .catch(() => {
        throw new Error("Kunde inte hämta alla kort");
      });
  }

  showError(message) {
    Vue.toasted.error(message, {
      singleton: true,
      duration: 5000,
    });
  }
}

export default new CardService();
