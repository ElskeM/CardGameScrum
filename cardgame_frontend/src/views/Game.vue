<template>
  <div>
    <div class="flex">
      <div id="gamecontroller">
        <input
          type="text"
          v-model="this.$route.params.id"
          placeholder="Har du redan ett id?"
        />
        <button @click="startGame">Starta Spel</button>
        <button @click="drawCard">Dra kort</button>
        <div>
          Länk till spelet:
          <span v-if="this.linkToGame">
            <a :href="this.linkToGame"> {{ this.linkToGame }}</a></span
          >
        </div>

        <input type="text" v-model="playerName" placeholder="Ditt namn" />
        <button @click="playerMove">TEST</button>
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
   </div>
    <div v-if="this.connected">
      <span v-if="this.$refs.gb.playerTurn">Your turn</span>
      <span v-else>Other player's turn</span>
    </div>
    <GameBoard
      @moved="playerMove"
      :playedCards="playedCards"
      :playerHand="playerHand"
      ref="gb"
    />
    <Chat 
      v-on:messageSent="sendChatMessage" 
      :playerName="playerName" 
      :chatMessages="chatMessages"
      
      />
  </div>
</template>

<script>
import axios from "axios";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

import GameBoard from "../components/GameBoard.vue";
import Chat from "../components/Chat.vue";

export default {
  components: { 
      GameBoard,
      Chat 
    },
  data() {
    return {
      connected: "",
      twoPlayers: false,
      started: false,
      gameId: "",

      whoWon: "",
      linkToGame: "",
      playerName: "",
      gameInfo: null,
      playerHand: [],
      playedCards: [],
      chatMessages: [],
    };
  },
  methods: {
    playerMove(value) {
      console.log("Tester Coolio!");
      if (this.stompClient && this.stompClient.connected) {
        console.log("TESTING!");
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
      console.log("NU FÖRSÖKER JAG SKICKA MEDDELANDE")
      console.log(JSON.stringify(message))
      console.log(this.playerName)
      this.stompClient.send(
        `/app/chatmessage/${this.gameId}`,
        JSON.stringify(message)
      )

    },


    confirmSecondPlayer() {
      if (this.stompClient && this.stompClient.connected) {
        console.log("HALLELUJAH!");
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
          this.playedCards = JSON.parse(tick.body).table;
          this.playerHand = JSON.parse(tick.body).player.hand;
          this.$refs.gb.setPlayerTurn(JSON.parse(tick.body).player.turn);
          console.log("HEEEEEEEJ");
          this.linkToGame = `http://localhost:8081/game/${this.gameId}`;

        }
      );
      this.stompClient.subscribe(
        `/cardgame/updateGameBoard/${this.gameId}`,
        (tick) => {
          console.log(tick);
          this.playedCards = JSON.parse(tick.body);
          console.log("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
          console.log(JSON.parse(tick.body));
          /*if (JSON.parse(tick.body)[1].name === this.playerName) {
            //this.changeTurns(true)
            this.$refs.gb.setPlayerTurn(true);
          } else {
            //this.changeTurns(false)
            this.$refs.gb.setPlayerTurn(false);
          }*/
          console.log("UPPDATERAT GAMEBOARD!!!!");
        }
      );
      this.stompClient.subscribe(
        `cardgame/chat/${this.gameId}`,
        (tick) => {
          console.log(tick);
          console.log(JSON.parse(tick.body));
        }
        )
    },

    startGame() {
      this.gameId = this.$route.params.id;
      this.socket = new SockJS("http://localhost:8080/gs-guide-websocket");
      this.stompClient = Stomp.over(this.socket);
      if (this.gameId) {
        console.log("GAME ID IS TRUE");
        this.stompClient.connect(
          {},
          (frame) => {
            console.log(frame);
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
          .get(`http://localhost:8080/game/${this.playerName}`)
          .then((response) => (this.gameId = response.data.id))
          .then(
            this.stompClient.connect(
              {},
              (frame) => {
                console.log(frame);
                this.connected = true;
                this.$router.push(`/game/${this.gameId}`);
                this.linkToGame = `http://localhost:8081/game/${this.gameId}`;
                /*this.stompClient.subscribe(
                  `/cardgame/connected/${this.gameId}`,
                  tick => {
                    this.twoPlayers = JSON.parse(tick.body);
                    console.log("twoPlayers = " + this.twoPlayers);
                  }
                );*/

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

    start() {
      axios
        .get(`${this.linkToGame}/${this.playerName}/${this.playerNumber}`)
        .then((response) => (this.whoWon = response.data));
    },

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
  border: 1px solid black;
  margin: 10px;
  padding: 10px;
  width: fit-content;
}
#scoreboard {
  min-width: 320px;
}

.flex {
  display: flex;
}

.spacer {
  display: inline-block;
  width: 5rem;
}

h3 {
  margin: 5px;
}
</style>
