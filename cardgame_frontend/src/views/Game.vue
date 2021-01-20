<template>
  <div>
    <div class="flex">
      <div id="gamecontroller">
        <input type="text" v-model="playerName" placeholder="Ditt namn" />
        <button id="btn-start" @click="startGame" v-if="!this.gameId">
          Starta Spel
        </button>
        <button id="btn-start" @click="startGame" v-else-if="!this.connected">
          Gå med
        </button>
        <div>
          <span v-if="this.linkToGame">
            Länk till spelet:
            <a :href="this.linkToGame" target="_blank">
              {{ this.linkToGame }}</a
            ></span
          >
        </div>

        <!--<button @click="playerMove">TEST</button>-->
      </div>

      <div id="scoreboard">
        <div v-if="this.connected">
          <h3>Connected to game: {{ this.gameId }}</h3>
          <div v-if="this.gameInfo">
            <span id="matches">Matches: {{ this.gameInfo.matches }}</span
            ><br />
            <b>Wins</b>
            <div>
              <span v-for="player in this.gameInfo.players" :key="player.name">
                {{ player.name }}: {{ player.wins }}
                <span class="spacer"></span>
              </span>
            </div>
          </div>
          <div v-else>Waiting for game info...</div>
        </div>
        <div v-else>
          <h1>DISCONNECTED</h1>
        </div>
      </div>

     <div id="chat-icon-container" @click="chatIconClicked" v-bind:class="{ invisible: hideChatSymbol}">
       <img src="../assets/chat-icon.png" id="chat-icon">
      <div id="chat-alert"  v-bind:class="{ invisible: hideAlert }">{{unreadMessages}}</div>
    
    </div>

    </div>
    <div v-if="this.connected">
      <span v-if="this.$refs.gb.playerTurn">Your turn</span>
      <span v-else>Other player's turn</span>
    </div>
    
    <GameBoard
      @moved="playerMove"
      :playedCards="playedCards"
      :playerHand="playerHand"
      :muck="muck"
      ref="gb"
    />
    <Chat id="chat" 
      
      v-on:minimize="hideChat = !hideChat"
      v-bind:class="{ invisible: hideChat }"
      v-on:messageSent="sendChatMessage"
      :playerName="playerName"
      :chatMessages="chatMessages"
      :chatMessageColor="chatMessageColor"
    />
  </div>
</template>

<script>
import axios from "axios";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import "../services/auth-header";

import GameBoard from "../components/GameBoard.vue";
import authHeader from "../services/auth-header";
import Chat from "../components/Chat.vue";
import { mapGetters } from "vuex";


