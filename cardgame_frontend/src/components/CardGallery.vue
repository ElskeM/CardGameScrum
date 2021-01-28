<template>
  <div>
    <div class="gallery" :class="{ blurred: isBlurred }">
      <div
        @click="toggleBigCard"
        v-for="card in wholeCollection"
        :key="card.id"
      >
        <DisplayCard :card="card" @click="setBigCard" />
      </div>
    </div>
    <BigCardInfo
      :visible="isVisible"
      :bigCard="bigCard"
      @hide="toggleBigCard"
    />
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
    this.fetchFullDeck();
  },

  components: {
    DisplayCard,
    BigCardInfo,
  },

  data() {
    return {
      isVisible: false,
      isBlurred: false,
      bigCard: {},
    };
  },

  methods: {
    ...mapActions(["fetchFullDeck"]),

    setBigCard(card) {
      this.bigCard = card;
    },

    toggleBigCard() {
      this.isVisible = !this.isVisible;
      this.isBlurred = !this.isBlurred;
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
</style>
