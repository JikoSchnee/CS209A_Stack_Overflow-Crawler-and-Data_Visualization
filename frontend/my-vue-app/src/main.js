import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import request from "@/utils/request";
import Fragment from 'vue-fragment'

Vue.config.productionTip = false

Vue.use(ElementUI);
Vue.use(Fragment.Plugin)

// 全局挂载axios
Vue.prototype.request = request


new Vue({
  render: h => h(App)
}).$mount('#app')
