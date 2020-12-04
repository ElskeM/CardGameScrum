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
        <button @click="startGame">Starta</button>
        <div>Länk till spelet: {{this.linkToGame}}</div>
        <input type="text" v-model="playerNumber" placeholder="Gissa ett tal...">
        <input type="text" v-model="playerName" placeholder="Ditt namn">
        <button @click = "start">START</button>
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
      playedCards: [
        {
          id: 0,
          title: "Blandkost",
          subtitle: "Svenskt genomsnitt",
          description: "1 års mat för en genomsnittlig svensk",
          frequence: 365,
          score: 2000,
          extraInfo: "Utsläpp från nöttkött: 45 %, mjölkprodukter: 25%",
          category: "Livsmedel",
          frontImage: "http://localhost:8080/images/Kort1_front.jpg",
          backImage: "http://localhost:8080/images/Kort1_back.jpg"
        },
        {
          id: 1,
          title: "Blandkost",
          subtitle: "Frigående kyckling",
          description:
            "1års mat för en genomsnittlig svensk, men allt kött är från frigående kyckling",
          frequence: 365,
          score: 1000,
          extraInfo: "Utsläpp från kyckling: 20%",
          category: "Livsmedel",
          frontImage: "http://localhost:8080/images/Kort2_front.jpg",
          backImage: "http://localhost:8080//images/Kort2_back.jpg"
        }
      ],
      playerHand: [
        {
          id: 2,
          title: "Vegetarisk kost",
          subtitle: null,
          description:
            "1 års mat för en genomsnittlig svensk vegetarian, protein från växter, ägg och mjölkprodukter",
          frequence: 365,
          score: 900,
          extraInfo: "Utsläpp från mjölkprodukter: 50%",
          category: "Livsmedel",
          frontImage: "http://localhost:8080/images/Kort3_front.jpg",
          backImage: "http://localhost:8080/images/Kort3_back.jpg"
        },
        {
          id: 3,
          title: "Vegansk kost",
          subtitle: null,
          description:
            "1 års mat för en genomsnittlig svensk vegan, med protein från växter",
          frequence: 365,
          score: 500,
          extraInfo: null,
          category: "Livsmedel",
          frontImage: "http://localhost:8080/images/Kort4_front.jpg",
          backImage: "http://localhost:8080/images/Kort4_back.jpg"
        }
      ]
    };
  },
  methods: {
    confirmSecondPlayer() {
      if (this.stompClient && this.stompClient.connected) {
        console.log("HALLELUJAH!");
        this.stompClient.send(`/app/connected/${this.gameId}`, {});
      }
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
            this.stompClient.subscribe(`/cardgame/drawn/${this.gameId}`, (tick) => {
              this.received_messages.push(JSON.parse(tick.body).content);
            });
             this.stompClient.subscribe(`/cardgame/startCard/${this.gameId}`, (tick) => {
              this.received_cards = JSON.parse(tick.body).content;
              
            });

            this.confirmSecondPlayer();
          },
          (error) => {
            console.log(error);
            this.connected = false;
          }
        );

      } else {
        axios
          .get("http://localhost:8080/game/")
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

                 this.stompClient.subscribe(`/cardgame/startCard/${this.gameId}`, (tick) => {
                        this.received_cards = JSON.parse(tick.body).content;
                        console.log(this.received_cards)
                 });



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
