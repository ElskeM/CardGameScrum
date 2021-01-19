<template>
    <div>
        <div id="gallery-container">
            <div id="previous_button" class="button" @click="nextCard">&lt;</div>
            <div id="card-container">
                <img id="card" :src="muck[index].backImage">
            </div>
        <div id="next-button" class="button" @click="previousCard">&gt;</div>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        muck: Array
    },
    data () {
       return {
           index: 0
       }
    },
    methods: {
        previousCard() {
            if(this.index > 0) {
                this.index--
            }
            else {
                this.index = this.muck.length - 1
            }
        },
        nextCard() {
            if(this.index < this.muck.length-1){
                this.index++
            }
            else {
                this.index = 0
            }
        }
    }
    
}
</script>

<style scoped>
    #card-container {
        width: 245px;
        height: 350px;

    }

    img {
        width: 245px;
        height: 350px
    }

    #gallery-container {
        width: 300px;
        display: flex;
        justify-content: center;
        align-items: center
        
    } 

    .button {
        background-color: grey;
        color: white;
        width: 30px;
        height: 30px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        opacity: 70%;
    }

    .button:hover {
        opacity: 100%;
        cursor: pointer
    }



</style>