<template>
    <div class="outer-container" @click="leave" >
        <div class="container">
       
        <div class="flip-container" @click.stop="handleClick">
            <vue-flip  v-model="model" height="400px" width="75%" >
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
    </div>
</template>


<script>
import VueFlip from 'vue-flip';

export default {
    data() {
        return {
            model: true
        }
    },

    components: {
    'vue-flip': VueFlip
  },
    props: ['bigCard'],

    methods: {
        leave() {
            this.$emit("left")
            this.model = false;
            console.log("KÖR")
          
        },
        handleClick() {
            this.model = !this.model;
            console.log(this.model)
        }
    } 
    
}
</script>


<style scoped>

.outer-container {

    width: 1500px;
    height: 1000px;
    border: solid black 5px;
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: -1
}

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
    /*scale: 120%; */
    width: 100%;
    height: 100%;
    border: 1px solid black;
}

</style>