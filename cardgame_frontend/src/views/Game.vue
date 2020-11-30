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
        



        
    </div>
</template>


<script>

import axios from 'axios'

export default {
    data() {
        return {
      //      number: 10,
            playerNumber: '',
          //  playerNumberHolder: '',
            btnValue: "Fastställ Nummer",
            whoWon: '',
            linkToGame: '',
            playerName: '',
            gameId: ''
        }
    },
    methods : {


        startGame() {
            if(this.gameId) {
                axios.get(`http://localhost:8080/game/${this.gameId}`)
                .then(response => this.linkToGame = `http://localhost:8080/game/${response.data.id}`)
            }else {
                 axios.get('http://localhost:8080/game/')
                .then(response => this.linkToGame = `http://localhost:8080/game/${response.data.id}`)
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