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
         <input type="text" v-model="gameId" placeholder="Har du redan ett id?">
        <button @click="startGame">Starta Spel</button>
        <button @click="drawCard">Dra kort</button>
        <div>Länk till spelet: {{this.linkToGame}}</div>
        <input type="text" v-model="playerNumber" placeholder="Gissa ett tal...">
        <input type="text" v-model="playerName" placeholder="Ditt namn">
        <button @click ="playerMove">TEST</button>
        <div>Vinnaren är: {{this.whoWon}}</div>
        

<GameBoard :playedCards="playedCards" :playerHand="playerHand"/>

        
    </div>

</template>

<script>
import axios from "axios";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

import GameBoard from '../components/GameBoard.vue'
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

      
    test() {
         if (this.stompClient && this.stompClient.connected) {
        console.log("TESTING!");
        this.stompClient.send(`/app/connected/cardPlayed/`, JSON.stringify({message: "hej"}, {}));
        }
    },

    drawCard() {},

     playerMove() {
        if (this.stompClient && this.stompClient.connected) {
        console.log("TESTING!");
        this.stompClient.send(`/app/connected/playerMove/${this.gameId}/${this.playerName}`, 
        JSON.stringify(
                {
                playerName: this.playerName, 
                cardPosition: 20,
                cardId: 23           
                }
                
           ) 
           )

        }
    },

    confirmSecondPlayer() {
      if (this.stompClient && this.stompClient.connected) {
        console.log("HALLELUJAH!");
        this.stompClient.send(`/app/connected/${this.gameId}/${this.playerName}`, {});
      }
    },

    subscriptions() {
            this.stompClient.subscribe(`/cardgame/startCard/${this.gameId}/${this.playerName}`, (tick) => {
              this.playedCards = JSON.parse(tick.body)[0];
              this.playerHand = JSON.parse(tick.body)[1];
              console.log("HEEEEEEEJ")
            });
              this.stompClient.subscribe(`/cardgame/updateGameBoard/${this.gameId}`, (tick) => {
                  console.log(tick)
            //  this.playedCards = JSON.parse(tick.body);
              console.log("UPPDATERAT GAMEBOARD!!!!")
            });
        
    },

    startGame() {
      this.socket = new SockJS("http://localhost:8080/gs-guide-websocket");
      this.stompClient = Stomp.over(this.socket);
      if (this.gameId) {
        this.stompClient.connect(
          {},
          (frame) => {
            console.log(frame);
            this.connected = true;
            this.subscriptions()


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
          //then(console.log("HEEEEEEEEJ"))
        //  .then(response => console.log(response))
          .then((response) => (this.gameId = response.data.id))
          .then(
            this.stompClient.connect(
              {},
              (frame) => {
                console.log(frame);
                this.connected = true;
                this.stompClient.subscribe(
                  `/cardgame/connected/${this.gameId}`,
                  (tick) => {
                    this.twoPlayers = JSON.parse(tick.body);
                    console.log("twoPlayers = " + this.twoPlayers);
                  }
                );

                this.subscriptions()



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
            axios.get(`http://localhost:8080/game/start/${this.playerNumber}`)
        },
        whoWins() {
            axios.get('http://localhost:8080/game/play')
        }

        */
  },
};
</script>

<style scoped></style>
