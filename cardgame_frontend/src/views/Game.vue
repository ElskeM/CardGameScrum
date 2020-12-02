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
        <div>Länk till spelet: {{this.gameId}}</div>
        <input type="text" v-model="playerNumber" placeholder="Gissa ett tal...">
        <input type="text" v-model="playerName" placeholder="Ditt namn">
        <button @click = "start">START</button>
        <div>Vinnaren är: {{this.whoWon}}</div>
        



        
    </div>
</template>


<script>

import axios from 'axios'
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
    data() {
        return {
            connected: '',
            twoPlayers: false,
            started: false,
            gameId: '',


      //      number: 10,
            playerNumber: '',
          //  playerNumberHolder: '',
            btnValue: "Fastställ Nummer",
            whoWon: '',
            linkToGame: '',
            playerName: ''
           
        }
    },
    methods : {

        confirmSecondPlayer() {
            if (this.stompClient && this.stompClient.connected) {
                this.stompClient.send(`/app/connected/${this.gameId}`, {});
             }
        },

        startGame() {
            this.socket = new SockJS("http://localhost:8080/gs-guide-websocket");
            this.stompClient = Stomp.over(this.socket);
            if(this.gameId) {
                    this.stompClient.connect(
                    {},
                    frame => {
                        console.log(frame)
                        this.connected = true;
                        this.stompClient.subscribe(`/app/drawn/${this.gameId}`, tick => {
                        this.received_messages.push(JSON.parse(tick.body).content);
                 });
                 },
                    error => {
                     console.log(error);
                     this.connected = false;
                })
                
                this.confirmSecondPlayer()
                
            }else {
                 axios.get('http://localhost:8080/game/')
                .then(response => this.gameId = response.data.id)
                .then(
                    this.stompClient.connect(
                    {},
                    frame => {
                        console.log(frame)
                        this.connected = true;
                        this.stompClient.subscribe(`/app/connected/${this.gameId}`, tick => {
                        this.twoPlayers = this.JSON.parse(tick.body).content;
                 });
                 },
                    error => {
                     console.log(error);
                     this.connected = false;
                }
             ) )
            }

        },

        start() {
            axios.get(`${this.linkToGame}/${this.playerName}/${this.playerNumber}`)
            .then(response => this.whoWon = response.data)
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
    
}
</script>

<style scoped>

</style>