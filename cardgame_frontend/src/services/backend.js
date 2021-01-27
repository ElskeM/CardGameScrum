let backend = {
  PROTOCOL:"http",
  IP:"localhost",
  PORT:8080,
  STOMP: {
    GAMEINFO:"/cardgame/gameInfo",
    MOVE:"/app/connected/playerMove",
    MOVEINFO: "/cardgame/madeMove",
    CHATSEND:"/app/chatmessage",
    CHATRECEIVE: "/cardgame/chat",
    CONNECTED:"/app/connected",
    GAMESTATE_CHANGED: "/cardgame/startCard",
    BOTH_PLAYERS_CONNECTED: "/cardgame/connected"
  }
};

backend.ROOT_URL = backend.PROTOCOL + "://" + backend.IP + ":" + backend.PORT;
backend.GAME_URL = backend.ROOT_URL + "/game";
backend.WEBSOCKET_URL = backend.ROOT_URL + "/gs-guide-websocket";

export default backend;
