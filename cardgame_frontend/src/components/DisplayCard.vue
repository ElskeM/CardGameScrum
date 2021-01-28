<template>
    <div class="container">
      <img
        class="card"
        v-bind:class="{hover : hoverOn}"
        :src="card.frontImage"
        :href="card.frontImage"
        @click="$emit('click', card)"
      />
    </div>
</template>

<script>
export default {
  name: "DisplayCard",
  props: ["card", "hoverOn"],
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

.hover:hover {
  transform: scale(1.15);
}
</style>
