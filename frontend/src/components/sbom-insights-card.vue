<script setup>
import {onBeforeMount, ref} from "vue";

const props = defineProps(['item', 'title'])
const ratio = ref()

onBeforeMount(() => {
  let counter = 0;
  if (props.item.creationDate.length > 1) {
    counter++
  }
  if (props.item.total_relationships > 0) {
    counter++
  }
  if (props.item.totalSpdxId > 0) {
    counter++
  }
  if (props.item.totalPackageVersionInfo > 0) {
    counter++
  }
  if (props.item.totalPackageNames > 0) {
    counter++
  }
  if (props.item.totalSupplier > 0) {
    counter++
  }
  if (Object.keys(props.item.creators).length > 0) {
    counter++
  }
  ratio.value = Math.round(counter / 7 * 100)
})

</script>

<template>
  <p-card class="surface-100 mb-3">
    <template #title>{{ title }}</template>
    <template #content>
      <div class="grid">
        <div class="col-6">
          <table>
            <tr>
              <td>Name:</td>
              <td>{{ item.name }}</td>
            </tr>
            <tr>
              <td>Spdx ID:</td>
              <td>{{ item.spdxId }}</td>
            </tr>
            <tr>
              <td>Data License:</td>
              <td>{{ item.dataLicense }}</td>
            </tr>
            <tr>
              <td>Spdx Version:</td>
              <td>{{ item.spdxVersion }}</td>
            </tr>
            <tr>
              <td>Creation Date:</td>
              <td>{{ item.creationDate }}</td>
            </tr>
            <tr>
              <td>Creators org:</td>
              <td>{{ item.creators.Organization }}</td>
            </tr>
            <tr>
              <td>Creators tool:</td>
              <td>{{ item.creators.Tool }}</td>
            </tr>
            <tr>
              <td>Comment:</td>
              <td>{{ item.comment }}</td>
            </tr>
            <tr>
              <td>License List Version:</td>
              <td>{{ item.licenseListVersion }}</td>
            </tr>
            <tr>
              <td>Document Namespace:</td>
              <td class="text-overflow-ellipsis">{{ item.documentNamespace }}</td>
            </tr>
          </table>
        </div>
        <div class="col-6">
          <div class="grid">
            <div class="col-12">
              <div class="font-bold">Minimum Requirements (NTIA):</div>
              <div class="grid">
                <div class="col-8">
                  <table>
                    <tr>
                      <td>
                        <i v-if="Object.keys(item.creators).length > 0" class="pi pi-check text-green-400 font-bold mr-2"></i>
                        <i v-else class="pi pi-times text-red-400 font-bold mr-2"></i>
                      </td>
                      <td>Author Name</td>
                    </tr>
                    <tr>
                      <td>
                        <i v-if="item.totalSupplier > 0" class="pi pi-check text-green-400 font-bold mr-2"></i>
                        <i v-else class="pi pi-times text-red-400 font-bold mr-2"></i>
                      </td>
                      <td>Suppliers Name</td>
                    </tr>
                    <tr>
                      <td>
                        <i v-if="item.totalPackageNames > 0" class="pi pi-check text-green-400 font-bold mr-2"></i>
                        <i v-else class="pi pi-times text-red-400 font-bold mr-2"></i>
                      </td>
                      <td>Names of the Components</td>
                    </tr>
                    <tr>
                      <td>
                        <i v-if="item.totalPackageVersionInfo > 0" class="pi pi-check text-green-400 font-bold mr-2"></i>
                        <i v-else class="pi pi-times text-red-400 font-bold mr-2"></i>
                      </td>
                      <td>Version of the Component</td>
                    </tr>
                    <tr>
                      <td>
                        <i v-if="item.totalSpdxId > 0" class="pi pi-check text-green-400 font-bold mr-2"></i>
                        <i v-else class="pi pi-times text-red-400 font-bold mr-2"></i>
                      </td>
                      <td>Other Unique Identifiers</td>
                    </tr>
                    <tr>
                      <td>
                        <i v-if="item.total_relationships > 0" class="pi pi-check text-green-400 font-bold mr-2"></i>
                        <i v-else class="pi pi-times text-red-400 font-bold mr-2"></i>
                      </td>
                      <td>Dependency Relationship</td>
                    </tr>
                    <tr>
                      <td>
                        <i v-if="item.creationDate.length > 1" class="pi pi-check text-green-400 font-bold mr-2"></i>
                        <i v-else class="pi pi-times text-red-400 font-bold mr-2"></i>
                      </td>
                      <td>Timestamp</td>
                    </tr>
                  </table>
                </div>
                <div class="col-4">
                  <p-knob v-model="ratio" readonly valueTemplate="{value}%"/>
                </div>
              </div>
            </div>
            <p-divider/>
            <div class="col-12">
              <table>
                <tr>
                  <td>Total Packages:</td>
                  <td>{{ item.total_packages }}</td>
                </tr>
                <tr>
                  <td>Total Relationships:</td>
                  <td>{{ item.total_relationships }}</td>
                </tr>
                <tr>
                  <td>Total External Infos:</td>
                  <td>{{ item.total_hasExternalLicensingInfos }}</td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>
    </template>
  </p-card>
</template>

<styles>

</styles>