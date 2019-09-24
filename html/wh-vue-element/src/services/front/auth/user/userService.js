/** 处理页面与后台交互向后台传递参数 **/

const services = {
  $axios: {},
  list: function (params) {
    return this.$axios({
      method: 'post',
      url: '/user/list',
      data: params
    })
  },
  selectOne: function (params) {
    return this.$axios({
      method: 'post',
      url: '/user/selectOne',
      data: params
    })
  },
  edit: function (params) {
    return this.$axios({
      method: 'post',
      url: '/user/edit',
      data: params
    })
  },
  del: function (params) {
    return this.$axios({
      method: 'post',
      url: '/user/delete',
      data: params
    })
  },
  changeStatusStart: function (params) {
    return this.$axios({
      method: 'post',
      url: '/user/start',
      data: params
    })
  },
  changeStatusStop: function (params) {
    return this.$axios({
      method: 'post',
      url: '/user/stop',
      data: params
    })
  },
  changeStatusLock: function (params) {
    return this.$axios({
      method: 'post',
      url: '/user/lock',
      data: params
    })
  },
  changeStatusUnLock: function (params) {
    return this.$axios({
      method: 'post',
      url: '/user/unLock',
      data: params
    })
  },
  add: function (params) {
    return this.$axios({
      method: 'post',
      url: '/user/add',
      data: params
    })
  }
}

export default services
