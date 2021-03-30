import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from "../views/Home"
import Manage from "../views/Manage"
import Login from "../views/Login"
import Regist from "../views/Regist"
import First from "../components/visual/First"
import Second from "../components/visual/Second"
import Dashboard from "../views/Dashboard"
Vue.use(VueRouter)

const routes = [
  { path: "/", name: "index", redirect: "/home" },
  { path: "/home", name: "home", component: Home },
  {
    path: "/manage", name: "manage", component: Manage, meta: {
      requireAuth: true
    }
  },
  {
    path: "/dashboard", name: "dashboard", component: Dashboard, meta: {
      requireAuth: true
    }, children: [
      {
        path: '', component: First, meta: {
          requireAuth: true
        },
      },
      {
        // 当 /dashboard/first 匹配成功，
        // First 会被渲染在 Dashboard 的 <router-view> 中
        path: 'first',
        component: First, meta: {
          requireAuth: true
        },
      },
      {
        path: 'second',
        component: Second, meta: {
          requireAuth: true
        },
      }
    ]
  },
  {
    path: "/login", name: "login", component: Login
  },
  {
    path: "/regist", name: "regist", component: Regist
  },
]

const router = new VueRouter({
  routes
})

export default router
