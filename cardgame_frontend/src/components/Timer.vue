<template>
  <!--https://medium.com/js-dojo/how-to-create-an-animated-countdown-timer-with-vue-89738903823f-->
  <div id="timer-box">
    <div class="base-timer">
      <svg
        class="base-timer__svg"
        viewBox="0 0 100 100"
        xmlns="http://www.w3.org/2000/svg"
      >
        <g class="base-timer__circle">
          <circle class="base-timer__path-elapsed" cx="50" cy="50" r="46.5" />
          <path
            :class="remainingPathColor"
            :stroke-dasharray="circleDasharray"
            class="base-timer__path-remaining"
            d="
            M 50, 50
            m -45, 0
            a 45,45 0 1,0 90,0
            a 45,45 0 1,0 -90,0
          "
          ></path>
        </g>
      </svg>
      <span class="base-timer__label">
        {{ timeLeft }}
      </span>
    </div>
    <span id="timer-info">
      <p v-if="this.playerTurn" :class="remainingPathColor">DIN TUR</p>
      <p v-else>MOTSTÅNDARENS TUR</p>
      Missade rundor: {{ this.missedTurns }}<br />
      Tre missade rundor i rad<br />resulterar i förlust!
    </span>
  </div>
</template>

<script>
const FULL_DASH_ARRAY = 283; //Kanske den här som behövr vara större för att färgringen ska täcka grå
const WARNING_THRESHOLD = 20;
const ALERT_THRESHOLD = 10;

export default {
  data() {
    return {
      timerInterval: null,
      timeLimit: 40,
      timePassed: 0,
    };
  },
  props: {
    playerTurn: Boolean,
    missedTurns: {
      type: Number,
      required: true,
    },
  },
  computed: {
    timeLeft() {
      return this.timeLimit - this.timePassed;
    },
    // Update the dasharray value as time passes, starting with 283
    circleDasharray() {
      return `${(this.timeFraction * FULL_DASH_ARRAY).toFixed(0)} 283`;
    },
    timeFraction() {
      // Divides time left by the defined time limit.
      const rawTimeFraction = this.timeLeft / this.timeLimit;

      return rawTimeFraction - (1 / this.timeLimit) * (1 - rawTimeFraction);
    },
    colorCodes() {
      return {
        info: {
          color: "green",
        },
        warning: {
          color: "orange",
          threshold: WARNING_THRESHOLD,
        },
        alert: {
          color: "red",
          threshold: ALERT_THRESHOLD,
        },
      };
    },

    remainingPathColor() {
      const { alert, warning, info } = this.colorCodes;
      if (this.timeLeft <= alert.threshold) {
        return alert.color;
      } else if (this.timeLeft <= warning.threshold) {
        return warning.color;
      } else {
        return info.color;
      }
    },
  },

  methods: {
    startTimer() {
      this.timerInterval = setInterval(() => (this.timePassed += 1), 1000);
    },
    resetAndStartTimer() {
      this.timePassed = 0;
      if (!this.timerInterval) {
        this.startTimer();
      }
    },
    stopTimer() {
      clearInterval(this.timerInterval);
      this.timerInterval = null;
    },
  },
};
</script>

<style scoped>
#timer-box {
  display: table;
}
#timer-info {
  display: table-cell;
  vertical-align: middle;
  padding-left: 10px;
}
/* Sets the containers height and width */
.base-timer {
  position: relative;
  width: 95px;
}
/* Removes SVG styling that would hide the time label */
.base-timer__circle {
  fill: none;
  stroke: none;
}
/* The SVG path that displays the timer's progress */
.base-timer__path-elapsed {
  stroke-width: 7px;
  stroke: grey;
}
.base-timer__label {
  position: absolute;

  /* Size should match the parent container */
  width: 95px;
  height: 95px;
  /* Keep the label aligned to the top */
  top: 0;
  /* Create a flexible box that centers content vertically and horizontally */
  display: flex;
  align-items: center;
  justify-content: center;
  /* Sort of an arbitrary number; adjust to your liking */
  font-size: 48px;
}
.base-timer__path-remaining {
  /* Just as thick as the original ring */
  stroke-width: 7px;
  /* Rounds the line endings to create a seamless circle */
  stroke-linecap: round;
  /* Makes sure the animation starts at the top of the circle */
  transform: rotate(90deg);
  transform-origin: center;
  /* One second aligns with the speed of the countdown timer */
  transition: 1s linear all;
  /* Allows the ring to change color when the color value updates */
  fill-rule: nonzero;
  stroke: currentColor;
}
.base-timer__path-remaining.green {
  color: rgb(65, 184, 131);
}
.base-timer__path-remaining.orange {
  color: orange;
}
.base-timer__path-remaining.red {
  color: red;
}
.base-timer__svg {
  /* Flips the svg and makes the animation to move left-to-right*/
  transform: scaleX(-1);
}
</style>
