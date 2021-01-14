<template>
    <div>
        <div>
            <div v-for="message in chattMessages" :key="message.id">
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
        chatMessages: Array
    },

    data() {
        return {
            message: "",

            chattMessages: [
                {
                    name: "Pontus",
                    message: "Hej",
                    id: 0
                },
                {
                    name: "Lasse",
                    message: "Hej hej",
                    id: 1
                }
            ]
        }
    },
    methods: {
        sendMessage() {
            console.log("DENNA SPELARES NAMN")
            console.log(this.playerName)
            console.log("SLUT P ÅDENNA SPELARES NAMN")
            this.$emit('messageSent', {name: this.playerName, message:this.message})

        }
    }


    
}
</script>

<style scoped>


</style>