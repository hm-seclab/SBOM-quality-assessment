<script setup>
import {ref} from "vue";
  import * as AsciinemaPlayer from "asciinema-player";
  import 'asciinema-player/dist/bundle/asciinema-player.css'

  const props = defineProps(['metadata'])

  function loadAsciinemaData() {
    let url = window.location.origin +
              '/api/downloadShellLogFile?sbom_file_id=' +
              props.metadata.sbom_file_id
    console.log(url)
    AsciinemaPlayer.create({url: url}, document.getElementById('asciinema'));
  }

  const visible = ref(false);
</script>

<template>
  <p-button label="View Logs" icon="pi pi-list" style="width: 100%" @click="visible = true" @focusout="loadAsciinemaData()" outlined rounded :disabled="!metadata.log_exists"/>
  <p-dialog v-model:visible="visible" modal :header="'Shell Logs: ' + metadata.name + ' ' + metadata.mode + ' ' + metadata.generator" :style="{ width: '50vw' }">
    <div class="flex justify-content-between m-2">
      <b>Command:</b>
      <a :href="'/api/downloadShellLogFile?sbom_file_id=' + props.metadata.sbom_file_id" download>
        <p-button label="Download Log" icon="pi pi-download" outlined rounded/>
      </a>
    </div>
    <div class="bg-bluegray-100 border-round-lg command">
      <p style="margin: 15px">{{ metadata.command }}</p>
    </div>
    <br/>
    <b>Logs:</b>
    <div id="asciinema"></div>
  </p-dialog>
</template>

<style>
  .command {
    width: 100%;
    display: flex;
    font-family: 'Courier New';
  }
</style>