import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from "../views/Home"
import Manage from "../views/Manage"
import WorldVisualize from "../views/WorldVisual"
import ChinaVisualize from "../views/ChinaVisual"
import Login from "../views/Login"
import Regist from "../views/Regist"
import First from "../components/visual/First"
import Second from "../components/visual/Second"
import Dashboard from "../views/Dashboard"
Vue.use(VueRouter)

const routes = [
  { path: "/", name: "index", redirect: "/dashboard/first" },
  {
    path: "/manage", name: "manage", component: Manage, meta: {
      requireAuth: true
    }
  },
  {
    path: "/dashboard", name: "dashboard", component: Dashboard, children: [
      { path: '', component: First },
      {
        // 当 /dashboard/first 匹配成功，
        // First 会被渲染在 Dashboard 的 <router-view> 中
        path: 'first',
        component: First
      },
      {
        path: 'second',
        component: Second
      }
    ]
  },
  { path: "/wvisual", name: "wvisual", component: WorldVisualize },
  { path: "/cvisual", name: "cvisual", component: ChinaVisualize },
  {
    path: "/login", name: "login", component: Login
  },
  {
    path: "/regist", name: "regist", component: Regist
  },
  { path: "/test", name: "test", component: Home },
]

const router = new VueRouter({
  routes
})

export default router
