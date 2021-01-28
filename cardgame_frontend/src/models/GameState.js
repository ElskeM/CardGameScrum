export default class GameState {
    constructor() {
      this.player = {};
      this.player.playerHand = [];
      this.player.missedTurns = NaN;
      this.table = [];
      this.muck = [];
    }
  }