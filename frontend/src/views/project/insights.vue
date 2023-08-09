<script setup>
import {onBeforeMount, ref} from "vue";
import {useRoute} from "vue-router";
import SbomInsightsCard from "@/components/sbom-insights-card.vue";
import {retrieveSbomMetadata, retrieveSpdxInsightsForProject} from "@/utils/api";

onBeforeMount(() => {
  fetchMetadata()
  fetchSpdxInsights()
})

function fetchMetadata() {
  retrieveSbomMetadata().then(response => {
    const project_id = parseInt(route.params.project_id)
    metadata.value = response.filter(x => x.project_id === project_id).sort((x, y) => (x.mode + x.generator).localeCompare(y.mode + y.generator))
    dataLoaded.value = true;
  })
}

function fetchSpdxInsights() {
  retrieveSpdxInsightsForProject(route.params.project_id).then(response => {
    spdxInsights.value = response
  })
}

const route = useRoute()
const metadata = ref()
const spdxInsights = ref()
const dataLoaded = ref(false);
</script>

<template>
  <div class="container">
    <div v-for="item of spdxInsights">
      <sbom-insights-card :item="item" :title="item.mode + ' ' + item.generator"/>
    </div>

  </div>
</template>

<style scoped>

</style>