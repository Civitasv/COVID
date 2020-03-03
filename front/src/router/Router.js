import Vue from 'vue'
import VueRouter from 'vue-router'
import Virus from "../views/Virus"

Vue.use(VueRouter)

const routes = [
  { path: "/", name: "index", redirect: "/home" },
  { path: "/home", name: "home", component: Virus }
]

const router = new VueRouter({
  routes
})

export default router
