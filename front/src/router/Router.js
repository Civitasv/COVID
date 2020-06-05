import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from "../views/Home"
import Manage from "../views/Manage"
import WorldVisualize from "../views/WorldVisual"
import ChinaVisualize from "../views/ChinaVisual"
import Login from "../views/Login"

Vue.use(VueRouter)

const routes = [
  { path: "/", name: "index", redirect: "/home" },
  {
    path: "/manage", name: "manage", component: Manage, meta: {
      requireAuth: true
    }
  },
  { path: "/home", name: "home", component: Home },
  { path: "/wvisual", name: "wvisual", component: WorldVisualize },
  { path: "/cvisual", name: "cvisual", component: ChinaVisualize },
  {
    path: "/login", name: "login", component: Login
  }
]

const router = new VueRouter({
  routes
})

export default router
