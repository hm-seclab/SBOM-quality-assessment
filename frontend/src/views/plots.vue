<script setup>
import {onMounted, ref} from "vue";
import {retrieveSbomMetadata} from "@/utils/api";
import Thumbnail from "@/components/thumbnail.vue";

import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
import {generateCoverageReportPlot} from "@/utils/plots";

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

const data = ref([])
const thumb = ref(new Map())
const dataloaded = ref(false)

onMounted(() => {
  thumb.value.set('All SBOMs', '-')
  fetchSbomMetadata()
})

async function fetchSbomMetadata() {
  retrieveSbomMetadata().then(response => {
    data.value = response
    thumb.value.set('All SBOMs', response.length)
    coverageReportContainer.value = generateCoverageReportPlot(response, 'container')
    coverageReportSource.value = generateCoverageReportPlot(response, 'source')
    coverageReportRelease.value = generateCoverageReportPlot(response, 'release')
    dataloaded.value = true
  })
}

const coverageReportRelease = ref()
const coverageReportContainer = ref()
const coverageReportSource= ref()

</script>

<template>
  <thumbnail title="Plots" :map="thumb"/>
  <div class="container">
    <p-card v-if="dataloaded" class="surface-100 mt-3">
      <template #title>
        <p>Generator Coverage Report</p>
      </template>
      <template #content>
        <div class="grid">
          <div class="col-4">
            <Bar id="my-chart-id" :options="coverageReportRelease.chartOptions" :data="coverageReportRelease.chartData"/>
          </div>
          <div class="col-4">
            <Bar :options="coverageReportContainer.chartOptions" :data="coverageReportContainer.chartData"/>
          </div>
          <div class="col-4">
            <Bar :options="coverageReportSource.chartOptions" :data="coverageReportSource.chartData"/>
          </div>
        </div>
      </template>
    </p-card>
  </div>
</template>

<styles>

</styles>