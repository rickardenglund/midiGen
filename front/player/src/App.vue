<template>
  <div id="app">
    <player />
    <MidiList />
  </div>
</template>

<script>
import MidiList from './components/MidiList.vue';
import Player from './components/Player.vue';

export default {
  name: 'app', 
  components: {
    MidiList,
    Player
  }, 
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
