<template>
    <div>
        <div class="number">{{this.number}}</div>
        <div class="number">{{this.playerNumber}}</div>
        <form action="post">
            <input type="text" name="play-num">
            <input type="submit" value="btn">
        </form>
    </div>
</template>


<script>
export default {
    data() {
        return {
            number: 10,
            playerNumber: ''
        }
    }
    
}
</script>

<style scoped>

</style>