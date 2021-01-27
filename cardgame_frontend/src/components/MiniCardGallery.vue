<template>
    <div>
    <!-- karusellen vill inte starta upp om den innehåller färre än två objekt, därav v-if:en  -->
    <VueSlickCarousel v-if="muck.length>1" @init="onInit" :settings="settings" id="carousel">
        <div v-for="card in muck" :key="card.id">
           <img :src="card.backImage">
        </div>
    </VueSlickCarousel>
    <!-- det första kortet visas istället som en vanlig img -->
    <img v-if="muck.length === 1" :src="muck[0].backImage">
    </div>
</template>

<script>
  import VueSlickCarousel from 'vue-slick-carousel'
  import 'vue-slick-carousel/dist/vue-slick-carousel.css'
  // optional style for arrows & dots
  import 'vue-slick-carousel/dist/vue-slick-carousel-theme.css'

export default {
    props: {
        muck: []
    },
    components: {
        VueSlickCarousel
    },
   


    data() {
        return {
            settings: { 
                    "dots": true,
                   "fade": true,
                  //  "lazyLoad": true,
                    "infinite": true,
                    "speed": 500,
                    "slidesToShow": 1,
                    "slidesToScroll": 1
            }
        }

    },

    methods: {
        onInit() {
                console.log(this.muck)
            },

        carouselSettings() {
            this.settings = 
            { 
                    "dots": true,
                 //  "fade": true,
                  //  "lazyLoad": true,
                    "infinite": true,
                    "speed": 500,
                    "slidesToShow": 1,
                    "slidesToScroll": 1
            }
            console.log("SETTINGS!!!!")
        }


        },

        
 /*   data () {
       return {
           index: 0
       }
    },
    methods: {
        previousCard() {
            if(this.index > 0) {
                this.index--
            }
            else {
                this.index = this.muck.length - 1
            }
        },
        nextCard() {
            if(this.index < this.muck.length-1){
                this.index++
            }
            else {
                this.index = 0
            }
        }
    } */
    
}
</script>

<style scoped>

#carousel {
    width: 300px;
}

.slick-slider {
    width: 230px
}

img {
    border-radius: 1rem
}


 /*   #card-container {
        width: 245px;
        height: 350px;

    }

    img {
        width: 245px;
        height: 350px;
        border-radius: 1rem;
    }

    #gallery-container {
        width: 300px;
        display: flex;
        justify-content: center;
        align-items: center
        
    } 

    .button {
        background-color: grey;
        color: white;
        width: 30px;
        height: 30px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        opacity: 70%;
    }

    .button:hover {
        opacity: 100%;
        cursor: pointer
    } */





</style>