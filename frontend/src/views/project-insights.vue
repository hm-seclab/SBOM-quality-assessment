<script setup>
import ProjectMetadata from "@/components/project-metadata.vue";
import Thumbnail from "@/components/thumbnail.vue";
import {onBeforeMount, ref} from "vue";
import {useRoute} from "vue-router";
import SbomInsightsCard from "@/components/sbom-insights-card.vue";
import SbomFilesTable from "@/components/sbom-files-table.vue";
import {retrieveSbomMetadata, retrieveSpdxInsightsForProject} from "@/utils/api";

onBeforeMount(() => {
  fetchMetadata()
  fetchSpdxInsights()
  setTabMenus()
})

function fetchMetadata() {
  retrieveSbomMetadata().then(response => {
    const project_id = parseInt(route.query.project_id)
    metadata.value = response.filter(x => x.project_id === project_id).sort((x, y) => (x.mode + x.generator).localeCompare(y.mode + y.generator))
    thumb.value.set('Generators', metadata.value.length)
    dataLoaded.value = true;
  })
}

function fetchSpdxInsights() {
  retrieveSpdxInsightsForProject(route.query.project_id).then(response => {
    spdxInsights.value = response
  })
}

function setTabMenus() {
  tabMenu.value = [
    {
      label: 'Spdx Insights',
      to: '/project-insights?project_id=' + route.query.project_id
    },
    {
      label: 'Dependencies',
      to: '/dependency-results?project_id=' + route.query.project_id
    },
    {
      label: 'Licenses',
      to: '/license-results?project_id=' + route.query.project_id
    }
  ]
}

const tabMenu = ref([]);
const route = useRoute()
const thumb = ref(new Map())
const metadata = ref()
const spdxInsights = ref()
const dataLoaded = ref(false);
</script>

<template>
  <thumbnail :title="metadata[0].name" :map="thumb" :menu="tabMenu"/>
  <div class="container">
    <p-divider/>
    <p-accordion>
      <p-accordionTab header="Project Metadata">
        <project-metadata :metadata="metadata[0]"/>
      </p-accordionTab>
      <p-accordionTab header="SBOM details">
        <sbom-files-table :data="metadata"/>
      </p-accordionTab>
    </p-accordion>
    <p-divider/>

    <div v-for="item of spdxInsights">
      <sbom-insights-card :item="item" :title="item.mode + ' ' + item.generator"/>
    </div>

  </div>
</template>

<style scoped>

</style>