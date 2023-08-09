<script setup>

</script>

<template>
  <b-navbar toggleable="md" type="dark" variant="dark" sticky="true">
    <b-navbar-brand class="navbar-custom-brand">SBOM Quality Assessment</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
      </b-navbar-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-nav-item>
          <router-link class="navbar-custom-brand" to="/">Projects</router-link>
        </b-nav-item>
        <b-nav-item>
          <router-link class="navbar-custom-brand" to="/sbom-files">SBOMs</router-link>
        </b-nav-item>
        <b-nav-item>
          <router-link class="navbar-custom-brand" to="/plots">Plots</router-link>
        </b-nav-item>
        <b-nav-item>
          <router-link class="navbar-custom-brand" to="/about">About</router-link>
        </b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<style scoped>
.navbar {
  margin-left: auto !important;
}

.navbar-custom-brand {
  color: white;
}


</style>