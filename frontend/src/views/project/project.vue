<script setup>
import Thumbnail from "@/components/thumbnail.vue";
import {onBeforeMount, ref} from "vue";
import {retrieveSbomMetadata} from "@/utils/api";
import {useRoute} from "vue-router";
import ProjectMetadata from "@/components/project-metadata.vue";
import SbomFilesTable from "@/components/sbom-files-table.vue";

const route = useRoute()
const thumb = ref(new Map())
const metadata = ref()
const dataLoaded = ref(false);

onBeforeMount(() => {
  fetchMetadata()
})

function fetchMetadata() {
  retrieveSbomMetadata().then(response => {
    const project_id = parseInt(route.params.project_id)
    metadata.value = response.filter(x => x.project_id === project_id).sort((x, y) => (x.mode + x.generator).localeCompare(y.mode + y.generator))
    thumb.value.set('Generators', metadata.value.length)
    dataLoaded.value = true;
  })
}

</script>

<template>
  <thumbnail :title="metadata[0].name" :map="thumb"/>
  <div class="container">
    <b-tabs pills justified class="mt-2">
      <b-tab title="Insights" @click="$router.push(`/project/${route.params.project_id}/insights`)"/>
      <b-tab title="Dependencies" @click="$router.push(`/project/${route.params.project_id}/dependencies`)"/>
      <b-tab title="Licenses" @click="$router.push(`/project/${route.params.project_id}/licenses`)"/>
    </b-tabs>
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
    <router-view/>
  </div>
</template>