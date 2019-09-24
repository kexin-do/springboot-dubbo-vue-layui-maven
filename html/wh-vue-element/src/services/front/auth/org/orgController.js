/** 处理页面跳转和页面参数传递 **/
// ajax请求
import services from './orgService'
// 编辑页面
import orgEdit from '@/components/front/auth/org/edit'
// 新增页面
import orgAdd from '@/components/front/auth/org/add'
// 详情页面
import orgDetail from '@/components/front/auth/org/detail'

// 当前模块名称
const name = 'org'

// 初始化数据
const data = function () {
  return {
    // ajax请求参数
    params: {
      orgName: '',
      orgCode: '',
      startTime: '',
      endTime: '',
      pageSize: 10,
      pageNum: 1
    },
    // 时间选择框参数
    selectDate: [],
    // 对应该模块的实体类，用于新增页面以及查看和编辑页面的数据回显
    currentVo: {
      orgId: '',
      orgCode: '',
      orgName: '',
      orgFName: '',
      orgEName: '',
      orgAddress: '',
      areaCode: '',
      orgSts: '',
      reportSaveRule: '',
      reportPrintRule: '',
      timeLimit: '',
      address: '',
      linkMan: '',
      pOrgName: '',
      linkMode: '',
      otherLinkMode: ''
    },
    // 后台返回结果集
    list: [],
    // 总条数
    total: 0,
    // 弹出窗口的控制信息
    showDialog: {
      edit: false,
      add: false,
      detail: false
    },
    // 弹出窗口的宽度
    editOrAddWidth: '75%',
    // 点击对应按钮后出现loading，不能重复提交同一信息
    loading: {
      detail: false,
      search: false,
      edit: false,
      add: false,
      list: false,
      del: false
    },
    // 用于新增和编辑页面的数据校验
    formRules: {
      orgName: [
        { required: true, message: '请输入机构名称', trigger: 'blur' }
      ],
      orgCode: [
        { required: true, message: '请输入机构编号', trigger: 'blur' },
        { min: 14, max: 14, message: '长度必须为14个字符', trigger: 'blur' }
      ],
      orgSts: [
        { required: true, message: '请选择机构状态', trigger: 'blur' }
      ]
    },
    // 页面每页显示条数配置
    pageSizes: [10, 20, 50, 100],
    // 时间选择框快捷设置
    pickerOptions: {
      shortcuts: [{
        text: '最近一周',
        onClick (picker) {
          const end = new Date()
          const start = new Date()
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
          picker.$emit('pick', [start, end])
        }
      }, {
        text: '最近一个月',
        onClick (picker) {
          const end = new Date()
          const start = new Date()
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
          picker.$emit('pick', [start, end])
        }
      }, {
        text: '最近三个月',
        onClick (picker) {
          const end = new Date()
          const start = new Date()
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
          picker.$emit('pick', [start, end])
        }
      }]
    }
  }
}

