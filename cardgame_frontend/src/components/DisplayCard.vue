<template>
    <div>
        <div class="container">
            <h3>{{this.card.title}}</h3>  
            <div>{{this.card.description}}</div>
            <div>{{this.card.score}}</div>

        </div>
    </div>
</template>

<script>
export default {
    name: 'DisplayCard',
    props: ['card']
}
</script>

<style scoped>

.container {
    border: 2px solid red;
    width: 150px;
    height: 250px;
    background: grey;

}
</style>