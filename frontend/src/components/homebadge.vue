<script setup>
  import router from "@/router";

  const props = defineProps(['reflist', 'data'])

  function retrieveState(meta) {
    let filter = props.data.filter((x) => x.generator + '-' + x.mode === meta);

    if (filter.length === 0) {
      return {icon: 'pi pi-question', color: 'bg-gray-100', severity: 'secondary', disabled: true, sbom_file_id: null}
    }

    if (filter[0].status === 'failed') {
      return {icon: 'pi pi-times', color: 'bg-red-50', severity: 'danger', disabled: false, sbom_file_id: filter[0].sbom_file_id}
    }

    if (filter[0].status === 'corrupted') {
      return {icon: 'pi pi-exclamation-triangle', color: 'bg-yellow-50', severity: 'warning', disabled: false, sbom_file_id: filter[0].sbom_file_id}
    }
    if (filter[0].status === 'good') {
      return {icon: 'pi pi-check', color: 'bg-green-50', severity: 'success', disabled: false, sbom_file_id: filter[0].sbom_file_id}
    }
    return null;
  }

  function navigationToSbomInsights(sbom_file_id) {
    router.push({path: '/sbom-insights', query: { sbom_file_id: sbom_file_id}})
  }
</script>

<template>
  <span class="p-buttonset" >
    <p-button
        :disabled="retrieveState(meta).disabled"
        v-for="meta of reflist"
        @click="navigationToSbomInsights(retrieveState(meta).sbom_file_id)"
        :icon="retrieveState(meta).icon"
        rounded outlined
        v-p-tooltip.bottom="{ value: meta, showDelay: 650, hideDelay: 300 }"
        :severity="retrieveState(meta).severity"
        :class="retrieveState(meta).color"/>
  </span>
</template>

<style scoped>

</style>