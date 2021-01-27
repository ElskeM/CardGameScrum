<template>
  <div>
    <div class="center-text">
      <div class="flex">
        <div id="gamecontroller" v-if="!this.connected">
          <input type="text" v-model="playerName" placeholder="Ditt namn" />
          <button id="btn-start" @click="startGame" v-if="!this.gameId">
            Starta Spel
          </button>
          <button id="btn-start" @click="joinGame" v-else-if="!this.connected">
            Gå med
          </button>
          <div>
            <span v-if="this.linkToGame">
              Länk till spelet:
              <a :href="this.linkToGame" target="_blank">
                {{ this.linkToGame }}
              </a>
            </span>
          </div>
        </div>

        <div id="scoreboard" class="game-gui">
          <div v-if="this.connected">
              <h3>{{this.playerName}}</h3>
            <!--<h3>Ansluten till spel: {{ this.gameId }}</h3>-->
            <div v-if="this.gameInfo">
              <span id="matches">
                Spelade matcher: {{ this.gameInfo.matches }}
              </span>
              <br />
              <b>Vinster</b>
              <div>
                <span
                  v-for="player in this.gameInfo.players"
                  :key="player.name"
                >
                  {{ player.name }}: {{ player.wins }}
                  <span class="spacer"></span>
                </span>
              </div>
            </div>
            <div v-else>Väntar på andra spelare...
              <div>
            <span v-if="this.linkToGame">
              Länk till spelet:
              <a :href="this.linkToGame" target="_blank">
                {{ this.linkToGame }}
              </a>
            </span>
          </div>
            </div>
          </div>
          <div v-else class="game-title">
            <h1>KLIMATKOLL</h1>
          </div>
        </div>
        <div class="game-gui" v-if="this.gameInfo">
          <Timer ref="timer" :playerTurn="this.$refs.gameboard.playerTurn" :missedTurns="this.gameState.player.missedTurns" v-if="this.gameState.player"/>
          
        </div>
      </div>

      <div id="icon-container"> 
          
          <div
            id="menu-icon-container"
            @click="$emit('hide')"
            v-bind:class="{ invisible: hideChatSymbol }"
            >
        <!-- Av VisualEditor team - https://git.wikimedia.org/summary/mediawiki%2Fextensions%2FVisualEditor.git, MIT, https://commons.wikimedia.org/w/index.php?curid=26927425 -->
            <img src="../assets/menu-icon.svg">
          </div>

          <div
            id="chat-icon-container"
            @click="chatIconClicked"
            v-bind:class="{ invisible: hideChatSymbol }"
          >
            <img src="../assets/chat-icon.png" id="chat-icon" />
            <div id="chat-alert" v-bind:class="{ invisible: hideAlert }">
              {{ unreadMessages }}
            </div>
          </div>
      </div>

    </div>
    <div v-if="this.gameInfo" id="turn-shower" class="center-text">
      
    </div>

    <GameBoard
      @moved="playerMove"
      :playedCards="gameState.table"
      :playerHand="gameState.player.hand"
      :muck="gameState.muck"
      ref="gameboard"
    />
    <Chat
      id="chat"
      v-on:minimize="chatIconClicked"
      v-bind:class="{ invisible: hideChat }"
      v-on:messageSent="sendChatMessage"
      :playerName="playerName"
      :chatMessages="chatMessages"
      :chatMessageColor="chatMessageColor"
    />
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import GameBoard from "../components/GameBoard.vue";
import Timer from "../components/Timer.vue";
import Chat from "../components/Chat.vue";
import { mapGetters } from "vuex";
import backend from "../services/backend";
import GameService from "../services/game.service";
import GameState from "../models/GameState";

