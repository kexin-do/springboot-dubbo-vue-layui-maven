/**
 * ajax请求配置
 */
import axios from 'axios'
import requestConfig from './requestConfig.js'
import qs from 'qs'

// axios默认配置
// 超时时间
axios.defaults.timeout = 10000
// 默认地址
axios.defaults.baseURL = requestConfig.apiUrl
// 每次带cookies访问后台
axios.defaults.withCredentials = true
// 整理数据
axios.defaults.transformRequest = function (data) {
  data = qs.stringify(data)
  // data = JSON.stringify(data)
  return data
}

// 路由请求拦截
// http request 拦截器
axios.interceptors.request.use(
  config => {
    config.headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
    // 判断是否存在ticket，如果存在的话，则每个http header都加上ticket
    return config
  },
  error => {
    return Promise.reject(error.response)
  })

// 路由响应拦截
// http response 拦截器
axios.interceptors.response.use(
  response => {
    if (response.data.resultCode === '404') {
      console.log('response.data.resultCode是404')
    } else {
      return response
    }
  },
  error => {
    // 返回接口返回的错误信息
    return Promise.reject(error.response)
  })

export default axios
