<template>
  <div>
    <!--        <div class="number">oppenent number: {{this.number}}</div>
        <div class="number">your number: {{this.playerNumber}}</div>
        <input type="text" v-model="playerNumberHolder">
        <button @click="createGame">{{this.btnValue}}</button>
        <button @click="setNumber">Hur gick det?</button>
        <button @click="whoWins">Vem vann?</button>
        <h1>{{this.whoWon}}</h1>
        <router-view/>
-->
    <input
      type="text"
      v-model="gameId"
      placeholder="Har du redan ett id?"
    >
    <button @click="startGame">Starta Spel</button>
    <button @click="drawCard">Dra kort</button>
    <div>Länk till spelet: {{this.linkToGame}}</div>
    <input
      type="text"
      v-model="playerNumber"
      placeholder="Gissa ett tal..."
    >
    <input
      type="text"
      v-model="playerName"
      placeholder="Ditt namn"
    >
    <button @click="playerMove">TEST</button>
    <div>Vinnaren är: {{this.whoWon}}</div>

    <GameBoard
      @moved="playerMove"
      :playedCards="playedCards"
      :playerHand="playerHand"
      ref="gb"
    />

  </div>

</template>

<script>
import axios from "axios";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

import GameBoard from "../components/GameBoard.vue";
export default {
  components: { GameBoard },
  data() {
    return {
      connected: "",
      twoPlayers: false,
      started: false,
      gameId: "",
      received_cards: [],

      //      number: 10,
      playerNumber: "",
      //  playerNumberHolder: '',
      btnValue: "Fastställ Nummer",
      whoWon: "",
      linkToGame: "",
      playerName: "",
      playerHand: [],
      playedCards: []
    };
  },
  methods: {

    playerMove(value) {
      console.log("Tester Coolio!");
      if (this.stompClient && this.stompClient.connected) {
        console.log("TESTING!");
           this.stompClient.send(`/app/connected/playerMove/${this.gameId}/${this.playerName}`,
          JSON.stringify({
            playerName: this.playerName,
            cardPosition: value.index,
            cardId: value.card
          })
        )
      }
    },
    drawCard(){

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
            this.stompClient.subscribe(`/cardgame/startCard/${this.gameId}/${this.playerName}`, (tick) => {
              this.playedCards = JSON.parse(tick.body)[1];
              this.playerHand = JSON.parse(tick.body)[0].hand;
              this.$refs.gb.setPlayerTurn(JSON.parse(tick.body)[0].turn);
              console.log("HEEEEEEEJ")
            });
              this.stompClient.subscribe(`/cardgame/updateGameBoard/${this.gameId}`, (tick) => {
                console.log(tick)
                this.playedCards = JSON.parse(tick.body)[0];
                console.log("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO")
                console.log(JSON.parse(tick.body)[1].name)
                if(JSON.parse(tick.body)[1].name === this.playerName) {
                    //this.changeTurns(true)
                    this.$refs.gb.setPlayerTurn(true);
                }else {
                    //this.changeTurns(false)
                    this.$refs.gb.setPlayerTurn(false);
                }
                console.log("UPPDATERAT GAMEBOARD!!!!")
            });
        

    },

startGame() {
      this.socket = new SockJS("http://localhost:8080/gs-guide-websocket");
      this.stompClient = Stomp.over(this.socket);
      if (this.gameId) {
        this.stompClient.connect(
          {},
          frame => {
            console.log(frame);
            this.connected = true;
            this.subscriptions()
            this.confirmSecondPlayer();
          },
          error => {
            console.log(error);
            this.connected = false;
          }
        );
      } else {
        axios
          .get(`http://localhost:8080/game/${this.playerName}`)
          .then(response => (this.gameId = response.data.id))
          .then(
            this.stompClient.connect(
              {},
              frame => {
                console.log(frame);
                this.connected = true;
                this.stompClient.subscribe(
                  `/cardgame/connected/${this.gameId}`,
                  tick => {
                    this.twoPlayers = JSON.parse(tick.body);
                    console.log("twoPlayers = " + this.twoPlayers);
                  }
                );

                this.subscriptions()



              },
              error => {
                console.log(error);
                this.connected = false;
              }
            )
          );
      }
    },
    setBoard(tick){
      console.log(JSON.parse(tick.body));
              console.log("HHHHEEEEEEEEEEJ");
              this.playedCards = JSON.parse(tick.body)[1];
              this.playerHand = JSON.parse(tick.body)[0].hand;
              this.$refs.gb.setPlayerTurn(JSON.parse(tick.body)[0].turn);
              console.log("playedCards:");
              console.log(this.playedCards);
    },
    /*startGame() {
      this.socket = new SockJS("http://localhost:8080/gs-guide-websocket");
      this.stompClient = Stomp.over(this.socket);
      var newGame = !this.gameId;
      console.log("Detta är newGame: " + newGame);
      if (newGame) {
        axios
          .get(`http://localhost:8080/game/${this.playerName}`)
          //then(console.log("HEEEEEEEEJ"))
          //  .then(response => console.log(response))
          .then(response => (this.gameId = response.data.id));
      }

      this.stompClient.connect(
        {},
        frame => {
          console.log(frame);
          this.connected = true;
          /*this.stompClient.subscribe(
              `/cardgame/drawn/${this.gameId}`,
              tick => {
                this.received_messages.push(JSON.parse(tick.body).content);
              }
            );
          this.stompClient.subscribe(
            `/cardgame/startCard/${this.gameId}/${this.playerName}`,
            tick => {
              console.log(JSON.parse(tick.body));
              console.log("HHHHEEEEEEEEEEJ");
              this.playedCards = JSON.parse(tick.body)[1];
              this.playerHand = JSON.parse(tick.body)[0].hand;
              this.$refs.gb.setPlayerTurn(JSON.parse(tick.body)[0].turn);
              console.log("playedCards:");
              console.log(this.playedCards);
            }
          );

          //this.confirmSecondPlayer();
        },
        error => {
          console.log(error);
          this.connected = false;
        }
      );
      if (!newGame) {
        this.confirmSecondPlayer();
      }
    },*/

    start() {
      axios
        .get(`${this.linkToGame}/${this.playerName}/${this.playerNumber}`)
        .then(response => (this.whoWon = response.data));
    }

    /*    createGame() {
            axios.get('http://localhost:8080/game/')
            .then(this.playerNumber = this.playerNumberHolder)
            .catch(error => console.log(error))
        },
        setNumber() {
            axios.get(`http://localhost:8080/game/start/${this.playerNumber}`)
        },
        whoWins() {
            axios.get('http://localhost:8080/game/play')
        }

        */
  }
};
</script>

<style scoped></style>
