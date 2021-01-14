<template>
    <div>
        <div>
            <div v-for="message in chatMessages" :key="message.id">
                <ChatMessage v-bind:message="message"/>
            </div>
            <div>
                <input type="text" v-model="message"/>
                <input type="button" value="Send" @click="sendMessage">
            </div>
            <div>
                {{playerName}}
            </div>
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


</style>