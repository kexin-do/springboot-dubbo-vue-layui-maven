/** 处理页面与后台交互向后台传递参数 **/
/**
 * 为了模块编码方便，请将所有的方法、属性、url等信息都按照如下定义方式命名
 *     类别                方法名                       url
 * 结果集请求方法:           list                       org/list
 * 查询单个请求方法:         selectOne                  org/selectOne
 * 修改请求方法：            edit                       org/edit
 * 新增请求方法：            add                        org/add
 * 删除请求方法：            del                        org/delete
 * 启用状态方法：            changeStatusStart          org/changeStatusStart
 * 停用状态方法：            changeStatusStop           org/changeStatusStop
 */

// 对应模块的ajax请求信息
const services = {
  // 在页面绘制完成后初始化
  $axios: {},
  // 列表查询方法
  list: function (params) {
    return this.$axios({
      method: 'post',
      url: '/org/list',
      data: params
    })
  },
  // 单个查询方法
  selectOne: function (params) {
    return this.$axios({
      method: 'post',
      url: '/org/selectOne',
      data: params
    })
  },
  // 修改方法
  edit: function (params) {
    return this.$axios({
      method: 'post',
      url: '/org/edit',
      data: params
    })
  },
  // 删除方法
  del: function (params) {
    return this.$axios({
      method: 'post',
      url: '/org/delete',
      data: params
    })
  },
  // 启用方法
  changeStatusStart: function (params) {
    return this.$axios({
      method: 'post',
      url: '/org/start',
      data: params
    })
  },
  // 停用方法
  changeStatusStop: function (params) {
    return this.$axios({
      method: 'post',
      url: '/org/stop',
      data: params
    })
  },
  // 新增方法
  add: function (params) {
    return this.$axios({
      method: 'post',
      url: '/org/add',
      data: params
    })
  }
}

// 导出模块
export default services
