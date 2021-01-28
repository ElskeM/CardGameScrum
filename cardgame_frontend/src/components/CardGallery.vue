<template>
  <div>
    <div class="gallery" :class="{ blurred: isBlurred }">
      <div
        @click="toggleBigCard"
        v-for="card in wholeCollection"
        :key="card.id"
      >
        <DisplayCard :card="card" @click="setBigCard" :hoverOn="hoverOn" />
      </div>
    </div>
    <BigCardInfo
      :visible="isVisible"
      :bigCard="bigCard"
      ref="bigcard"
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
      hoverOn: true
      
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
      this.hoverOn = !this.hoverOn
      this.$refs.bigcard.leave()

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
