import Vue from 'vue'
import VueRouter from 'vue-router'
import Virus from "../views/Virus"
//import GeoServer from "../views/GeoServer"
import GeoServer2 from "../views/GeoServer2"
//import iserver from "../views/iserver"

import UploadData from "../views/Upload"
import Home from "../views/Home"
import Visualize from "../views/Visualize"

Vue.use(VueRouter)

const routes = [
  { path: "/", name: "index", redirect: "/home" },
  { path: "/virus", name: "virus", component: Virus },
  //{ path: "/geo", name: "geo", component: GeoServer },
  { path: "/geo2", name: "geo2", component: GeoServer2 },
  //{ path: "/iserver", name: "iserver", component: iserver },
  { path: "/upload", name: "upload", component: UploadData },
  { path: "/home", name: "home", component: Home },
  { path: "/visual", name: "visualize", component: Visualize },
]

const router = new VueRouter({
  routes
})

export default router
