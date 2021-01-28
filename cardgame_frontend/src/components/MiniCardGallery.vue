<template>
  <div>
    <!-- karusellen vill inte starta upp om den innehåller färre än två objekt, därav v-if:en  -->
    <VueSlickCarousel v-if="muck.length > 1" :settings="settings" id="carousel">
      <div v-for="card in muck" :key="card.id">
        <img :src="card.backImage" />
      </div>
    </VueSlickCarousel>
    <!-- det första kortet visas istället som en vanlig img -->
    <img v-else-if="muck.length === 1" :src="muck[0].backImage" />
    <div
      v-else-if="muck.length === 0 && playedCards.length > 0"
      id="muck-platshållare"
    >
      <p>Kort som lagts på fel position</p>
    </div>
  </div>
</template>

<script>
import VueSlickCarousel from "vue-slick-carousel";
import "vue-slick-carousel/dist/vue-slick-carousel.css";
// optional style for arrows & dots
import "vue-slick-carousel/dist/vue-slick-carousel-theme.css";

export default {
  props: {
    muck: Array,
    playedCards: Array,
  },
  components: {
    VueSlickCarousel,
  },

  data() {
    return {
      settings: {
        dots: true,
        fade: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
      },
    };
  },
};
</script>

<style scoped>
#carousel {
  width: 300px;
}

.slick-slider {
  width: 230px;
}

img {
  border-radius: 1rem;
}

#muck-platshållare {
  border: solid white 5px;
  width: 200px;
  height: 300px;
  border-radius: 1rem;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #1d1f48;
}

#muck-platshållare p {
  text-align: center;
  padding: 3px;
  font-size: 25px;
}
</style>
