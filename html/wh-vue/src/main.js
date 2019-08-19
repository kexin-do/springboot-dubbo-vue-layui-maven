/* eslint-disable */
// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuex from 'vuex';
import axios from 'axios'
import qs from 'qs';

import router from './router'
import store from './utils/store'

Vue.use(Vuex)
Vue.prototype.$store = store;
Vue.prototype.$axios = axios;
Vue.prototype.$qs = qs;
axios.defaults.baseURL = 'http://127.0.0.1:7999/';
Vue.config.productionTip = false;

$( ()=>{  //这里用到了jquery，所以要配置好jquery；
  const configs = ['layer', 'laydate', 'laypage', 'laytpl', 'layedit',
    'form', 'upload', 'tree', 'table', 'element', 'util',
    'flow', 'carousel', 'rate', 'transfer' ];
  layui.config( {
    base : 'static/js/layui/'   //配置 layui 第三方扩展组件存放的基础目录
  })
    .use( configs, ()=>{
      const app = new Vue({
        router:router,
        store:store
      }).$mount('#app');
    })
})