export default {
  computed: mapGetters(["user"]),

  components: {
    GameBoard,
    Chat,
    Timer,
  },

  mounted() {
    this.playerName = this.user.username;

    if (this.gameId && Number.isNaN(this.gameId)) {
      this.$toasted.error("invalid game id: " + this.gameId, {
        duration: null,
      });
    }

   // this.$emit("switch")
  },

  data() {
    return {
      connected: "",
      gameId: this.$route.params.id,
      linkToGame: "",
      playerName: "",
      gameInfo: null,
      gameState: new GameState(),

      //Timer
      timerOn: false,

      //Chat-variabler
      chatMessages: [],
      chatMessageColor: "",
      hideChat: true,
      hideAlert: true,
      hideChatSymbol: true,
      unreadMessages: 0,
    };
  },
  methods: {
    chatIconClicked() {
      this.hideChat = !this.hideChat;
      this.unreadMessages = 0;
      this.hideAlert = true;
    },

    playerMove(value) {
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send(
          backend.STOMP.MOVE + `/${this.gameId}/${this.playerName}`,
          JSON.stringify({
            playerName: this.playerName,
            cardPosition: value.index,
            cardId: value.card,
          })
        );
      }
    },

    sendChatMessage(message) {
      this.stompClient.send(
        backend.STOMP.CHATSEND + `/${this.gameId}`,
        JSON.stringify(message)
      );
    },

    confirmSecondPlayer() {
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send(
          backend.STOMP.CONNECTED + `/${this.gameId}/${this.playerName}`,
          {}
        );
      }
    },

    changeTurns(bool) {
      this.$refs.gameboard.setPlayerTurn(bool);
    },

    subscriptions() {
      this.stompClient.subscribe(
        backend.STOMP.GAMEINFO + `/${this.gameId}`,
        (msg) => {
          this.gameInfo = JSON.parse(msg.body);
        }
      );

      this.stompClient.subscribe(
        backend.STOMP.GAMESTATE_CHANGED + `/${this.gameId}/${this.playerName}`,
        (tick) => {
          this.gameState = JSON.parse(tick.body);
          this.updateView();
        }
      );

      this.stompClient.subscribe(
        backend.STOMP.MOVEINFO + `/${this.gameId}/${this.playerName}`,
        (tick) => {
          this.$refs.gameboard.setCorrectMove(JSON.parse(tick.body));
        }
      );

      this.stompClient.subscribe(
        backend.STOMP.CHATRECEIVE + `/${this.gameId}`,
        (tick) => {
          this.chatMessages.unshift(JSON.parse(tick.body));
          this.onChatMessage();
        }
      );

//När den andra spelaren går med görs en emit till APP som då gömmer nav-baren högst upp i fönstret
      this.stompClient.subscribe(
        backend.STOMP.BOTH_PLAYERS_CONNECTED + `/${this.gameId}`,
        () => {
          this.$emit("hide")
        }
      );

    },

    onChatMessage() {
      if (this.chatMessages[0].name !== this.playerName) {
        this.unreadMessages++;
        if (this.hideChat) {
          this.hideAlert = false;
        }
      }
    },

    updateView() {
      if (this.timerOn) {
        this.$refs.timer.resetAndStartTimer();
      } else {
        this.timerOn = true;
      }
      this.hideChatSymbol = false;

      if (this.gameState.winner != null) {
        this.endGame();
      }
      this.$refs.gameboard.setPlayerTurn(this.gameState.player.turn);
    },

    endGame() {
      this.gameEnd = true;
      this.$refs.timer.stopTimer();
      this.timerOn = false;
      this.$refs.gameboard.setPlayerTurn(false);
      this.$confirm(
        "Vill du spela en gång till?",
        "Vinnare är: " + this.gameState.winner + "!"
      ).then(() => GameService.rematch(this.gameId));
    },

    initializeWebstomp() {
      this.socket = new SockJS(backend.WEBSOCKET_URL);
      this.stompClient = Stomp.over(this.socket);
    },

    joinGame() {
      this.linkToGame = window.location.origin + `/game/${this.gameId}`;
      this.initializeWebstomp();
      this.chatMessageColor = "blue";
      this.stompClient.connect(
        {},
        () => {
          //on success
          this.connected = true;
          this.subscriptions();
          this.confirmSecondPlayer();
        },
        () => {
          //on error
          this.connected = false;
          this.$toasted.error("FEL: KUNDE INTE GÅ MED I SPELET");
        }
      );
    },

    startGame() {
      GameService.startGame(this.playerName)
        .then((gameId) => {
          this.gameId = gameId;
          this.initializeWebstomp();
          this.stompClient.connect(
            {},
            () => {
              //on success
              this.connected = true;
              this.$router.push(`/game/${this.gameId}`);
              this.linkToGame = window.location.origin + `/game/${this.gameId}`;
              this.chatMessageColor = "green";
              this.subscriptions();
            },
            () => {
              //on failure
              this.connected = false;
              this.$toasted.error("FEL: KUNDE INTE STARTA SPELET");
            }
          );
        })
        .catch(() =>
          this.$toasted.error("Kunde inte starta spelet (internt serverfel)")
        );
    },
  },
};
</script>

<style scoped>
.game-gui {
  border: 1px solid;
  margin: 10px;
  padding: 10px;
  width: 320px;
  display: table;
  height: 100px;
}
.game-gui p{
  margin-top:0px;
  margin-bottom:5px;
  font-weight: bolder;
}

#header img {
  width: 10em;
}
#scoreboard {
  position: relative;
}

#chat-icon-container {
  width: 3rem;
  height: 3rem;
  background-color: white;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}

#chat-icon-container:hover {
  cursor: pointer;
}

#chat-icon {
  max-width: 80%;
  max-height: 80%;
}

#icon-container {
  height: 50px;
  width: 100px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: absolute;
  left: 20px;
  top: 15px;
}

#chat-alert {
  height: 1.3rem;
  width: 1.3rem;
  background-color: red;
  border-radius: 50%;
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  right: 1%;
  top: 1%;
  color: white;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 15px;
}


#menu-icon-container {
  width: 2rem;
  height: 2rem;
  border: white solid 2px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  background-color:  #2d2f4e
}

#menu-icon-container img {
  width: 100%;
  height: 100%
}

#menu-icon-container:hover {
  cursor: pointer
}



.flex {
  display: inline-flex;
  justify-content: center;
  align-items: center;
}

.spacer {
  display: inline-block;
  width: 5rem;
}

h3 {
  margin: 5px;
}
#turn-shower{
  text-align: center;
}
#btn-start {
  width: 4rem;
  height: 4rem;
  border-radius: 50%;
  background-color: green;
  font-weight: bold;
  color: white;
}

#btn-start:hover {
  background-color: rgb(4, 82, 4);
}

#chat {
  position: fixed;
  bottom: 0%;
  left: 1%;
}
.game-title{
  margin: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%)
}
.invisible {
  opacity: 0%;
}
</style>
