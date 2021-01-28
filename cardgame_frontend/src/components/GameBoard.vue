<template>
  <!-- https://learnvue.co/2020/01/how-to-add-drag-and-drop-to-your-vuejs-project/-->
  <div class="board">
    <div class="scrollbar">
      <draggable
        id="played-cards"
        class="card-holder"
        :class="{ correct: correctMove, wrong: wrongMove }"
        :disabled="allowPlay()"
        group="cards"
        v-bind="dragOptions"
        @end="onDrop"
        tag="ul"
      >
        <!-- Här hamnar de spelade korten. Är även en drag and drop-yta som bara blir aktiv om spelaren drar ett kort från handen. -->

        <transition-group type="transition" name="card-holder" id="played">
          <li
            v-for="card in playedCards"
            :value="card.id"
            :key="card.id"
            class="card"
          >
            <img class="card-image" :src="card.backImage" />
          </li>
        </transition-group>
      </draggable>
    </div>

    <div id="player-board">
      <draggable
        id="player-hand"
        class="card-holder"
        group="cards"
        @start="dragging = true"
        @end="onDrop"
        v-bind="dragOptions"
        tag="ul"
      >
        <!-- Korten i spelarens hand. Drag and drop-funktionen är bara aktiv om det är spelarens tur. -->
        <transition-group type="transition" name="card-holder" id="hand">
          <li
            v-for="card in playerHand"
            :value="card.id"
            :key="card.id"
            class="card"
          >
            <img class="card-image" :src="card.frontImage" />
          </li>
        </transition-group>
      </draggable>
      <div id="muck-card">
        <MiniCardGallery
          :muck="muck"
          id="mini-gallery"
          :playedCards="playedCards"
          ref="carousel"
        />
      </div>
    </div>
  </div>
</template>

<script>
import draggable from "vuedraggable";
import MiniCardGallery from "./MiniCardGallery.vue";

export default {
  components: {
    draggable,
    MiniCardGallery,
  },
  props: {
    playedCards: Array, //Spelade korten
    playerHand: Array, //Spelarens hand
    muck: Array, //Slänghögen, kort som lagts fel
  },
  data() {
    return {
      dragging: false, // Boolean som aktiverar funktionen att dra och släppa kort i playedCards
      playerTurn: false, // Indikerar on det är denna spelarens tur
      correctMove: false, // om true ger klassen correct till played-cards vilket startar en grön animation
      wrongMove: false, // om true ger klassen correct till played-cards vilket startar en röd animation
    };
  },
  methods: {
    async setCorrectMove(bool) {
      this.correctMove = bool;
      this.wrongMove = !bool;
    },

    allowPlay() {
      if (this.dragging && this.playerTurn) {
        return false;
      }
      return true;
    },
    onDrop(evt) {
      //Metod som körs när spelaren släpper kort på spelplanen. evt innehåller vilket kort och vilket index det släpps på
      console.log(evt);
      if (evt.to.getAttribute("id") == "played") {
        console.log(evt.newIndex); //Index i listan man lägger kortet
        console.log(evt.item.getAttribute("value")); //Hämtar det som är sparat i :value för  de släppta objektet. I vårat fall card.id.
        var move = {
          card: evt.item.getAttribute("value"),
          index: evt.newIndex,
        };
        this.playerHand.splice(evt.oldIndex, 1); //Tar bort kortet från spelarens hand
        console.log(move);
        this.$emit("moved", move); //Bubblar upp att ett move har gjorts
      }
      this.dragging = false;
    },
    setPlayerTurn(turn) {
      this.playerTurn = turn;
      if (turn) {
        this.correctMove = false;
        this.wrongMove = false;
      }
    },
  },
  computed: {
    dragOptions() {
      return {
        animation: 300,
        group: "description",
        disabled: false,
        ghostClass: "ghost",
      };
    },
  },
};
</script>

<style scoped>
#hand {
  min-width: 200px;
  min-height: 200px;
}

.card {
  display: inline-block;
  margin: 10px;
}
.card-image {
  border-radius: 1rem;
}

.board {
  display: grid;
}
.card-holder {
  text-align: center;
  white-space: nowrap;
  padding: 0em;
}

#played-cards {
  background-color: grey;
  width: fit-content;
  min-width: 100%;
}

.correct {
  animation-name: correctmove;
  animation-duration: 1s;
}

@keyframes correctmove {
  0% {
    background-color: grey;
  }
  50% {
    background-color: green;
  }
  100% {
    background-color: grey;
  }
}

.wrong {
  animation-name: wrongmove;
  animation-duration: 1s;
}

@keyframes wrongmove {
  0% {
    background-color: grey;
  }
  50% {
    background-color: red;
  }
  100% {
    background-color: grey;
  }
}

#player-board {
  width: 80%;
  display: flex;
  justify-content: center;
  margin-left: 20%;
}

#player-hand {
  width: 75%;
  margin-right: auto;
  /*margin-left: 20% */
}

#muck-card {
  width: 25%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.scrollbar {
  overflow-x: auto;
}

.ghost {
  opacity: 100;
  box-shadow: 10px 10px 5px -1px rgba(0, 0, 0, 0.14);
  border-radius: 1rem;
}

li {
  background-color: rgba(0, 0, 0, 0.14);
  border-radius: 1rem;
}
</style>
