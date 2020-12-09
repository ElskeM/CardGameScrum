<template>
  <!-- https://learnvue.co/2020/01/how-to-add-drag-and-drop-to-your-vuejs-project/-->
  <div class="board">
    <div>
      <draggable
        class="played-cards"
        group="cards"
        :disabled="allowPlay()"
        @end="onDrop"
      >
        <div
          v-for="card in playedCards"
          :value="card.id"
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
          :value="card.id"
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
  props: {
    playedCards: Array,
    playerHand: Array
  },
  data() {
    return {
      dragging: false, // Boolean som aktiverar funktionen att dra och släppa kort i playedCards
      playerTurn: false // Indikerar on det är denna spelarens tur
    };
  },
  methods: {
    allowPlay(){
        if(this.dragging&&this.playerTurn){
          return false;
        }
        return true
    },
    onDrop(evt) {
      //Metod som körs när spelaren släpper kort på spelplanen. evt innehåller vilket kort och vilket index det släpps på
      console.log(evt.to.getAttribute("class"));
      if (evt.to.getAttribute("class") == "played-cards") {
        console.log(evt.newIndex); //Index i listan man lägger kortet
        console.log(evt.item.getAttribute("value")); //Hämtar det som är sparat i :value för  de släppta objektet. I vårat fall card.id.
        var move = {"card":evt.item.getAttribute("value"),"index":evt.newIndex}
        console.log(move)
        this.$emit('moved', move)
      }
      this.dragging = false;
    },
    setPlayerTurn(turn){
      console.log(turn)
      this.playerTurn=turn
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
.card-image {
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