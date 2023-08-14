<script setup>
import {ref} from "vue";

const showDisclamer = ref(true)
</script>

<template>
  <p-card v-if="showDisclamer" class="surface-100 mt-2 mb-2">
    <template #title>
      <div class="flex justify-content-between">
        <p>About this Website</p>
        <p-button @click="showDisclamer = false" icon="pi pi-times" severity="secondary" text rounded/>
      </div>
    </template>
    <template #content>
      In this project we generate SBOMs with freely accessible tools and compare them against each other. As subjects we use well known open source projects. The SBOMs are generated in different phases of the software lifecycle, based on the source code, the release or a docker container. The generation is fully automated and the data can be biased. Please be careful drawing conclusions based on this data.
    </template>
    <template #footer>
      <div class="flex justify-content-end">
<!--        <a href="" target="_blank">-->
<!--          <p-button label="HowTo" icon="pi pi-book" severity="secondary" outlined rounded/>-->
<!--        </a>-->

        <a href="https://github.com/hm-seclab/SBOM-quality-assessment" target="_blank">
          <p-button label="Github" icon="pi pi-github" severity="secondary" class="ml-2" outlined rounded/>
        </a>
      </div>
    </template>
  </p-card>
</template>