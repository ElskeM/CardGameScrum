<template>
    <div>
        <div class="container">
       

  <!--      <router-link :to="{ path: `/singlecard/${this.card.id}`}" class="card"> </router-link>-->
            <img
            class="card"
            :src="card.frontImage"
            v-bind:href="this.card.frontImage"
            @click="$emit('displaycard-clicked', card)"
          >
        </div>
    </div>
</template>

<script>


export default {
    name: 'DisplayCard',
    props: ['card']
}
</script>

<style scoped>

.container {
    
    width: 150px;
    height: 250px;  
}

img {
    width: 150px;
    height: 250px
}

.card {
    transition: transform .2s;
    border-radius: 1rem;
}

.card:hover {
    transform: scale(1.15)
}
</style>