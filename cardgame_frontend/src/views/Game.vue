<template>
    <div>
        <div class="number">oppenent number: {{this.number}}</div>
        <div class="number">your number: {{this.playerNumber}}</div>
        <input type="text" v-model="playerNumberHolder">
        <button @click="createGame">{{this.btnValue}}</button>
        <button @click="setNumber">Hur gick det?</button>
        <button @click="whoWins">Vem vann?</button>
        <h1>{{this.whoWon}}</h1>
        <router-view/>
        
    </div>
</template>


<script>

import axios from 'axios'

export default {
    data() {
        return {
            number: 10,
            playerNumber: '',
            playerNumberHolder: '',
            btnValue: "Fastställ Nummer",
            whoWon: ''
        }
    },
    methods : {
        createGame() {
            axios.get('http://localhost:8080/game/start')
            .then(this.playerNumber = this.playerNumberHolder)
            .catch(error => console.log(error))
        },
        setNumber() {
            axios.get(`http://localhost:8080/game/start/${this.playerNumber}`)
        },
        whoWins() {
            axios.get('http://localhost:8080/game/play')
        }
    }
    
}
</script>

<style scoped>

</style>