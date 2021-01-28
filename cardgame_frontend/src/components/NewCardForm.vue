<template>
  <form id="newCardForm" @submit="createCard" method="post">
    <span>Title:</span><br />
    <input v-model="cardData.title" /><br />

    <span>Description:</span><br />
    <textarea v-model="cardData.description"></textarea>
    <br />
    <span>Category:</span>
    <br />
    <select v-model="cardData.category">
      <option
        v-for="category in formData.categories"
        :value="category"
        :key="category.id"
      >
        {{ category.category }}
      </option>
    </select>
    <br />
    <span>Score:</span><br />
    <input type="number" v-model="cardData.score" /><br /><br />

    <button type="submit">Send</button>
  </form>
</template>

<script>
import CardService from "../services/card.service";

export default {
  mounted() {
    CardService.fetchAllCategories().then(
      (categories) => (this.formData.categories = categories)
    );
  },
  data() {
    return {
      cardData: {
        title: null,
        score: null,
        description: null,
        category: null,
      },
      formData: {
        categories: [],
      },
    };
  },
  methods: {
    createCard(e) {
      e.preventDefault();
      CardService.createCard(this.cardData);
    },
  },
};
</script>
