import axios from "axios";
import backend from "./backend";
import authHeader from "./auth-header";

export default {
  async startGame(playerName) {
    return await axios
      .get(backend.GAME_URL + `/${playerName}`, {
        headers: authHeader(),
      })
      .then((response) => response.data.id);
  },

  async rematch(gameId) {
    return await axios.get(backend.GAME_URL + `/${gameId}/confirm`, {
      headers: authHeader(),
    });
  },
};
