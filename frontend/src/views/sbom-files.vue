<script setup>
import Thumbnail from "@/components/thumbnail.vue";
import {onMounted, ref} from "vue";
import {retrieveSbomMetadata} from "@/utils/api";
import SbomFilesTable from "@/components/sbom-files-table.vue";
import Disclamer from "@/components/disclamer.vue";

const data = ref([])
const thumb = ref(new Map())

onMounted(() => {
  thumb.value.set('All SBOMs', '-')
  fetchSbomMetadata()
})

async function fetchSbomMetadata() {
  retrieveSbomMetadata().then(response => {
    data.value = response
    thumb.value.set('All SBOMs', response.length)
  })
}
</script>

<template>
  <thumbnail title="SBOM List" :map="thumb"/>
  <div class="container">
    <disclamer/>
    <sbom-files-table :data="data" class="mt-3"/>
  </div>
</template>

<style scoped>

</style>