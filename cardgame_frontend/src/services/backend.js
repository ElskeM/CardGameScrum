import axios from "axios";

let backend = {
  PROTOCOL: "http",
  IP: "localhost",
  PORT: 8080,
  STOMP: {
    GAMEINFO: "/cardgame/gameInfo",
    MOVE: "/app/connected/playerMove",
    MOVEINFO: "/cardgame/madeMove",
    CHATSEND: "/app/chatmessage",
    CHATRECEIVE: "/cardgame/chat",
    CONNECTED: "/app/connected",
    GAMESTATE_CHANGED: "/cardgame/startCard",
    BOTH_PLAYERS_CONNECTED: "/cardgame/connected",
  },
  STATUS_ENDPOINT: "/status",
};

backend.ROOT_URL = backend.PROTOCOL + "://" + backend.IP + ":" + backend.PORT;
backend.GAME_URL = backend.ROOT_URL + "/game";
backend.WEBSOCKET_URL = backend.ROOT_URL + "/gs-guide-websocket";

backend.isConnected = function() {
  return axios
    .get(backend.ROOT_URL + backend.STATUS_ENDPOINT, { timeout: 1000 })
    .then(() => true)
    .catch(() => false);
};

export default backend;
