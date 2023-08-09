<script setup>
import {ref, onBeforeMount} from 'vue';
import { FilterMatchMode } from 'primevue/api';
import ForcePlot from "@/components/force-plot.vue";
import {toolLightColor} from "@/utils/global";
import {retrieveDependencyList, retrieveSbomMetadataByProject} from "@/utils/api";
import {useRoute} from "vue-router";

const route = useRoute()
const activeTab = ref()
const dataLoaded = ref(false)
const plotVisible = ref(false)
const comparison = ref();
const metadata = ref();
const apiReply = ref();

const selectableColumns = ref()
const selectedColumns = ref()

const filters = ref({
  global: { value: null, matchMode: FilterMatchMode.CONTAINS }
});

onBeforeMount(() => {
  retrieveSbomMetadataByProject(route.params.project_id).then(response => {
    metadata.value = response
    selectableColumns.value = metadata.value.filter(col => col.spdx_exists)
    selectedColumns.value = selectableColumns.value
  })
  loadComparision('RETRIEVE_DEPENDENCY_LIST_BY_REF_LOC')
});

function loadComparision(type) {
  activeTab.value = type
  dataLoaded.value = false
  retrieveDependencyList(route.params.project_id, type).then(response => {
    let resultlist = []
    apiReply.value = response
    response.forEach(responseEntry => {
      let result = {}
      result.name = responseEntry.name;
      resultlist.push(result)
      for (const meta of metadata.value) {
        if (!meta.spdx_exists) {
          continue
        }
        let identifier = metaToIdentifyer(meta)
        if (responseEntry.generator.includes(identifier)) {
          result[identifier] = true
        } else {
          result[identifier] = false
        }
      }
    })
    comparison.value = resultlist
    dataLoaded.value = true
  })
}

function metaToIdentifyer(meta) {
  return meta.generator + '-' + meta.mode
}
const onToggle = (val) => {
  selectedColumns.value = selectableColumns.value.filter(col => val.includes(col));
};
</script>

