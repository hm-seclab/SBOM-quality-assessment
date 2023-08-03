<script setup>
const props = defineProps(['metadata'])
</script>

<template>
    <table>
      <tr>
        <td><b>Project ID:</b></td>
        <td>{{ metadata.project_id }}</td>
      </tr>
      <tr>
        <td><b>Name:</b></td>
        <td>{{ metadata.name }}</td>
      </tr>
      <tr>
        <td><b>Container Image:</b></td>
        <td>{{ metadata.container }}</td>
      </tr>
      <tr>
        <td><b>Github Repository:</b></td>
        <td>{{ metadata.git }}</td>
      </tr>
    </table>
</template>

<style scoped>
.metadata-card {
  margin-top: 15px;
  margin-bottom: 15px;
  background-color: azure;
}
</style>