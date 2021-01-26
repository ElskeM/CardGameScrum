<template>
    <div>
    <div class="chat-container">

     <div class="chat-border" v-bind:class="{ bottom: hideChat }">
                <p>Chatta</p>
                <div class="minimize-icon-container">
               <!--  <img class="minimize-icon"  @click="$emit('minimize')" src="../assets/minimize-window.png"/> -->
                    <div class="minimize-icon"  @click="$emit('minimize')">
                        <p>X</p>
                    </div>
                </div>
            </div>
        
        <div class="chat-window-and-input" v-bind:class="{ invisible: hideChat }">

            <div class="chat-window">
                <div v-for="message in chatMessages" :key="message.id">
                    <ChatMessage v-bind:message="message" class="chat-message" v-bind:class="{right: message.color=='blue'}"/>
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
            hideChat: false
        }
    },
    methods: {
        sendMessage() {
            console.log(this.message)
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
        background-color: #1d1f48;
        display:flex;
        align-items: center;
        border: white solid 3px;
        border-radius: 5px 5px 0px 0px
    }

    .minimize-icon-container {
        width: 30px;
        height: 30px;
        margin-left: auto;
        

    }

    .minimize-icon {
        width: 1.5rem;
        height: 1.5rem;
        border: solid white 2px;
        border-radius: 50%;
        display:flex;
        justify-content: center;
        align-items: center;
    }

    .minimize-icon:hover {
        cursor: pointer;
        background-color:  #2d3069
    }


    .chat-container {
        display: flex;
        flex-direction: column;
        width: 300px;
        height: 400px;
    }

    .chat-window {
        width: 100%;
        height: 300px;
        display: flex;
        flex-direction: column-reverse;
        border: solid black 2px;
        overflow:auto;
        background-color: white;
        border: solid 4px  #1d1f48

        
    }


    .chat-message {
        width: max-content;
        max-width: 200px;
        min-width: 100px;
        border-radius: 5px;
        margin: 3px;

    }

    .right {
        margin-left: auto;
    }

    .text-input-container {
        width:100%;
        height: 60px;
    }

    .text-area {
        height: 40px;
       width: 100%; 
      
        border-radius: 4px;
        resize: none;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 18px;
        
    }


    .invisible {
        display:none
    }

    .bottom {
       margin-top: auto;
    }


</style>