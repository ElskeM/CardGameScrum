<template>
    <div>
        <div class="container">
       

  <!--      <router-link :to="{ path: `/singlecard/${this.card.id}`}" class="card"> -->
            <img
            class="card"
            :src="card.frontImage"
            v-bind:href="this.card.frontImage"
          >
   <!--     </router-link> -->
        
    <!--        <h3>{{this.card.title}}</h3> 
            <h4>{{this.card.subtitle}}</h4>
            <div>{{this.card.description}}</div>
            <div>{{this.card.score}} kg</div>
            <div>
                <a v-bind:href="this.card.frontImage">Card Front</a>
            </div> -->

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