<template>
  <div class="grid">
    <div class="col-3">
      <p-button label="by SPDX Ref" @click="loadComparision('RETRIEVE_DEPENDENCY_LIST_BY_REF_LOC')"
                class="w-full" :text="activeTab !== 'RETRIEVE_DEPENDENCY_LIST_BY_REF_LOC'"/>
    </div>
    <div class="col-3">
      <p-button label="by SPDX Name" @click="loadComparision('RETRIEVE_DEPENDENCY_LIST_BY_NAME')"
                class="w-full" :text="activeTab !== 'RETRIEVE_DEPENDENCY_LIST_BY_NAME'"/>
    </div>
    <div class="col-3">
      <p-button label="by SPDX Ref (with version)" @click="loadComparision('RETRIEVE_DEPENDENCY_LIST_BY_REF_LOC_VERSION')"
                class="w-full" :text="activeTab !== 'RETRIEVE_DEPENDENCY_LIST_BY_REF_LOC_VERSION'"/>
    </div>
    <div class="col-3">
      <p-button label="by SPDX Name (with version)" @click="loadComparision('RETRIEVE_DEPENDENCY_LIST_BY_NAME_VERSION')"
                class="w-full" :text="activeTab !== 'RETRIEVE_DEPENDENCY_LIST_BY_NAME_VERSION'"/>
    </div>
  </div>
  <div class="grid">
    <div class="col-3">
      <p-button label="by CycloneDx Purl" @click="loadComparision('RETRIEVE_CDX_DEPENDENCY_LIST_BY_PURL')"
                class="w-full" :text="activeTab !== 'RETRIEVE_CDX_DEPENDENCY_LIST_BY_PURL'"/>
    </div>
    <div class="col-3">
      <p-button label="by CycloneDx Name" @click="loadComparision('RETRIEVE_CDX_DEPENDENCY_LIST_BY_NAME')"
                class="w-full" :text="activeTab !== 'RETRIEVE_CDX_DEPENDENCY_LIST_BY_NAME'"/>
    </div>
    <div class="col-3">
      <p-button label="by CycloneDx Prul (with version)" @click="loadComparision('RETRIEVE_CDX_DEPENDENCY_LIST_BY_PURL_VERSION')"
                class="w-full" :text="activeTab !== 'RETRIEVE_CDX_DEPENDENCY_LIST_BY_PURL_VERSION'"/>
    </div>
    <div class="col-3">
      <p-button label="by CycloneDx Name (with version)" @click="loadComparision('RETRIEVE_CDX_DEPENDENCY_LIST_BY_NAME_VERSION')"
                class="w-full" :text="activeTab !== 'RETRIEVE_CDX_DEPENDENCY_LIST_BY_NAME_VERSION'"/>
    </div>
  </div>

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
    <p-dataTable v-model:filters="filters" :value="comparison" tableStyle="min-width: 50rem" stripedRows class="p-datatable-sm"
                 paginator :rows="100" :rowsPerPageOptions="[100, 200, 500, 1000]" removableSort :globalFilterFields="['name']">
      <template #header>
        <div class="flex justify-content-between">
          <div>
            <span class="p-input-icon-right">
              <i class="pi pi-search"/>
              <p-inputText v-model="filters['global'].value" placeholder="Search Dependencies"/>
            </span>
            <p-button label="Scatter Plot" outlined rounded class="ml-3" icon="pi pi-chart-bar"
                      @click="plotVisible = true"/>
            <p-dialog v-model:visible="plotVisible" modal maximizable header="SBOM Generator Dependencies Plot"
                      :style="{ width: '100%', height: '100%' }">
              <template #header>
                <p class="font-bold text-2xl">SBOM Generator Dependencies Plot</p>
                <div>
                  <p-chip class="pl-0 m-1">
                    <span class="border-circle w-2rem h-2rem flex align-items-center justify-content-center" style="background-color: #1f76b3"/>
                    <span class="ml-2">Container generator</span>
                  </p-chip>
                  <p-chip class="pl-0 m-1">
                    <span class="border-circle w-2rem h-2rem flex align-items-center justify-content-center" style="background-color: #fd7e0e"/>
                    <span class="ml-2">Release generator</span>
                  </p-chip>
                  <p-chip class="pl-0 m-1">
                    <span class="border-circle w-2rem h-2rem flex align-items-center justify-content-center" style="background-color: #2c9f2c"/>
                    <span class="ml-2">Source generator</span>
                  </p-chip>
                  <p-chip class="pl-0 m-1">
                    <span class="border-circle w-2rem h-2rem flex align-items-center justify-content-center" style="background-color: #7e7e7e"/>
                    <span class="ml-2">Dependency</span>
                  </p-chip>
                </div>
              </template>
              <force-plot :metadata="metadata" :data="apiReply"/>
            </p-dialog>
          </div>
          <p-multiSelect :modelValue="selectedColumns" :options="selectableColumns" optionLabel="generator"
                         @update:modelValue="onToggle"
                         display="chip" placeholder="Select Columns" :maxSelectedLabels="3">
            <template #option="slotProps">
              <div class="flex align-items-center">
                <div>{{ slotProps.option.mode }} {{ slotProps.option.generator }}</div>
              </div>
            </template>
          </p-multiSelect>
        </div>
      </template>
      <p-columnGroup type="header">
        <p-row>
          <p-column style="max-width: 20%; background: linear-gradient(to right, #eeeeee, #f8f8f8)">
            <template #header>
              <div style="width: 20%">
                <div class="text-lg">Dependencies</div>
              </div>
            </template>
          </p-column>
          <p-column header="Container"
                    style="background: linear-gradient(to right, #eeeeee, #f8f8f8)"
                    v-if="selectedColumns.filter(item => item.mode === 'container').length"
                    :colspan="selectedColumns.filter(item => item.mode === 'container').length"/>
          <p-column header="Release"
                    style="background: linear-gradient(to right, #eeeeee, #f8f8f8)"
                    v-if="selectedColumns.filter(item => item.mode === 'release').length"
                    :colspan="selectedColumns.filter(item => item.mode === 'release').length"/>
          <p-column header="Source"
                    style="background: linear-gradient(to right, #eeeeee, #f8f8f8)"
                    v-if="selectedColumns.filter(item => item.mode === 'source').length"
                    :colspan="selectedColumns.filter(item => item.mode === 'source').length"/>
        </p-row>
        <p-row>
          <p-column>
            <template #header>
              <div style="width: 20%">
                <div class="text-gray-500 text-smaller">
                  {{ comparison.length }}
                </div>
              </div>
            </template>
          </p-column>
          <p-column v-for="(meta, index) of selectedColumns" :colspan="1"
                    :style="'background-color: ' + toolLightColor(meta.mode)"
                    :key="metaToIdentifyer(meta) + '_' + index"
                    :field="metaToIdentifyer(meta)">
            <template #header>
              <div class="rotated-header">{{ meta.generator }}</div>
              <div class="rotated-header text-gray-500 text-smaller">
                {{ comparison.filter(item => item[metaToIdentifyer(meta)] == true).length }}
              </div>
            </template>
          </p-column>
        </p-row>
        <p-row>
          <p-column field="name" sortable/>
          <p-column v-for="meda of selectedColumns" :colspan="1" sortable
                    :key="metaToIdentifyer(meda)"
                    :field="metaToIdentifyer(meda)">
          </p-column>
        </p-row>
      </p-columnGroup>
      <p-column field="name">
        <template #body="slotProps">
          <div :title="slotProps.data.name">
            {{
              slotProps.data.name.length > 50 ? slotProps.data.name.substring(0, 50) + "..." : slotProps.data.name
            }}
          </div>
        </template>
      </p-column>
      <p-column v-for="(meta, index) of selectedColumns"
                :key="metaToIdentifyer(meta) + '_' + index"
                :field="metaToIdentifyer(meta)"
                :header="metaToIdentifyer(meta)">
        <template #body="{ data, field }">
          <div v-if="data[field]">
            <i class="pi pi-check-circle" style="color: green" title="true"/>
          </div>
          <div v-else>
            <i class="pi pi-times-circle" style="color: lightcoral" title="false"/>
          </div>
        </template>
      </p-column>
    </p-dataTable>
  </div>
</template>

<style scoped>
.rotated-header {
  transform: rotate(-60deg);
  white-space: nowrap;
  width: 10px;
  padding-right: 20px;
  margin-top: 60px;
}
</style>