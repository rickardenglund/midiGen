<template>
  <div class="hello">
    <svg  width="500px" height="120px">
      <line v-for="i in 5" :key="i + 500" x1="0" :y1="i*20" x2="500" :y2="i*20" style="stroke:rgb(0,0,0);stroke-width:2" />
      <circle v-for="(note, key) in notes" :key="key" :cx="50*key + 10" :cy="110-(toLineHeight(note.pitch))*10" r="10" stroke-width="1" fill="blue" />
    </svg>
    <p v-for="(note, key) in notes" :key="key + 200"> {{note.pitch}} : {{toLineHeight(note.pitch)}}</p>
  </div>
</template>

<script>
export default {
  name: "ShowNotes",
  data() {
    return {};
  },
  props: ["notes"],
  methods: {
    toLineHeight: function(pitch) {
      var note = pitch % 60;
      var lineHeightMap = [-1, -1, 0, 0, 1, 2, 2, 3, 3, 4, 4, 5];
      var octave = Math.floor(note / 12);
      return lineHeightMap[note % 12] + 7 * octave;
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

.active {
  font-weight: bold;
}
</style>
