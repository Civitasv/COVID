import Vue from 'vue'
import App from './App.vue'
import router from './router/Router.js'
import store from "./store/index.js"
// 引入view-design
import ViewUI from "view-design"
// 引入css
import 'view-design/dist/styles/iview.css'
import echarts from "echarts"

Vue.config.productionTip = false
// 安装ViewUI
Vue.use(ViewUI)
// 全局作用域
Vue.prototype.$echarts = echarts

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
