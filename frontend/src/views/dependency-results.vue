<script>
import Thumbnail from "@/components/thumbnail.vue";
import ProjectMetadata from "@/components/project-metadata.vue";
import DependencyTable from "@/components/dependency-table.vue";
import SbomFilesTable from "@/components/sbom-files-table.vue";
import {
  retrieveCdxDependencyListByName,
  retrieveCdxDependencyListByNameVersion,
  retrieveCdxDependencyListByPurl,
  retrieveCdxDependencyListByPurlVersion,
  retrieveDependencyListByName,
  retrieveDependencyListByNameVersion,
  retrieveDependencyListByRefLoc,
  retrieveDependencyListByRefLocCompressedVersion,
  retrieveDependencyListByRefLocVersion,
  retrieveSbomMetadata
} from "@/utils/api";

export default {
  components: {SbomFilesTable, ProjectMetadata, DependencyTable, Thumbnail},
  created() {
    this.fetchMetadata()
    this.setTabMenus()
  },
  data() {
    return {
      tabmenu: [],
      metadata: {},
      activeTab: 0,
      comparisonName: {},
      comparisonRef: {},
      comparisonRefAgg: {},
      comparisonRefUnVer: {},
      thumb: new Map()
    }
  },
  methods: {
    retrieveDependencyListByNameVersion,
    retrieveDependencyListByRefLocCompressedVersion,
    retrieveDependencyListByRefLocVersion,
    retrieveDependencyListByName,
    retrieveDependencyListByRefLoc,
    retrieveCdxDependencyListByName,
    retrieveCdxDependencyListByNameVersion,
    retrieveCdxDependencyListByPurl,
    retrieveCdxDependencyListByPurlVersion,
    fetchMetadata() {
      retrieveSbomMetadata().then(response => {
        const project_id = parseInt(this.$route.query.project_id)
        this.metadata = response.filter(x => x.project_id === project_id).sort((x, y) => (x.mode + x.generator).localeCompare(y.mode + y.generator))
        this.thumb.set('Generators', this.metadata.length)
      })
    },
    setTabMenus() {
      this.tabmenu = [
        {
          label: 'Spdx Insights',
          to: '/project-insights?project_id=' + this.$route.query.project_id
        },
        {
          label: 'Dependencies',
          to: '/dependency-results?project_id=' + this.$route.query.project_id
        },
        {
          label: 'Licenses',
          to: '/license-results?project_id=' + this.$route.query.project_id
        }
      ]
    }
  }
}
</script>

<template>
  <thumbnail :title="metadata[0].name" :map="thumb" :menu="tabmenu"/>
  <div class="container">
    <p-divider/>
    <p-accordion>
      <p-accordionTab header="Project Metadata">
        <project-metadata :metadata="metadata[0]"/>
      </p-accordionTab>
      <p-accordionTab header="SBOM details">
        <sbom-files-table :data="metadata"/>
      </p-accordionTab>
    </p-accordion>
    <p-divider/>

    <div>
      <b-tabs content-class="mt-3" v-model="this.activeTab">
        <b-tab title="by Orig Ref" active lazy>
          <KeepAlive>
            <dependency-table :endpoint="() => retrieveDependencyListByRefLoc(this.$route.query.project_id)" :metadata="metadata"/>
          </KeepAlive>
        </b-tab>
        <b-tab title="by compact Ref" lazy>
          <KeepAlive>
            <dependency-table :endpoint="() => retrieveDependencyListByRefLoc(this.$route.query.project_id)" :metadata="metadata"/>
          </KeepAlive>
        </b-tab>
        <b-tab title="by Name" lazy>
          <KeepAlive>
            <dependency-table :endpoint="() => retrieveDependencyListByName(this.$route.query.project_id)" :metadata="metadata"/>
          </KeepAlive>
        </b-tab>
        <b-tab title="by Orig Ref (with version)" lazy>
          <KeepAlive>
            <dependency-table :endpoint="() => retrieveDependencyListByRefLocVersion(this.$route.query.project_id)" :metadata="metadata"/>
          </KeepAlive>
        </b-tab>
        <b-tab title="by compact Ref (with version)" lazy>
          <KeepAlive>
            <dependency-table :endpoint="() => retrieveDependencyListByRefLocCompressedVersion(this.$route.query.project_id)" :metadata="metadata"/>
          </KeepAlive>
        </b-tab>
        <b-tab title="by Name (with version)" lazy>
          <KeepAlive>
            <dependency-table :endpoint="() => retrieveDependencyListByNameVersion(this.$route.query.project_id)" :metadata="metadata"/>
          </KeepAlive>
        </b-tab>
        <b-tab title="Cdx by Name" lazy>
          <KeepAlive>
            <dependency-table :endpoint="() => retrieveCdxDependencyListByName(this.$route.query.project_id)" :metadata="metadata"/>
          </KeepAlive>
        </b-tab>
        <b-tab title="Cdx by Name (with version)" lazy>
          <KeepAlive>
            <dependency-table :endpoint="() => retrieveCdxDependencyListByNameVersion(this.$route.query.project_id)" :metadata="metadata"/>
          </KeepAlive>
        </b-tab>
        <b-tab title="Cdx by Purl" lazy>
          <KeepAlive>
            <dependency-table :endpoint="() => retrieveCdxDependencyListByPurl(this.$route.query.project_id)" :metadata="metadata"/>
          </KeepAlive>
        </b-tab>
        <b-tab title="Cdx by Purl (with version)" lazy>
          <KeepAlive>
            <dependency-table :endpoint="() => retrieveCdxDependencyListByPurlVersion(this.$route.query.project_id)" :metadata="metadata"/>
          </KeepAlive>
        </b-tab>
      </b-tabs>
    </div>
  </div>
</template>

<style scoped>

</style>