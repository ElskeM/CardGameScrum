<template>
    <div id="dots">
        <span id="dot-1">•</span>
        <span id="dot-2">•</span>
        <span id="dot-3">•</span>  
    </div> 
</template>

<style  scoped>
    #dots {
        font-size: 60px;
    }

    #dot-1 {
        animation-name: dot-animation;
        animation-duration: 1s;
        animation-iteration-count: infinite;
        animation-delay: 0.33s;
        animation-timing-function: ease-out;    }

    #dot-2 {
        animation-name: dot-animation;
        animation-duration: 1s;
        animation-iteration-count: infinite;
        animation-delay: 0.66s;
        animation-timing-function: ease-out;
    }

    #dot-3 {
        animation-name: dot-animation;
        animation-duration: 1s;
        animation-iteration-count: infinite;
        animation-timing-function: ease-out;

    }
    
    @keyframes dot-animation {
        0%{opacity: 100%}
        50% {opacity: 0%}
        100%{opacity: 100%}
    }




</style>