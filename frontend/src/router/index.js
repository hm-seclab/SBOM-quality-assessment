import {createRouter, createWebHashHistory } from "vue-router";
import Home from "@/views/home.vue";
import DependencyResults from "@/views/dependency-results.vue";
import LicenseResults from "@/views/license-results.vue";
import SpdxInsights from "@/views/project-insights.vue";
import About from "@/views/about.vue";
import SbomFiles from "@/views/sbom-files.vue";
import SbomInsights from "@/views/sbom-insights.vue";
import Plots from "@/views/plots.vue";

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
      path: '/plots',
      name: 'Plots',
      component: Plots
    },
    {
        path: '/sbom-files',
        name: 'SBOM files',
        component: SbomFiles
    },
    {
        path: '/project-insights',
        name: 'Project insights',
        component: SpdxInsights
    },
    {
        path: '/dependency-results',
        name: 'Dependency results',
        component: DependencyResults
    },
    {
        path: '/license-results',
        name: 'License results',
        component: LicenseResults
    },
    {
      path: '/sbom-insights',
      name: 'SBOM Insights',
      component: SbomInsights
    },
    {
        path: '/about',
        name: 'about page',
        component: About
    }
]

const router = createRouter ({
    history: createWebHashHistory(),
    routes
})

export default router