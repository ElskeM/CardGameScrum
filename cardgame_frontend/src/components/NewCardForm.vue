

<template>
  <form
    @submit="createCard"
    action="https://vuejs.org/"
    method="post"
  >
    <span>Title</span>
    <input v-model="cardData.title">
    <p>Message is: {{ cardData.title }}</p>

    <span>Description</span>
    <p style="white-space: pre-line;">{{ cardData.description }}</p>
    <br>
    
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
        title: "",
        score: ""
      }
    };
  },
  methods: {
    createCard(e) {
      console.warn(this.cardData);
      e.preventDefault();
      axios.post("http://localhost:8080/newCard",this.cardData).then((result)=>{console.warn(result)})
    }
  }
};
</script>