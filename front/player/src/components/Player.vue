<template>
  <div class="player">
    <h1>Player</h1>
    <button v-on:click="play">play</button>
    <button v-on:click="newSequence">new</button>
    <button v-on:click="showNotes = !showNotes">{{showNotesBtnText}}</button>
    

    <ShowNotes v-if="showNotes" :notes="notes"></ShowNotes>
  <div> 
    <button v-on:click="playScale">play scale</button>
    <select v-model="currentScale">
      <option v-for="i in 12" :key="i" :value="i"> {{toNoteName(i)}}</option>
    </select>
  </div>
  <p>scale: {{toNoteName(this.currentScale)}}</p>
  </div>
</template>

<script>
import axios from "axios";
import ShowNotes from './ShowNotes.vue';
export default {
  name: "Player",
  components: {
    ShowNotes
  },
  data() {
    return {
      notes: [],
      showNotes: false,
      currentScale: 0
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
      axios({ method: "GET", url: "http://pi:8080/play/scale?offset=" + (this.currentScale) }).then(
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
    },
    toNoteName: function(index) {
      var notes = ['C', 'C#', 'D', 'D#', 'E', 'F', 'F#', 'G', 'G#', 'A', 'A#', 'B#', 'C'];
      return notes[index];
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
