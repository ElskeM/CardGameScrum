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
        animation-name: dot1-animation;
        animation-duration: 1s;
        animation-iteration-count: infinite;
        animation-timing-function: ease-in-out;
    }

    @keyframes dot1-animation {
        0%{opacity: 100%}
        50% {opacity: 0%}
        100%{opacity: 100%}
    }

    #dot-2 {
        animation-name: dot2-animation;
        animation-duration: 1s;
        animation-iteration-count: infinite;
        animation-delay: 0.05s;
        animation-timing-function: ease-in-out;

    }

    @keyframes dot2-animation {
        0%{opacity: 100%}
        50% {opacity: 0%}
        100%{opacity: 100%}
    }

    #dot-3 {
        animation-name: dot3-animation;
        animation-duration: 1s;
        animation-iteration-count: infinite;
        animation-delay: 0.1s;
        animation-timing-function: ease-in-out;

    }

    @keyframes dot3-animation {
        0%{opacity: 100%}
        50% {opacity: 0%}
        100%{opacity: 1000%}
    }


</style>