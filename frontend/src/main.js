import { createApp } from 'vue'
import App from './App.vue'
import axios from 'axios'
import router from "@/router";

import { BootstrapVue3 } from 'bootstrap-vue-3'
import PrimeVue from "primevue/config"
import Button from 'primevue/button'
import Card from 'primevue/card';
import DataView from 'primevue/dataview';
import Divider from 'primevue/divider';
import Panel from 'primevue/panel';
import Accordion from 'primevue/accordion';
import AccordionTab from 'primevue/accordiontab';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import ColumnGroup from 'primevue/columngroup';
import Row from 'primevue/row';
import InputText from "primevue/inputtext";
import MultiSelect from 'primevue/multiselect';
import Skeleton from 'primevue/skeleton';
import Knob from 'primevue/knob';
import Chip from 'primevue/chip';
import TabMenu from 'primevue/tabmenu';
import InputSwitch from "primevue/inputswitch";
import Dialog from 'primevue/dialog';
import Tooltip from 'primevue/tooltip';


import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'
import "primevue/resources/themes/bootstrap4-light-blue/theme.css"
import "primevue/resources/primevue.min.css"
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';

const app = createApp(App);

// axios.defaults.baseURL = 'http://localhost:8080/'
// axios.defaults.baseURL = 'https://sbom.seclab.cs.hm.edu/'

app.use(BootstrapVue3)

app.use(PrimeVue)
app.component("p-button", Button)
app.component("p-card", Card)
app.component("p-dataView", DataView)
app.component("p-divider", Divider)
app.component("p-panel", Panel)
app.component("p-accordion", Accordion)
app.component("p-accordionTab", AccordionTab)
app.component("p-dataTable", DataTable)
app.component("p-column", Column)
app.component("p-columnGroup", ColumnGroup)
app.component("p-row", Row)
app.component("p-inputText", InputText)
app.component("p-multiSelect", MultiSelect)
app.component("p-skeleton", Skeleton)
app.component("p-knob", Knob)
app.component("p-chip", Chip)
app.component("p-tabMenu", TabMenu)
app.component("p-dialog", Dialog)
app.component("p-inputSwitch", InputSwitch)

app.directive('p-tooltip', Tooltip)

app.use(router)
app.mount('#app')
