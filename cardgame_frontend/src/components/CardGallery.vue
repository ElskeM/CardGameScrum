<template>
    <div>
        
        <div v-for="card in wholeCollection" :key="card.id">
            
            <DisplayCard v-bind:card="card"/>
        </div>
    </div>
    
</template>

<script>

import {mapGetters} from 'vuex'
import DisplayCard from './DisplayCard.vue'


export default {
    name: 'CardGallery',
    computed: mapGetters(['wholeCollection']),
    created() {
        console.log(this.wholeCollection)
    },
    components : {
        DisplayCard
    }

    
}
</script>


<style scoped>

</style>