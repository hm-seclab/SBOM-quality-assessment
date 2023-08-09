<script setup>
import {FilterMatchMode} from "primevue/api";
import {retrieveSbomMetadata} from "@/utils/api";
import {onMounted, ref} from "vue";
import Homebadge from "@/components/homebadge.vue";
import Thumbnail from "@/components/thumbnail.vue";
import Disclamer from "@/components/disclamer.vue";

const projectList = ref([])
const dataLoaded = ref(false);

const thumb = ref(new Map())
const filters = ref({global: {value: null, matchMode: FilterMatchMode.CONTAINS}})

const containerGeneratorList = ref([
  'syft-container',
  'trivy-container',
  'cdxgen-container',
  'microsoft-container',
  'tern-container'])

const releaseGeneratorList = ref([
  'syft-release',
  'trivy-release',
  'cdxgen-release',
  'microsoft-release'])

const sourceGeneratorList = ref([
  'syft-source',
  'trivy-source',
  'cdxgen-source',
  'microsoft-source',
  'scancode-source',
  'github-source'])

onMounted(() => {
  thumb.value.set('All Projects', '-')
  thumb.value.set('All SBOMs', '-')
  fetchSubjectProjects()
})

async function fetchSubjectProjects() {
  let sbomList = await retrieveSbomMetadata()
  let list = new Map()
  thumb.value.set('All SBOMs', sbomList.length)

  sbomList.forEach(entry  => {
    list.set(entry.project_id, entry.name)
  })

  for (const x of list) {
    const holder = {}
    holder.project_id = x[0]
    holder.name = x[1]
    holder.sbom = sbomList.filter((b) => b.project_id === holder.project_id)
    projectList.value.push(holder)
  }

  thumb.value.set('All Projects', projectList.value.length)
  dataLoaded.value = true
}
</script>

<template>
  <thumbnail title="Home" :map="thumb"/>
  <div class="container">
    <disclamer/>
    <div v-if="dataLoaded === false" style="margin-top: 12px">
      <p-skeleton class="mb-2" height="160px"/>
      <p-skeleton v-for="index in 30" class="mb-2" height="40px"/>
    </div>
    <div v-else>
      <p-dataTable :value="projectList" style="margin-top: 12px" class="p-datatable-sm"
                   v-model:filters="filters" filter-display="row" stripedRows dataKey="project_id" paginator :rows="50" :rowsPerPageOptions="[50, 100, 200, 500, 1000]">
        <template #header>
          <div class="flex justify-content-between">
            <div>
              <span class="p-input-icon-right">
                <i class="pi pi-search"/>
                <p-inputText v-model="filters['global'].value" placeholder="Search"/>
              </span>
            </div>
            <div class="flex">
              <div class="flex flex-column">
                <p-chip class="pl-0 m-1">
                  <span class="border-circle w-2rem h-2rem flex align-items-center justify-content-center bg-green-400"><i class="pi pi-check text-gray-50"/></span>
                  <span class="ml-2">SBOM generated (with some Dependencies)</span>
                </p-chip>
                <p-chip class="pl-0 m-1">
                  <span class="border-circle w-2rem h-2rem flex align-items-center justify-content-center bg-gray-600"><i class="pi pi-question text-gray-50"/></span>
                  <span class="ml-2">No SBOM generation attempted</span>
                </p-chip>
              </div>
              <div class="flex flex-column">
                <p-chip class="pl-0 m-1">
                  <span class="border-circle w-2rem h-2rem flex align-items-center justify-content-center bg-yellow-400"><i class="pi pi-exclamation-triangle text-gray-50"/></span>
                  <span class="ml-2">SBOM generated (with no Dependencies)</span>
                </p-chip>
                <p-chip class="pl-0 m-1">
                  <span class="border-circle w-2rem h-2rem flex align-items-center justify-content-center bg-red-400"><i class="pi pi-times text-gray-50"/></span>
                  <span class="ml-2">SBOM generation failed</span>
                </p-chip>
              </div>
            </div>
          </div>
        </template>

        <p-column field="project_id" header="ID" sortable>
        </p-column>

        <p-column field="name" header="Name" sortable filter>
          <template #body="slotProps">
              <div>{{ slotProps.data.name }}</div>
          </template>
        </p-column>

        <p-column field="generatorList" header="Release">
          <template #body="slotProps">
            <homebadge
                :reflist="releaseGeneratorList"
                :data="slotProps.data.sbom"/>
          </template>
        </p-column>

        <p-column field="generatorList" header="Container">
          <template #body="slotProps">
            <homebadge
                :reflist="containerGeneratorList"
                :data="slotProps.data.sbom"/>
          </template>
        </p-column>

        <p-column field="generatorList" header="Source">
          <template #body="slotProps">
            <homebadge
                :reflist="sourceGeneratorList"
                :data="slotProps.data.sbom"/>
          </template>
        </p-column>

        <p-column header="">
          <template #body="slotPros">
            <router-link :to="'/project/' + slotPros.data.project_id + '/insights'">
              <p-button label="Details" rounded outlined/>
            </router-link>
          </template>
        </p-column>
      </p-dataTable>
    </div>
  </div>
</template>

<style scoped>

</style>