<template>
  <div>
    <div class="gallery" v-bind:class="{ blurred: isBlurred }">
      <div
        @click="showBigCard"
        class="card"
        v-for="card in wholeCollection"
        :key="card.id"
      >
        <DisplayCard v-bind:card="card" v-on:displaycard-clicked="setBigCard" />
      </div>
    </div>
    <div
      class="big-card"
      v-bind:class="{ visible: isVisible, invisible: isInvisible }"
    >
      <BigCardInfo :bigCard="bigCard" v-on:left="hideBigCard" />
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import DisplayCard from "./DisplayCard.vue";
import BigCardInfo from "./BigCardInfo.vue";

export default {
  name: "CardGallery",
  computed: { ...mapGetters(["wholeCollection", "numberOfCards"]) },
  created() {
    this.fetchFullDeck().then((fulldeck) => {
      if (fulldeck.length === 0) {
        this.showError();
      }
    });
  },

  components: {
    DisplayCard,
    BigCardInfo,
  },

  data() {
    return {
      isVisible: false,
      isBlurred: false,
      isInvisible: true,
      bigCard: "",
    };
  },
  methods: {
    ...mapActions(["fetchFullDeck"]),

    showError() {
      this.$toasted.show(
        "Warning: No cards in database<br>Is the backend set-up correctly?",
        {
          position: "bottom-center",
          type: "error",
          singleton: true,
          duration: 5000,
        }
      );
    },

    setBigCard(card) {
      this.bigCard = card;
      console.log(this.bigCard.score);
    },

    showBigCard() {
      console.log("trycker showBigCard");
      this.isVisible = !this.isVisible;
      this.isBlurred = !this.isBlurred;
      this.isInvisible = !this.isInvisible;
      console.log(this.isVisible);
    },

    hideBigCard() {
      this.isVisible = !this.isVisible;
      this.isBlurred = !this.isBlurred;
      this.isInvisible = !this.isInvisible;
    },
  },
};
</script>

<style scoped>
.gallery {
  width: 100%;
  height: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.blurred {
  -webkit-transition: 0.5s -webkit-filter linear;
  transition: 0.5s -webkit-filter linear;
  -webkit-filter: blur(5px);
  filter: blur(5px);
}

.card {
  margin: 10px;
}

.big-card {
  position: fixed;
  top: 50%;
  left: 50%;
  margin-right: -50%;
  transform: translate(-50%, -50%);
  visibility: hidden;
  width: 100%;
  height: 100%;
}

.invisible {
  opacity: 0;
}

.visible {
  visibility: visible;
}
</style>
