<template>
    <div>
        
        <div v-for="card in wholeCollection" :key="card.id">
            
            <DisplayCard v-bind:card="card"/>
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

</style>