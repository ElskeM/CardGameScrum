<template>
    <div v-bind:class="message.color">
        <div>{{message.name}}</div>
        <div>{{message.message}}</div>
    </div>
</template>

<script>
export default {
    props: ['message'],
}
</script>

<style scoped>

.blue {
    background-color: #62adb3
}

.green {
    background-color: #275258
}



</style>