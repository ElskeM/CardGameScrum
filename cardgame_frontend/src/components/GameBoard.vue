<template>
  <!-- https://learnvue.co/2020/01/how-to-add-drag-and-drop-to-your-vuejs-project/-->
  <div class="board">
    <div>
      <draggable
        class="played-cards"
        group="cards"
        :disabled="!dragging"
        @end="onDrop"
      >
        <div
          v-for="card in playedCards"
          :value="card"
          :key="card.id"
          class="card"
        ><img
            class="card-image"
            :draggable="false"
            :src="card.backImage"
          ></div>
      </draggable>
    </div>
    <draggable
      group="cards"
      @start="dragging=true"
      @end="onDrop"
      
      v-bind="dragOptions"
    >
      <transition-group
        type="transition"
        name="card-holder"
      >
        <div
          v-for="card in playerHand"
          :value="card"
          :key="card.id"
          class="card list-group-item"
        >
          <img
            class="card-image"
            :src="card.frontImage"
          >
        </div>
      </transition-group>
    </draggable>

  </div>

</template>

<script>
import draggable from "vuedraggable";
export default {
  components: {
    draggable
  },
  props:{
playedCards:Array,
playerHand:Array
  },
  data() {
    return {
      dragging: false,
      
    };
  },
  methods: {
    onDrop(evt) {
      //Metod som körs när spelaren släpper kort på spelplanen. evt innehåller vilket kort och vilket index det släpps på
      console.log(evt);
      this.dragging=false;
    }
  },
  computed: {
    dragOptions() {
      return {
        animation: 200,
        group: "description",
        disabled: false,
        ghostClass: "ghost"
      };
    }
  }
};
</script>

<style scoped>
.card {
  float: left;
  margin-bottom: 10px;
  padding: 10px;
  transition: transform 0.5s;
}
.card-image{
  transition: transform 0.5s;
}
.board {
  display: grid;
}
.drag-el {
  background-color: #fff;
  margin-bottom: 10px;
  padding: 5px;
}
.played-cards {
  width: 100%;
  height: 10em;
  background-color: grey;
}
.card-holder-move {
  transition: transform 0.5s;
}
.card-holder-enter-active {
  transition: all 200ms ease-out;
}
.card-holder-leave-active {
  transition: 0.2s opacity ease-out;
}
.no-move {
  transition: transform 0.5s;
}
</style>