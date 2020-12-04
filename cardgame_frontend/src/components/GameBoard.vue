<template>
  <!-- https://learnvue.co/2020/01/how-to-add-drag-and-drop-to-your-vuejs-project/-->
  <div class="board">
    <div>
      <draggable
        class="played-cards"
        group="cards"
        :disabled="!dragging"
        @start="drag=true"
        @end="onDrop"
        :move="onMoveCallback"
      >
        <div
          v-for="card in playedCards"
          :value="card"
          :key="card.id"
          class="card"
          :move="onMoveCallback"
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
      @end="dragging=false"
      
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
//playedCards:Array och playerHand:Array
  },
  data() {
    return {
      dragging: false,
      playedCards: [
        {
          id: 0,
          title: "Blandkost",
          subtitle: "Svenskt genomsnitt",
          description: "1 års mat för en genomsnittlig svensk",
          frequence: 365,
          score: 2000,
          extraInfo: "Utsläpp från nöttkött: 45 %, mjölkprodukter: 25%",
          category: "Livsmedel",
          frontImage: "http://localhost:8080/images/Kort1_front.jpg",
          backImage: "http://localhost:8080/images/Kort1_back.jpg"
        },
        {
          id: 1,
          title: "Blandkost",
          subtitle: "Frigående kyckling",
          description:
            "1års mat för en genomsnittlig svensk, men allt kött är från frigående kyckling",
          frequence: 365,
          score: 1000,
          extraInfo: "Utsläpp från kyckling: 20%",
          category: "Livsmedel",
          frontImage: "http://localhost:8080/images/Kort2_front.jpg",
          backImage: "http://localhost:8080//images/Kort2_back.jpg"
        }
      ],
      playerHand: [
        {
          id: 2,
          title: "Vegetarisk kost",
          subtitle: null,
          description:
            "1 års mat för en genomsnittlig svensk vegetarian, protein från växter, ägg och mjölkprodukter",
          frequence: 365,
          score: 900,
          extraInfo: "Utsläpp från mjölkprodukter: 50%",
          category: "Livsmedel",
          frontImage: "http://localhost:8080/images/Kort3_front.jpg",
          backImage: "http://localhost:8080/images/Kort3_back.jpg"
        },
        {
          id: 3,
          title: "Vegansk kost",
          subtitle: null,
          description:
            "1 års mat för en genomsnittlig svensk vegan, med protein från växter",
          frequence: 365,
          score: 500,
          extraInfo: null,
          category: "Livsmedel",
          frontImage: "http://localhost:8080/images/Kort4_front.jpg",
          backImage: "http://localhost:8080/images/Kort4_back.jpg"
        }
      ]
    };
  },
  methods: {
    startDrag: (evt, card) => {
      evt.dataTransfer.dropEffect = "move";
      evt.dataTransfer.effectAllowed = "move";
      evt.dataTransfer.setData("cardId", card.id);
    },
    onMoveCallback: function(evt) {
      console.log("HEELLOO! " + evt);
      return false;
    },
    onDrop(evt) {
      console.log(evt);
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