<template>
  <!-- https://learnvue.co/2020/01/how-to-add-drag-and-drop-to-your-vuejs-project/-->
  <div>
    <ul
      class="card-area"
      @drop='onDrop($event)'
      @dragover.prevent
      @dragenter.prevent
    >
      <li
        v-for="card in playedCards"
        :value="card"
        :key="card.id"
        class="card"
      >
        {{ card.backImage }}
      </li>
    </ul>
    <ul class="card-area">
      <li
        v-for="card in playerHand"
        :value="card"
        :key="card.id"
        class="card"
        draggable
        @dragstart='startDrag($event, card)'
      >
        {{ card.frontImage }}
      </li>
    </ul>
  </div>

</template>

<script>
export default {
  data() {
    return {
      playedCards: [
        {
          id:0,
          title: "Blandkost",
          subtitle: "Svenskt genomsnitt",
          description: "1 års mat för en genomsnittlig svensk",
          frequence: 365,
          score: 2000,
          extraInfo: "Utsläpp från nöttkött: 45 %, mjölkprodukter: 25%",
          category: "Livsmedel",
          frontImage: "/images/Kort1_front.jpg",
          backImage: "/images/Kort1_back.jpg"
        },
        {
          id:1,
          title: "Blandkost",
          subtitle: "Frigående kyckling",
          description:
            "1års mat för en genomsnittlig svensk, men allt kött är från frigående kyckling",
          frequence: 365,
          score: 1000,
          extraInfo: "Utsläpp från kyckling: 20%",
          category: "Livsmedel",
          frontImage: "/images/Kort2_front.jpg",
          backImage: "/images/Kort2_back.jpg"
        }
      ],
      playerHand: [
        {
          id:2,
          title: "Vegetarisk kost",
          subtitle: null,
          description:
            "1 års mat för en genomsnittlig svensk vegetarian, protein från växter, ägg och mjölkprodukter",
          frequence: 365,
          score: 900,
          extraInfo: "Utsläpp från mjölkprodukter: 50%",
          category: "Livsmedel",
          frontImage: "/images/Kort3_front.jpg",
          backImage: "/images/Kort3_back.jpg"
        },
        {
          id:3,
          title: "Vegansk kost",
          subtitle: null,
          description:
            "1 års mat för en genomsnittlig svensk vegan, med protein från växter",
          frequence: 365,
          score: 500,
          extraInfo: null,
          category: "Livsmedel",
          frontImage: "/images/Kort4_front.jpg",
          backImage: "/images/Kort4_back.jpg"
        }
      ],
      startDrag: (evt, card) => {
    evt.dataTransfer.dropEffect = "move";
    evt.dataTransfer.effectAllowed = "move";
    evt.dataTransfer.setData("cardId", card.id);
  },
  onDrop(evt) {
    const cardId = evt.dataTransfer.getData("cardId");
    const item = this.playerHand.find(card => card.id == cardId);
    this.playedCards.push(item);
    this.playerHand.splice(this.playerHand.indexOf(item),1);
  }
    };
  },
  
};
</script>

<style scoped>
.drop-zone {
  background-color: #eee;
  margin-bottom: 10px;
  padding: 10px;
}

.drag-el {
  background-color: #fff;
  margin-bottom: 10px;
  padding: 5px;
}
</style>