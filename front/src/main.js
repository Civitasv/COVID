import Vue from 'vue'
import App from './App.vue'
import router from './router/Router.js'
import store from "./store/index.js"
// 引入element
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import echarts from "echarts"
import 'mapbox-gl/dist/mapbox-gl.css'
import VueiClient from '@supermap/vue-iclient-mapboxgl';
import "./assets/css/app.css"

// 粒子库
import VueParticles from 'vue-particles'

Vue.use(VueParticles)
Vue.use(ElementUI);
Vue.use(VueiClient);
Vue.config.productionTip = false

// 全局作用域
Vue.prototype.$echarts = echarts
new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
