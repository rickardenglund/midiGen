<template>
  <div class="player">
    <h1>Player</h1>
    <button v-on:click="play">play</button>
    <button v-on:click="newSequence">new</button>
    <button v-on:click="showNotes = !showNotes">{{showNotesBtnText}}</button>
    

    <p v-if="showNotes" v-for="(note, key) in notes" :key="key">{{note.pitch}}</p>
  <div> 
    <button v-on:click="playScale">play scale</button>
    <select v-model="currentScale">
      <option v-for="i in 12" :key="i" :value="i"> {{i}}</option>
    </select>
  </div>
  <p>{{this.currentScale}}</p>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Player",
  data() {
    return {
      notes: [],
      showNotes: false,
      currentScale: 'apa'
    };
  },
  methods: {
    play: function(event) {
      axios({ method: "GET", url: "http://pi:8080/play" }).then(
        result => {
          this.notes = result.data;
        },
        error => {
          console.error(error);
        }
      );
    },
    playScale: function(event) {
      axios({ method: "GET", url: "http://pi:8080/play/scale?offset=" + (this.currentScale-1) }).then(
        result => {
          this.notes = result.data;
        },
        error => {
          console.error(error);
        }
      );
    },
    newSequence: function(event) {
      axios({ method: "GET", url: "http://pi:8080/new" }).then(
        result => {
          this.notes = result.data;
        },
        error => {
          console.error(error);
        }
      );
    }
  },
  mounted() {},
  computed: {
    showNotesBtnText: function() {
      return this.showNotes ? "Hide" : "Show";
    }
  }
};
</script>

<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
