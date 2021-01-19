<template>
  <!-- https://learnvue.co/2020/01/how-to-add-drag-and-drop-to-your-vuejs-project/-->
  <div class="board">
    <div class="scrollbar">
      <draggable
        class="played-cards card-holder"
        group="cards"
        :disabled="allowPlay()"
        @end="onDrop"
        id="played-cards"
      >
        <div
          v-for="card in playedCards"
          :value="card.id"
          :key="card.id"
          class="card"
        >
          <div>
            <img class="card-image" :src="card.backImage" />
          </div>
        </div>
      </draggable>
    </div>
    <div id="player-board"> 
      <draggable
        group="cards"
        class="card-holder"
        @start="dragging = true"
        @end="onDrop"
        v-bind="dragOptions"
        id="player-hand"
      >
        <transition-group type="transition" name="card-holder">
          <div
            v-for="card in playerHand"
            :value="card.id"
            :key="card.id"
            class="card list-group-item player-card"
          >
            <img class="card-image" :src="card.frontImage" />
          </div>
        </transition-group>
      </draggable>

      <div id="muck-card">
          <MiniCardGallery :muck="muck" id="mini-gallery" />
      </div>  
    </div>
  </div>
</template>

<script>
import draggable from "vuedraggable";
import MiniCardGallery from './MiniCardGallery.vue'
export default {
  components: {
    draggable,
    MiniCardGallery,
  },
  props: {
    playedCards: Array,
    playerHand: Array,
    muck: Array
  },
  data() {
    return {
      dragging: false, // Boolean som aktiverar funktionen att dra och släppa kort i playedCards
      playerTurn: false, // Indikerar on det är denna spelarens tur
    };
  },
  methods: {
    allowPlay() {
      if (this.dragging && this.playerTurn) {
        return false;
      }
      return true;
    },
    onDrop(evt) {
      //Metod som körs när spelaren släpper kort på spelplanen. evt innehåller vilket kort och vilket index det släpps på
      console.log(evt);
      if (evt.to.getAttribute("id") == "played-cards") {
        console.log(evt.newIndex); //Index i listan man lägger kortet
        console.log(evt.item.getAttribute("value")); //Hämtar det som är sparat i :value för  de släppta objektet. I vårat fall card.id.
        var move = {
          card: evt.item.getAttribute("value"),
          index: evt.newIndex,
        };
        this.playerHand.splice(evt.oldIndex, 1);
        console.log(move);
        this.$emit("moved", move);
      }
      this.dragging = false;
    },
    setPlayerTurn(turn) {
      console.log(turn);
      this.playerTurn = turn;
    },
  },
  computed: {
    dragOptions() {
      return {
        animation: 200,
        group: "description",
        disabled: false,
        ghostClass: "ghost",
      };
    },
  },
};
</script>

<style scoped>
.card {
  display: inline-block;
  margin-bottom: 10px;
  padding: 10px;
  transition: transform 0.5s;
}
/*.card-image {
  transition: transform 0.5s;
}
.drag-el {
  background-color: #fff;
  margin-bottom: 10px;
  padding: 5px;
}*/
.board {
  display: grid;
}
.card-holder {
  text-align: center;
  /*width: 100%;
  height: auto;
  overflow: auto;*/
  white-space: nowrap;
}
#played-cards {
  background-color: grey;
}

#player-board {
  width: 80%;
  display: flex;
  justify-content: center;
  margin-left: 20%
}

#player-hand {

  width: 75%;
  margin-right: auto
  /*margin-left: 20% */
}

#muck-card {
  width: 25%;
  display: flex;
  justify-content: center;
  align-items: center
}




.scrollbar {
  overflow-x: auto;
}
</style>