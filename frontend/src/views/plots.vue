<script setup>
import {onMounted, ref} from "vue";
import {retrieveSbomMetadata} from "@/utils/api";
import Thumbnail from "@/components/thumbnail.vue";

import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

const data = ref([])
const thumb = ref(new Map())

onMounted(() => {
  thumb.value.set('All SBOMs', '-')
  fetchSbomMetadata()
})

async function fetchSbomMetadata() {
  retrieveSbomMetadata().then(response => {
    data.value = response
    thumb.value.set('All SBOMs', response.length)
  })
}

const chart = ref({
  chartData: {
    labels: ['Label 1', 'Label 2', 'Label 3'], // Replace with your labels
        datasets: [
      {
        label: 'Dataset 1',
        backgroundColor: 'rgba(255, 99, 132, 0.5)',
        data: [10, 20, 30], // Replace with your data for Dataset 1
      },
      {
        label: 'Dataset 2',
        backgroundColor: 'rgba(54, 162, 235, 0.5)',
        data: [15, 25, 35], // Replace with your data for Dataset 2
      },
    ],
  },
  chartOptions: {
    plugins: {
      title: {
        display: true,
        text: 'Test BarChart'
      }
    },
    responsive: true,
    aspectRatio: 1,
    maintainAspectRatio: false,
    scales: {
      x: {
        stacked: true,
      },
      y: {
        stacked: true,
      },
    },
  },
})


</script>

<template>
  <thumbnail title="Plots" :map="thumb"/>
  <div class="container">
    <p-card class="surface-200 mt-3">
      <template #title>
        <p>Generator coverage</p>
      </template>
      <template #content>
        <div class="grid">
          <div class="col-4">
            <Bar id="my-chart-id" :options="chart.chartOptions" :data="chart.chartData"/>
          </div>
          <div class="col-4">
            <Bar :options="chart.chartOptions" :data="chart.chartData"/>
          </div>
          <div class="col-4">
            <Bar :options="chart.chartOptions" :data="chart.chartData"/>
          </div>
        </div>
      </template>
    </p-card>
  </div>
</template>

<styles>

</styles>