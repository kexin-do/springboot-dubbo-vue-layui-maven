/** 处理页面与后台交互向后台传递参数 **/

const services = {
  $axios: {},
  list: function (params) {
    return this.$axios({
      method: 'post',
      url: '/org/list',
      data: params
    })
  },
  selectOne: function (params) {
    return this.$axios({
      method: 'post',
      url: '/org/selectOne',
      data: params
    })
  },
  edit: function (params) {
    return this.$axios({
      method: 'post',
      url: '/org/edit',
      data: params
    })
  },
  del: function (params) {
    return this.$axios({
      method: 'post',
      url: '/org/delete',
      data: params
    })
  },
  changeStatusStart: function (params) {
    return this.$axios({
      method: 'post',
      url: '/org/start',
      data: params
    })
  },
  changeStatusStop: function (params) {
    return this.$axios({
      method: 'post',
      url: '/org/stop',
      data: params
    })
  },
  add: function (params) {
    return this.$axios({
      method: 'post',
      url: '/org/add',
      data: params
    })
  }
}

export default services
