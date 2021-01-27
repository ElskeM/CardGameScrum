<template>
    <div class="container">
      <img
        class="card"
        :src="card.frontImage"
        :href="card.frontImage"
        @click="$emit('click', card)"
      />
    </div>
</template>

<script>
export default {
  name: "DisplayCard",
  props: ["card"],
};
</script>

<style scoped>
.container,
img {
  width: 150px;
  height: 250px;
}

.container {
  margin: 10px;
}

.card {
  transition: transform 0.2s;
  border-radius: 1rem;
}

.card:hover {
  transform: scale(1.15);
}
</style>
