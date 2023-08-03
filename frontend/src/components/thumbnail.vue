<script>
  export default {
    props: ['title', 'map', 'menu']
  }
</script>

<template >
  <div class="thumbnail">
    <div class="container">
      <div class="grid">
        <div class="col-4">
          <div class="grid" style="height: 100%">
            <div class="col-12 m-2" style="height: 50%">
              <h1 class="thumb-color">{{ title }}</h1>
            </div>
            <div v-if="typeof menu != 'undefined'" style="height: 50%; display: flex; justify-content: flex-end; flex-direction: column">
              <div style="display:inline-flex">
                <router-link v-for="item of menu" :to="item.to">
                  <p-button style="vertical-align: bottom; color: whitesmoke" :label="item.label" link size="large"/>
                </router-link>
              </div>
            </div>
          </div>
        </div>
        <div class="col-8">
          <div class="ext-container">
            <div v-for="[key, value] in map" class="aspect">
              <b class="thumb-color" style="font-size: 1.5em; line-heigght: normal">{{ key }}<br/></b>
              <b class="thumb-color" style="font-size: 4em">{{ value }}</b>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.thumbnail {
  width: 100%;
  background: linear-gradient(to right, #45208C, #009DA7);
}

.aspect {
  margin: 2em;
  text-align: right;
  line-height: 1;
}
.ext-container {
  justify-content: right;
  display: flex;
  width: 100%
}
.thumb-color {
  color: white;
}

</style>