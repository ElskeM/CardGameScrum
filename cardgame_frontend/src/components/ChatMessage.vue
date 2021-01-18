<template>
    <div v-bind:class="message.color" class="msg-container">
        <div class="name">{{message.name}}</div>
        <div class="message">{{message.message}}</div>
    </div>
</template>

<script>
export default {
    props: ['message'],
}
</script>

<style scoped>

.blue {
    background-color: #62adb3;

}

.green {
    background-color: #275258;
    
    
}

.name {
    font-size: 70%;
    font-weight: bold;
    font-family: Arial, Helvetica, sans-serif;
    color: white;
    margin-left: 5px;
    margin-top: 5px;

    

   
}

.message {
    margin: 5px;
    font-family: Arial, Helvetica, sans-serif;
    color:white;

}

.msg-container {
    display: flex;
    flex-direction: column;
    width: 60%
}





</style>