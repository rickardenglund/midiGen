<template>
  <div class="hello">
    <h1>Midi Devices</h1>
    <ul>
      <li v-bind:class="{ active: device.name === activeDevice.name }" v-for="(device, key) in devices" :key="key" v-on:click="setDevice(key)">
        {{device.name}}
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "MidiList",
  data() {
    return {
      devices: [],
      activeDevice: {}
    };
  },
  methods: {
    setDevice: function(key) {
      axios({
        method: "GET",
        url: "http://pi:8080/set/device?id=" + key
      }).then(
        result => {
          this.activeDevice = result.data;
        },
        error => {
          console.error(error);
        }
      );
    }
  },
  mounted() {
    axios({ method: "GET", url: "http://pi:8080/list" }).then(
      result => {
        console.log(result);
        this.devices = result.data;
      },
      error => {
        console.error(error);
      }
    );

    axios({ method: "GET", url: "http://pi:8080/get/midiDevice" }).then(
      result => {
        this.activeDevice = result.data;
      },
      error => {
        console.error(error);
      }
    );
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
