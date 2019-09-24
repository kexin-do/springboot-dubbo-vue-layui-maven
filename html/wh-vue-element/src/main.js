/* eslint-disable */
import Vue from 'vue'
import Vuex from 'vuex'
// element-ui模块引入
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
// fa字体图标引用
import 'font-awesome/css/font-awesome.min.css'
// qs用于ajax请求时json和string互转
import qs from 'qs'

import router from '@/router'
import store from '@/utils/vuex/store'
import dateFormat from '@/utils/dateFormat'
import http from '@/utils/ajax/http'
import App from '@/App'

Vue.use(Vuex);
Vue.use(ElementUI);
Vue.prototype.$dateFormat = dateFormat;
Vue.prototype.$store = store;
Vue.prototype.$axios = http;
Vue.prototype.$qs = qs;
Vue.config.productionTip = false;

const app = new Vue({
  router:router,
  store:store,
  render: (h) => h(App)
}).$mount('#app');


