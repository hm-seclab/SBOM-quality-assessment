<script setup>
import {ref} from "vue";
import {FilterMatchMode} from "primevue/api";
import {convertExecutionTime, convertTimestampHumanReadable} from "@/utils/global";

const props = defineProps(['data'])

const filters = ref({global: {value: null, matchMode: FilterMatchMode.CONTAINS}})

</script>

<template>
  <div v-if="data.length === 0">
    <p-skeleton class="mb-2" height="160px"/>
    <p-skeleton v-for="index in 30" class="mb-2" height="40px"/>
  </div>
  <div v-else>
    <p-dataTable :value="data" stripedRows class="p-datatable-sm"
                 selectionMode="single"
                 v-model:filters="filters"
                 dataKey="sbom_file_id" paginator :rows="50" :rowsPerPageOptions="[100, 200, 500, 1000]">
      <template #header>
        <div class="flex justify-content-end">
          <p-inputText v-model="filters['global'].value" placeholder="Search"/>
        </div>
      </template>

      <p-column field="sbom_file_id" header="ID" sortable/>
      <p-column field="name" header="Name" sortable/>
      <p-column field="generator" header="Generator" sortable/>
      <p-column field="mode" header="Mode" sortable/>

      <p-column field="timestamp" header="Timestamp" sortable>
        <template #body="slotProps">
          {{ convertTimestampHumanReadable(slotProps.data.timestamp) }}
        </template>
      </p-column>

      <p-column field="executionTime" header="Runtime" sortable>
        <template #body="slotProps">
          {{ convertExecutionTime(slotProps.data.executionTime) }}
        </template>
      </p-column>

      <p-column field="totalPackages" header="Packages" sortable/>
      <p-column field="totalRelationships" header="Relationships" sortable>
        <template #body="slotProps">
          <div class="flex justify-content-between">
            <p>{{ slotProps.data.totalRelationships }}</p>
            <div class="flex align-content-center flex-wrap" v-if="slotProps.data.spdx_exists || slotProps.data.cdx_exists">
              <p-badge value="SPDX" class="m-1" :severity="slotProps.data.spdx_exists ? 'success' : 'warning'"/>
              <p-badge value="CDX" class="m-1" :severity="slotProps.data.cdx_exists ? 'success' : 'warning'"/>
            </div>
            <div v-else>
              <p-badge value="failed" class="m-1" severity="danger"/>
            </div>
          </div>
        </template>
      </p-column>

      <p-column>
        <template #body="slotProps">
          <div class="flex justify-content-end">
            <router-link :to="'sbom-insights?sbom_file_id=' + slotProps.data.sbom_file_id">
              <p-button label="Details" rounded outlined/>
            </router-link>
          </div>
        </template>
      </p-column>
    </p-dataTable>
  </div>
</template>

<style scoped>

</style>