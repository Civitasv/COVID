import Vue from 'vue'
import VueRouter from 'vue-router'
import Virus from "../views/Virus"
import UploadData from "../views/Upload"
import Home from "../views/Home"
Vue.use(VueRouter)

const routes = [
  { path: "/", name: "index", redirect: "/home" },
  { path: "/virus", name: "virus", component: Virus },
  { path: "/upload", name: "upload", component: UploadData },
  { path: "/home", name: "home", component: Home }
]

const router = new VueRouter({
  routes
})

export default router
