<template>
    <div>
<!--       <div id="gallery-container" v-if="this.muck&&this.muck.length" >
            <div id="previous_button" class="button" @click="nextCard">&lt;</div>
            <div id="card-container">
                <img id="card" :src="muck[index].backImage">
            </div>
        <div id="next-button" class="button" @click="previousCard">&gt;</div>
        </div>  -->
    <VueSlickCarousel @init="onInit" :settings="settings" id="carousel">
        <div v-for="card in muck" :key="card.id">
           <img :src="card.backImage">
        </div>
 <!--     <div>1</div>
      <div>2</div>
      <div>3</div>
      <div>4</div> -->
    </VueSlickCarousel>
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