import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from "../views/Home"
import Row from "../components/layer/Row"
import Manage from "../views/Manage"
import WorldVisualize from "../views/WorldVisual"
import ChinaVisualize from "../views/ChinaVisual"

Vue.use(VueRouter)

const routes = [
  { path: "/", name: "index", redirect: "/home" },
  { path: "/manage", name: "manage", component: Manage },
  { path: "/home", name: "home", component: Home },
  { path: "/wvisual", name: "wvisual", component: WorldVisualize },
  { path: "/cvisual", name: "cvisual", component: ChinaVisualize },
  { path: "/row", name: "row", component: Row }
]

const router = new VueRouter({
  routes
})

export default router
