<script setup>
import {onBeforeMount, ref} from "vue";
import {retrieveSbomMetadata, retrieveSpdxInsightsForFile} from "@/utils/api";
import {useRoute} from "vue-router";
import Thumbnail from "@/components/thumbnail.vue";
import {convertExecutionTime, convertTimestampHumanReadable} from "@/utils/global";
import Asciinema from "@/components/asciinema.vue";
import SbomInsightsCard from "@/components/sbom-insights-card.vue";

const route = useRoute()
const metadata = ref()
const spdxInsights = ref()
const thumb = ref(new Map())

onBeforeMount(() => {
  fetchSbomMetadata()
  fetchSpdxInsights()
})

async function fetchSbomMetadata() {
  retrieveSbomMetadata().then(response => {
    metadata.value = response.filter(x => x.sbom_file_id === parseInt(route.query.sbom_file_id))[0]
    thumb.value.set('All Dependencies', metadata.value.totalPackages)
    thumb.value.set('All Releationships', metadata.value.totalRelationships)
  })
}

async function fetchSpdxInsights() {
  retrieveSpdxInsightsForFile(route.query.sbom_file_id).then(response => {
    spdxInsights.value = response
  })
}
</script>

<template>
  <thumbnail :title="metadata.name + ' ' + metadata.generator + ' ' + metadata.mode" :map="thumb"/>
  <div class="container">
    <p-card class="surface-100 mt-3 mb-3">
      <template #title>
        <p>Metadata</p>
      </template>
      <template #content>
        <div class="grid">
          <div class="col-10">
            <b>Project Metadata</b>
            <table>
              <tr>
                <td>Name:</td>
                <td>{{ metadata.name }}</td>
              </tr>
              <tr>
                <td>Project ID:</td>
                <td>{{ metadata.project_id }}</td>
              </tr>
              <tr>
                <td>Git Repository:</td>
                <td>{{ metadata.git }}</td>
              </tr>
            </table>
            <br/>

            <b>SBOM Metadata</b>
            <table>
              <tr>
                <td>SBOM file ID:</td>
                <td>{{ metadata.sbom_file_id }}</td>
              </tr>
              <tr>
                <td>Generator:</td>
                <td>{{ metadata.generator }}</td>
              </tr>
              <tr>
                <td>Mode:</td>
                <td>{{ metadata.mode }}</td>
              </tr>
              <tr>
                <td>Timestamp:</td>
                <td>{{ convertTimestampHumanReadable(metadata.timestamp) }}</td>
              </tr>
              <tr>
                <td>Execution Time:</td>
                <td>{{ convertExecutionTime(metadata.executionTime) }}</td>
              </tr>
              <tr>
                <td>SPDX exists:</td>
                <td>
                  {{ metadata.spdx_exists ? metadata.spdx_orig ? 'Generated' : 'Converted' : 'Not Generrated' }}
                </td>
              </tr>
              <tr>
                <td>CycloneDx exists:</td>
                <td>
                  {{ metadata.cdx_exists ? metadata.cdx_orig ? 'Generated' : 'Converted' : 'Not Generrated' }}
                </td>
              </tr>
            </table>

          </div>
          <div class="col-2">
            <div class="flex flex-column">
              <a v-if="metadata.spdx_exists"
              :href="'/api/downloadSbomFile?type=spdx&sbom_file_id=' + metadata.sbom_file_id" download>
                <p-button label="Download SPDX" rounded outlined class="m-1" icon="pi pi-download" style="width: 100%" />
              </a>
              <a v-else>
                <p-button label="Download SPDX" rounded outlined class="m-1" icon="pi pi-download" style="width: 100%" disabled severity="secondary"/>
              </a>
              <a v-if="metadata.cdx_exists"
                 :href="'/api/downloadSbomFile?type=cdx&sbom_file_id=' + metadata.sbom_file_id" download>
                <p-button label="Download CDX" rounded outlined class="m-1" icon="pi pi-download" style="width: 100%" />
              </a>
              <a v-else>
                <p-button label="Download CDX" rounded outlined class="m-1" icon="pi pi-download" style="width: 100%" disabled severity="secondary"/>
              </a>
              <a>
                <asciinema :metadata="metadata"/>
              </a>
            </div>
          </div>

          <br/><br/>
          <b>Generator Command:</b>
          <div class="bg-bluegray-100 border-round-lg command">
            <p style="margin: 15px">{{ metadata.command }}</p>
          </div>
        </div>

      </template>
    </p-card>

    <sbom-insights-card v-if="spdxInsights" :item="spdxInsights" title="SBOM insights"/>
  </div>

</template>

<styles scoped>

</styles>