export default {
  
  computed: mapGetters(["user"]),

  components: {
    GameBoard,
    Chat,
  },

  mounted() {
    this.playerName = this.user.username
  },
  
  data() {
    return {
      connected: "",
      twoPlayers: false,
      started: false,
      gameId: this.$route.params.id,

      whoWon: "",
      linkToGame: "",
      playerName: "",
      gameInfo: null,
      playerHand: [],
      playedCards: [],
      chatMessages: [],
      chatMessageColor: "",
      muck: [], // lista med slängda kort
      hideChat: true,
      hideAlert: true,
      hideChatSymbol: true,
      unreadMessages: 0
    };
  },
  methods: {

    chatIconClicked() {
      this.hideChat = !this.hideChat
      this.unreadMessages = 0
      this.hideAlert = true
    },

    playerMove(value) {
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send(
          `/app/connected/playerMove/${this.gameId}/${this.playerName}`,
          JSON.stringify({
            playerName: this.playerName,
            cardPosition: value.index,
            cardId: value.card,
          })
        );
      }
    },
    drawCard() {},

    sendChatMessage(message) {
      this.stompClient.send(
        `/app/chatmessage/${this.gameId}`,
        JSON.stringify(message)
      );
    },

    confirmSecondPlayer() {
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send(
          `/app/connected/${this.gameId}/${this.playerName}`,
          {}
        );
      }
    },

    changeTurns(bool) {
      this.$refs.gb.setPlayerTurn(bool);
    },

    

    subscriptions() {
      this.stompClient.subscribe(`/cardgame/gameInfo/${this.gameId}`, (msg) => {
        this.gameInfo = JSON.parse(msg.body);
      });
      this.stompClient.subscribe(
        
        `/cardgame/startCard/${this.gameId}/${this.playerName}`,
        (tick) => {
          this.hideChatSymbol = false
          this.playedCards = JSON.parse(tick.body).table;
          this.playerHand = JSON.parse(tick.body).player.hand;
          this.muck = JSON.parse(tick.body).muck;
          if (JSON.parse(tick.body).winner != null) {
            this.gameEnd = true;
            this.winner = JSON.parse(tick.body).winner;
            this.$alert("Vill du spela en gång till?", "Vinnare är: " + this.winner + "!");
            this.$refs.gb.setPlayerTurn(false);
            this.$confirm(
              "Vill du spela en gång till?",
              "Vinnare är: " + this.winner + "!"
            ).then(() => {
              axios.get(`http://localhost:8080/game/${this.gameId}/confirm`, {
                headers: authHeader(),
              });
            });
          } else {
            this.$refs.gb.setPlayerTurn(JSON.parse(tick.body).player.turn);
          }
          this.$refs.gb.setPlayerTurn(JSON.parse(tick.body).player.turn);
          this.linkToGame = `http://localhost:8081/game/${this.gameId}`;
        }
      );
      this.stompClient.subscribe(
        `/cardgame/updateGameBoard/${this.gameId}`,
        tick => {
          this.playedCards = JSON.parse(tick.body);  
        }
      );
      this.stompClient.subscribe(
        `/cardgame/chat/${this.gameId}`,
        (tick) => {
          this.chatMessages.unshift(JSON.parse(tick.body))
          if((JSON.parse(tick.body)).name !== this.playerName) {
            this.unreadMessages++
            if(this.hideChat) {
            this.hideAlert = false;
            }
          }
        }
        )
    }, 

    startGame() {
      this.gameId = this.$route.params.id;
      this.socket = new SockJS("http://localhost:8080/gs-guide-websocket");
      this.stompClient = Stomp.over(this.socket);
      if (this.gameId) {
        this.chatMessageColor = "blue"
        this.stompClient.connect(
          {},
          () => {
            this.connected = true;
            this.subscriptions();
            this.confirmSecondPlayer();
          },
          (error) => {
            console.log(error);
            this.connected = false;
          }
        );
      } else {
        axios
          .get(`http://localhost:8080/game/${this.playerName}`, {
            headers: authHeader(),
          })
          .then((response) => (this.gameId = response.data.id))
          .then(
            this.stompClient.connect(
              {},
              (frame) => {
                console.log(frame);
                this.connected = true;
                this.$router.push(`/game/${this.gameId}`);
                this.linkToGame = `http://localhost:8081/game/${this.gameId}`;
                this.chatMessageColor = "green"
                this.subscriptions();
              },
              (error) => {
                console.log(error);
                this.connected = false;
              }
            )
          );
      }
    },

/*
   start() {
      axios
        .get(`${this.linkToGame}/${this.playerName}/${this.playerNumber}`, {headers:authHeader()})
        .then(response => (this.whoWon = response.data));
    }
    
    */

    /*    createGame() {
            axios.get('http://localhost:8080/game/')
            .then(this.playerNumber = this.playerNumberHolder)
            .catch(error => console.log(error))
        },
        setNumber() {
            fetch(`http://localhost:8080/game/start/${this.playerNumber}`)
        },
        whoWins() {
            fetch('http://localhost:8080/game/play')
        }

        */
  },
};
</script>

<style scoped>
#gamecontroller,
#scoreboard {
  border: 1px solid;
  margin: 10px;
  padding: 10px;
  width: fit-content;
}
#header img {
  width: 10em;
}
#scoreboard {
  min-width: 320px;
}

#chat-icon-container {
  width: 5rem;
  height: 5rem;
  background-color: white;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative
}

#chat-icon {
  max-width: 80%;
  max-height: 80%;
}

#chat-alert {
  height: 2rem;
  width: 2rem;
  background-color: red;
  border-radius: 50%;
  position:absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  right: 1%;
  top: 1%;
  color: white;
  

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

#btn-start {
  width: 4rem;
  height: 4rem;
  border-radius: 50%;
  background-color: green;
  font-weight: bold;
  color: white
}

#btn-start:hover {
  background-color:rgb(4, 82, 4)
}


#chat {
  position:fixed;
  bottom: 0%;
  left: 1%
}

.invisible {
  opacity: 0%;
 
    }

</style>
