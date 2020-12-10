<template>
    <div>

       
        <div class="gallery">
       <div  @click="showBigCard" class="card" v-for="card in wholeCollection" :key="card.id">
                <DisplayCard v-bind:card="card"/>
           
      </div>
        </div>
         <div class="big-card" v-bind:class="{ visible: isVisible }">
             HELLO WORLD
         </div>
    </div>
    
</template>

<script>

import {mapGetters, mapActions} from 'vuex'
import DisplayCard from './DisplayCard.vue'


export default {
    name: 'CardGallery',
    computed: mapGetters(['wholeCollection']),
    created() {
        this.fetchFullDeck()
    },
    components : {
        DisplayCard
    },

    data() {
        return {
            isVisible: false
        }

    },
    methods : {
        ...mapActions(['fetchFullDeck']),

        showBigCard() {
            console.log("trycker showBigCard")
            this.isVisible = !this.isVisible
            console.log(this.isVisible)
    }
    
    }

    
}
</script>


<style scoped>


.gallery {
    width: 100%;
    height: 100%;
    display:flex;
    flex-wrap: wrap;
    border: solid black 5px;
    justify-content: center;
    position: absolute
}

.card {
    margin: 10px;
}

.big-card {
    height: 100px;
    width: 100px;
    background: red;
        
        position: fixed;
        top: 3rem;
        top: 50%;
        left: 50%;
        margin-right: -50%; 
        transform: translate(-50%, -50%); 
        visibility: hidden;
       
}

.visible {
    visibility:visible
}
</style>