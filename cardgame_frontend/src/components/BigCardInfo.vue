<template>
  <div class="outermost-container" v-show="visible">
    <div class="outer-container" @click="leave">
      <div class="container">
        <div class="flip-container" @click.stop>
          <vue-flip active-click v-model="isFlipped" height="400px" width="75%">
            <template v-slot:front>
              <div class="img">
                <img class="card" :src="bigCard.frontImage" />
              </div>
            </template>
            <template v-slot:back>
              <div class="img">
                <img class="card" :src="bigCard.backImage" />
              </div>
            </template>
          </vue-flip>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import VueFlip from "vue-flip";

export default {
  data() {
    return {
      isFlipped: false,
    };
  },

  components: {
    "vue-flip": VueFlip,
  },

  props: { bigCard: Object, visible: Boolean },

  methods: {
    leave() {
            console.log("LEEEEAVE")

      this.$emit("hide");
      this.isFlipped = false;
      this.visible = false;
    },
  },
};
</script>

<style scoped>
.outermost-container {
  position: fixed;
  top: 50%;
  left: 50%;
  margin-right: -50%;
  transform: translate(-50%, -50%);
}

.outer-container {
  width: 100%;
  height: 100%;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: -1;
}

.container {
  height: 500px;
  width: 300px;
  background: white;
  border-radius: 3rem;
  display: flex;
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
  align-items: center;
}

img {
  border-radius: 1rem;
  width: 100%;
  height: 100%;
  border: 1px solid black;
}
</style>
