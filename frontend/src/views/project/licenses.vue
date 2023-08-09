<script setup>
import {onBeforeMount, ref} from "vue";
import {useRoute} from "vue-router";
import LicenseTable from "@/components/license-table.vue";
import {retrieveSbomMetadata} from "@/utils/api";

onBeforeMount(() => {
  fetchMetadata()
})

function fetchMetadata() {
  retrieveSbomMetadata().then(response => {
    const project_id = parseInt(route.params.project_id)
    metadata.value = response.filter(x => x.project_id === project_id).sort((x, y) => (x.mode + x.generator).localeCompare(y.mode + y.generator))
    dataLoaded.value = true;
  })
}

const route = useRoute()
const metadata = ref()
const activeTab = ref()
const dataLoaded = ref(false);
</script>

<template>
  <div>
    <div v-if="dataLoaded === false">
      <p-skeleton class="mb-2" height="160px"/>
      <p-skeleton class="mb-2" height="25px"/>
      <p-skeleton class="mb-2" height="25px"/>
      <p-skeleton class="mb-2" height="25px"/>
      <p-skeleton class="mb-2" height="25px"/>
      <p-skeleton class="mb-2" height="25px"/>
      <p-skeleton class="mb-2" height="25px"/>
      <p-skeleton class="mb-2" height="25px"/>
      <p-skeleton class="mb-2" height="25px"/>
      <p-skeleton class="mb-2" height="25px"/>
      <p-skeleton class="mb-2" height="25px"/>
    </div>

    <div v-else>
      <b-tabs content-class="mt-3" pills justified v-model="activeTab">
        <b-tab title="License Declared" active lazy>
          <license-table :metadata="metadata" attribute="licenseDeclared"/>
        </b-tab>

        <b-tab title="License Concluded" lazy>
          <license-table :metadata="metadata" attribute="licenseConcluded"/>
        </b-tab>

        <b-tab title="Files Analyzed" lazy>
          <license-table :metadata="metadata" attribute="filesAnalyzed"/>
        </b-tab>

        <b-tab title="Copyright Text" lazy>
          <license-table :metadata="metadata" attribute="copyrightText"/>
        </b-tab>

        <b-tab title="Download Location" lazy>
          <license-table :metadata="metadata" attribute="downloadLocation"/>
        </b-tab>
      </b-tabs>
    </div>
  </div>
</template>

<style scoped>

</style>