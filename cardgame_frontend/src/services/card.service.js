import axios from 'axios';
import backend from './backend';

const ALL_CARDS_URL = backend.ROOT_URL + "/allCards";

class CardService {
    fetchFullDeck() {
        return axios.get(ALL_CARDS_URL)
        .then(response=>{
            if(response.data && response.data.cards){
                return response.data.cards;
            }
            else {
                return [];
            }
        })
        .catch(()=>{throw new Error("Kunde inte hämta alla kort")});
    }
}

export default new CardService();