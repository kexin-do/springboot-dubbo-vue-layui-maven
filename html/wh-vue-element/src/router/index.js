/* eslint-disable */

import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import login from '@/components/login'
import index from '@/components/index'
import user from '@/views/front/auth/user/index'
import org from '@/views/front/auth/org/index'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      redirect:'/login'
    },
    {
      path: '/login',
      name: 'login',
      component:login
    },
    {
      path: '/index',
      name: 'index',
      component: index,
      children: [
        {
          name:'org',
          path: '/org/orgManage',
          component: org
        },
        {
          name: 'user',
          path: '/user/userManage',
          component: user
        },
        {
          name:'credit',
          path: '/creditUser/index',
          component: {
            template: '<div>credit</div>'
          }
        }
      ]
    },
    {
      path: '*',
      redirect: '/index'
    }
  ]
})

