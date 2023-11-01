import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router';//如果router文件夹下有index.js就不需要'./router/index.js'
import store from './store'
import global from './globalFun'
//import '@/api/mock'
import '@/permission' // permission control

Vue.config.productionTip = false
Vue.use(ElementUI);
new Vue({
  router,//挂载路由
  store,
  render: h => h(App),
}).$mount('#app')
