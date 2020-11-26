

<template>
  <form
    @submit="createCard"
    action="https://vuejs.org/"
    method="post"
  >
    <span>Title</span>
    <input v-model="cardData.cardTitle">
    <p>Message is: {{ cardData.cardTitle }}</p>

    <span>Description</span>
    <p style="white-space: pre-line;">{{ cardData.description }}</p>
    <br>
    <textarea
      v-model="cardData.description"
      placeholder="add multiple lines"
    ></textarea>
    <br>
    <span>Score</span>
    <input
      type="text"
      v-model="cardData.score"
    >

    <button type="submit">Send</button>
  </form>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      cardData: {
        cardTitle: "",
        description: "",
        score: ""
      }
    };
  },
  methods: {
    createCard(e) {
      console.warn(this.cardData);
      e.preventDefault();
      axios.post("http://localhost:8080/newcard",this.cardData).then((result)=>{console.warn(result)})
    }
  }
};
</script>