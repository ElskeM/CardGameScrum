<template>
    <div>
        <div class="chat-window">
            <div v-for="message in chatMessages" :key="message.id">
                <ChatMessage v-bind:message="message" class="chat-message"/>
            </div>
        </div>     
        <div>
            <input type="text" v-model="message"/>
            <input type="button" value="Send" @click="sendMessage">
        </div>
           


    </div>

    
</template>

<script>
 import ChatMessage from "./ChatMessage.vue";
export default {
   components: {
       ChatMessage
        },
    props: {
        playerName: String,
        chatMessageColor: String,
        chatMessages: Array
    },

    data() {
        return {
            message: ""
        }
    },
    methods: {
        sendMessage() {
            console.log("DENNA SPELARES NAMN")
            console.log(this.playerName)
            console.log("SLUT P ÅDENNA SPELARES NAMN")
            this.$emit('messageSent', {name: this.playerName, message:this.message, color:this.chatMessageColor})

        }
    }


    
}
</script>

<style scoped>
    .chat-window {
        width: 20%;
        height: 300px;
        display: flex;
       /* flex-direction: column;*/
        flex-direction: column-reverse;
        border: solid black 2px;
        overflow: scroll
    }

    .chat-message {
        width: 80%;
        border-radius: 5px;
        margin: 3px;

    }

</style>