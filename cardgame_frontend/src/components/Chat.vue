<template>
    <div>
    <div class="chat-container">

                    <div class="chat-border" v-bind:class="{ bottom: hideChat }">
                <p>Chatta</p>
                <div class="minimize-icon-container">
                <img class="minimize-icon" @click="hideChat = !hideChat" src="../assets/minimize-window.png"/>
                </div>
            </div>
        
        <div class="chat-window-and-input" v-bind:class="{ invisible: hideChat }">

            <div class="chat-window">
                <div v-for="message in chatMessages" :key="message.id">
                    <ChatMessage v-bind:message="message" class="chat-message"/>
                </div>
            </div>     
            <div class="text-input-container">
                <textarea class="text-area" v-model="message"  @keyup.enter.exact="sendMessage"></textarea>

            </div>
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
            message: "",
            hideChat: true
        }
    },
    methods: {
        sendMessage() {
            console.log("DENNA SPELARES NAMN")
            console.log(this.playerName)
            console.log("SLUT P ÅDENNA SPELARES NAMN")
            this.$emit('messageSent', {name: this.playerName, message:this.message, color:this.chatMessageColor})
            this.message = ""

        }
    }


    
}
</script>

<style scoped>


    .chat-border {
        height: 30px;
        width: 100%;
        background-color: lightblue;
        display:flex;
        align-items: center;
        border: blue solid 3px;
        border-radius: 5px 5px 0px 0px
    }

    .minimize-icon-container {
        width: 30px;
        height: 30px;
        margin-left: auto;
        

    }

    .minimize-icon {
        width: 100%;
    }


    .chat-container {
        display: flex;
        flex-direction: column;
        width: 20%;
        height: 400px;
        
        /*border: solid red 3px;*/
    }

    .chat-window {
        width: 100%;
        height: 300px;
        display: flex;
        flex-direction: column-reverse;
        border: solid black 2px;
        overflow:auto;
        
    }


    .chat-message {
        
        max-width: auto;
        border-radius: 5px;
        margin: 3px;

    }

    .text-input-container {
        width:100%;
        height: 60px
    }

    .text-area {
        height: 40px;
        width: 80%;
        border-radius: 4px;
        resize: none;
        font-family: Arial, Helvetica, sans-serif;
        
    }


    .invisible {
        display:none
    }

    .bottom {
       margin-top: auto;
    }


</style>