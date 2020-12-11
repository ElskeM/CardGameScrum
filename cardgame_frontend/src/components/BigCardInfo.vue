<template>
    <div class="container">
       
        <div class="flip-container">
            <vue-flip active-click class="flip">
                <template v-slot:front class="front">
                     <div class="img">
                         <img class="card" :src="bigCard.frontImage">
                   </div>

                </template>
                 <template v-slot:back class="back">
                      <div class="img">
                           <img class="card" :src="bigCard.backImage">
                     </div>

                </template>
            
            </vue-flip>
        </div>

    </div>
</template>


<script>
import VueFlip from 'vue-flip';

export default {
    components: {
    'vue-flip': VueFlip
  },
     props: ['bigCard']
    
}
</script>


<style scoped>
.container {
    height: 500px;
    width: 300px;
    background: white;  
    border-radius: 3rem;
    display:flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    border: 0.5rem solid purple;
}

.flip-container {
    width: 100%;
    height: 100%;
   
    display: flex;
    justify-content: center;
    align-items: center
    
}

.flip {
    height: 400px;
    width: 75%; 
 
}



img {
    border-radius: 1rem;
   /* scale: 120%; */
    width: 100%;
    height: 100%;
    border: 1px solid black;
}

</style>