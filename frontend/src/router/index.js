import {createRouter, createWebHashHistory } from "vue-router";
import Home from "@/views/home.vue";
import About from "@/views/about.vue";
import SbomFiles from "@/views/sbom-files.vue";
import Plots from "@/views/plots.vue";
import Project from "@/views/project/project.vue";
import Insights from "@/views/project/insights.vue";
import Dependencies from "@/views/project/dependencies.vue";
import Licenses from "@/views/project/licenses.vue";
import SbomInsights from "@/views/sbom-insights.vue";

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
      path: '/project/:project_id',
      name: 'Project',
      component: Project,
      children: [
        {
            path: 'insights',
            name: 'Project insights',
            component: Insights
        },
        {
            path: 'dependencies',
            name: 'Dependencies',
            component: Dependencies
        },
        {
            path: 'licenses',
            name: 'Licenses',
            component: Licenses
        }
      ]
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