// 模块内定义的方法
const methods = {
  // 时间格式化
  formatTime: function (row, column, cellValue, index) {
    return this.$dateFormat.format(new Date(cellValue))
  },
  // 当前页改变触发事件
  pageChange: function (pageNum) {
    this.params.pageNum = pageNum
    this.search()
  },
  // 每页显示条数改变触发事件
  sizeChange: function (pageSize) {
    this.params.pageSize = pageSize
    this.search()
  },
  // 时间区间选择框改变触发事件
  searchDateChange: function () {
    this.params.startTime = this.selectDate[0]
    this.params.endTime = this.selectDate[1]
  },
  // 查询按钮对应的方法
  search: function () {
    this.loading.search = true
    services.list(this.params)
      .then((success) => {
        console.log(success.data.data.rows)
        this.list = success.data.data.rows
        this.total = success.data.data.total
      })
      .catch((fail) => {
        this.$message({
          message: '请求失败',
          type: 'error',
          showClose: true,
          offset: '120'
        })
      })
      .finally(() => {
        this.loading.search = false
      })
  },
  // 查询单条信息并打开打开详情页面
  showDetail: function (row) {
    services.selectOne({orgId: row.orgId})
      .then((success) => {
        this.currentVo = {}
        this.currentVo = success.data.data.orgInfo
        this.showDialogWindow('detail')
      })
      .catch((fail) => {

      })
      .finally(() => {
        this.loading.detail = false
      })
  },
  // 查询单条信息并打开编辑页面
  editShow: function (row) {
    services.selectOne({orgId: row.orgId})
      .then((success) => {
        this.currentVo = {}
        this.currentVo = success.data.data.orgInfo
        this.showDialogWindow('edit')
      })
      .catch((fail) => {

      })
      .finally(() => {
      })
  },
  // 修改方法
  edit: function (data) {
    this.loading.edit = true
    services.edit(data).then((success) => {
      console.log(success)
      this.$message({
        message: '提交成功',
        type: 'success'
      })
      this.cancelShowWindow('edit')
      this.search()
      this.currentVo = {}
    }).catch((fail) => {
    }).finally(() => {
      this.loading.edit = false
    })
  },
  // 通过类型来显示窗口的方法
  showDialogWindow: function (type) {
    this.currentVo = {}
    if (type === 'add') {
      this.showDialog.add = true
    } else if (type === 'edit') {
      this.showDialog.edit = true
    } else if (type === 'detail') {
      this.showDialog.detail = true
    }
  },
  // 通过类型来关闭窗口的方法
  cancelShowWindow: function (type) {
    if (type === 'add') {
      this.showDialog.add = !this.showDialog.add
    } else if (type === 'edit') {
      this.showDialog.edit = !this.showDialog.edit
    } else if (type === 'detail') {
      this.showDialog.detail = !this.showDialog.detail
    }
  },
  // 修改状态
  changeStatus: function (row) {
    if (row.orgSts === '1') {
      this.$confirm('确认停用【' + row.orgName + '】吗？', '提示', {type: 'warning'}).then(() => {
        services.changeStatusStop({orgId: row.orgId})
          .then((success) => {
            this.$message({
              message: '停用成功',
              type: 'success'
            })
            this.search()
          })
          .catch((fail) => {

          })
          .finally(() => {
          })
      }).catch(() => {
      })
    } else {
      this.$confirm('确认启用【' + row.orgName + '】吗？', '提示', {type: 'warning'}).then(() => {
        services.changeStatusStart({orgId: row.orgId})
          .then((success) => {
            this.$message({
              message: '启用成功',
              type: 'success'
            })
            this.search()
          })
          .catch((fail) => {

          })
          .finally(() => {
          })
      }).catch(() => {
      })
    }
  },
  // 删除方法
  del: function (row) {
    this.loading.del = true
    services.del({id: row.id})
      .then((success) => {

      })
      .catch((fail) => {

      })
      .finally(() => {
        this.loading.del = false
      })
  },
  // 新增方法
  add: function (data) {
    this.loading.add = true
    services.add(data)
      .then((success) => {
        console.log(success)
        this.$message({
          message: '提交成功',
          type: 'success'
        })
        this.cancelShowWindow('add')
        this.search()
        this.currentVo = {}
      })
      .catch((fail) => {

      })
      .finally(() => {
        this.loading.add = false
      })
  }
}

// 计算相关模块
const computed = {

}

// 外部组件加载模块
const components = {
  'org-edit': orgEdit,
  'org-add': orgAdd,
  'org-detail': orgDetail
}

// 监听器
const watch = {

}

// 页面初始化完成后执行的方法
function created () {
  // 为services模块初始化一步查询对象
  services.$axios = this.$axios
}

// 页面完全绘制完成后执行的方法
function mounted () {
  this.search()
}

// 导出模块
export default {
  name: name,

  data: data,

  components: components,

  created: created,

  computed: computed,

  watch: watch,

  methods: methods,

  mounted: mounted
}
