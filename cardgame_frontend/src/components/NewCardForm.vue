

<template>
  <form
  id="newCardForm"
    @submit="createCard"
    method="post"
  >
    <span>Title:</span><br>
    <input v-model="cardData.title"><br>

    <span>Description:</span><br>
    <textarea v-model="cardData.description"></textarea>
    <br>
    <span>Category:</span>
    <br>
    <select v-model="cardData.category">

      <option
        v-for="category in formData.categories"
        :value="category"
        :key="category.id"
      >
        {{ category.category }}
      </option>
    </select>
    <br>
    <span>Score:</span><br>
    <input
      type="number"
      v-model="cardData.score"
    ><br><br>

    <button type="submit">Send</button>
  </form>
</template>

<script>
import axios from "axios";

export default {
  async mounted() {
    axios.get("http://localhost:8080/categories").then(response => {
      console.warn(response);
      this.formData.categories = response.data.categories;
    });
  },
  data() {
    return {
      cardData: {
        title: null,
        score: null,
        description: null,
        category: null
      },
      formData: {
        categories: []
      }
    };
  },
  methods: {
    createCard(e) {
      console.warn(this.cardData);
      e.preventDefault();
      axios
        .post("http://localhost:8080/newCard", this.cardData)
        .then(result => {
          console.warn(result);
        });
    }
  }
};
</script>