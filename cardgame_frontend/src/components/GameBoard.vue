<template>
  <div>
    <ul>
  <li v-for="card in playedCards" :value="card" :key="card.id">
    {{ card.frontImage }}
  </li>
  </ul>
  </div>

</template>

<script>
export default {
  data() {
    return {
      playedCards: []
    };
  }
};
</script>