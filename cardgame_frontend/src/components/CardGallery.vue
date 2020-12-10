<template>
    <div>
        <div class="gallery">
       <!-- <div class="card" v-for="card in wholeCollection" :key="card.id">-->
                <DisplayCard v-for="card in wholeCollection" :key="card.id" v-bind:card="card"/>
           
    <!--    </div> -->
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
    methods : {
        ...mapActions(['fetchFullDeck'])
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
}

.card {
    margin: 10px;
}
</style>