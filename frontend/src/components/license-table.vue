<script setup>
import {onMounted, ref} from "vue";
import {retrieveLiceseList} from "@/utils/api";

  const props = defineProps(['metadata', 'attribute'])

  onMounted(() => {
    retrieveLiceseList(props.metadata[0].project_id, props.attribute).then(response => {
      let tempComparison = []
      props.metadata.forEach(metaEntry => {
        if (metaEntry.spdx_exists) {
          let bomEntry = {}
          let licenseList = []
          let alldependencyAmount = 0
          let allLicensedAmount = 0
          let allLicenseTypesAmount = 0
          bomEntry.generator = metaEntry.generator + '-' + metaEntry.mode
          response.forEach(entry => {
            if (entry.generator === bomEntry.generator) {
              licenseList.push(entry);
              alldependencyAmount = alldependencyAmount + entry.amount
              allLicenseTypesAmount = allLicenseTypesAmount + 1

              if (entry.license === 'NOASSERTION' || entry.license === 'NONE' || entry.license === null || entry.license.length === 0) {
              } else {
                allLicensedAmount = allLicensedAmount + entry.amount
              }
            }
          })

          bomEntry.dependencyAmount = alldependencyAmount
          bomEntry.allLicensedAmount = allLicensedAmount
          bomEntry.allLicenseTypesAmount = allLicenseTypesAmount
          bomEntry.licenseList = licenseList
          bomEntry.ratio = alldependencyAmount === 0 ? 0 : Math.round((allLicensedAmount / alldependencyAmount * 100))
          tempComparison.push(bomEntry)
        }})
      comparison.value = tempComparison
      dataLoaded.value = true
    })
  })

  function calculateCoverage(dependencies, licenced) {
    return (Number(dependencies === 0 ? 0 : licenced / dependencies * 100))
  }

  const comparison = ref()
  const dataLoaded = ref(false)
const expandedRows = ref([]);
</script>

<template>
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
    <p-panel v-for="(sbom, index) in comparison" toggleable :header="sbom.generator" collapsed>
      <template #header>
        <div class="col-11">
          <div class="flex flex-column sm:flex-row justify-content-between align-items-center xl:align-items-start flex-1 gap-4">
            <div class="flex flex-column align-items-center sm:align-items-start gap-3">
              <div class="text-2xl font-bold text-900">{{ sbom.generator }}</div>
            </div>
            <div class="flex">
              <table>
                <tr>
                  <td style="width: 12em">License Types Detected:</td>
                  <td style="width: 3em">{{ sbom.allLicenseTypesAmount }}</td>
                </tr>
                <tr>
                  <td>Licensed Dependencies:</td>
                  <td>{{ sbom.allLicensedAmount }}</td>
                </tr>
                <tr>
                  <td>All Dependencies:</td>
                  <td>{{ sbom.dependencyAmount }}</td>
                </tr>
              </table>
              <p-knob v-model="sbom.ratio" readonly valueTemplate="{value}%"/>
            </div>
          </div>
        </div>
      </template>
      <p-dataTable v-model:expandedRows="sbom.expandedRows" :value="sbom.licenseList" class="p-datatable-sm">
        <p-column expander style="width: 5rem" />
        <p-column field="amount" header="Amount"/>
        <p-column field="license" header="License" >
          <template #body="slotProps">
            <div class="truncate" :title="slotProps.data.license">
              {{ slotProps.data.license === null ? slotProps.data.license : slotProps.data.license.length > 120 ? slotProps.data.license.substring(0, 120) + '...' : slotProps.data.license}}
            </div>
          </template>
        </p-column>
        <template #expansion="slotProps">
          <p-chip v-for="item of slotProps.data.packageList" :label="item" style="margin: 2px"/>
        </template>
      </p-dataTable>
    </p-panel>
  </div>
</template>

<style scoped>
.truncate {
  white-space: nowrap;
  overflow: hidden;
}
</style>