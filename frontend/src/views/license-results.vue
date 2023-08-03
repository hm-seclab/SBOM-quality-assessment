<script setup>
import {onBeforeMount, ref} from "vue";
import Thumbnail from "@/components/thumbnail.vue";
import {useRoute} from "vue-router";
import ProjectMetadata from "@/components/project-metadata.vue";
import LicenseTable from "@/components/license-table.vue";
import SbomFilesTable from "@/components/sbom-files-table.vue";
import {retrieveSbomMetadata} from "@/utils/api";

onBeforeMount(() => {
  fetchMetadata()
  setTabMenus()
})

function fetchMetadata() {
  retrieveSbomMetadata().then(response => {
    const project_id = parseInt(route.query.project_id)
    metadata.value = response.filter(x => x.project_id === project_id).sort((x, y) => (x.mode + x.generator).localeCompare(y.mode + y.generator))
    thumb.value.set('CycloneDx', metadata.value.filter(item => item.cdx_orig == true).length)
    thumb.value.set('SPDX', metadata.value.filter(item => item.spdx_orig == true).length)
    thumb.value.set('Generators', metadata.value.length)
    dataLoaded.value = true;
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
const activeTab = ref()
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
        <b-tabs content-class="mt-3" justified v-model="activeTab">
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
  </div>
</template>

<style scoped>

</style>