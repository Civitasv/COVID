import Vue from 'vue'
import App from './App.vue'
import router from './router/Router.js'
import store from "./store/index.js"
// 引入element
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import * as echarts from 'echarts';
import 'mapbox-gl/dist/mapbox-gl.css'
import VueiClient from '@supermap/vue-iclient-mapboxgl';
import "./assets/css/app.css"
// 粒子
import VueParticles from 'vue-particles'

Vue.use(VueParticles)
Vue.use(ElementUI);
Vue.use(VueiClient);
Vue.config.productionTip = false

// 全局作用域
Vue.prototype.$echarts = echarts

router.beforeEach((to, _from, next) => {
  if (to.meta.requireAuth) {
    // 获取 JWT Token
    if (to.path !== '/login' && !window.localStorage.getItem('JWT_TOKEN')) {
      next({
        path: 'login',
        query: { redirect: to.fullPath }
      })
    }
    else {
      next()
    }
  }
  else {
    next();
  }
}
)

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
