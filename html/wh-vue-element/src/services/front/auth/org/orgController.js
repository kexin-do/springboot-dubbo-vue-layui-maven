/** 处理页面跳转和页面参数传递 **/
import services from './orgService'
import orgEdit from '@/components/front/org/edit'
import orgAdd from '@/components/front/org/add'
import orgDetail from '@/components/front/org/detail'

const name = 'org'

const data = function () {
  return {
    params: {
      orgName: '',
      orgCode: '',
      startTime: '',
      endTime: '',
      pageSize: 10,
      pageNum: 1
    },
    selectDate: [],
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
    list: [],
    total: 0,
    showDialog: {
      edit: false,
      add: false,
      detail: false
    },
    editOrAddWidth: '75%',
    loading: {
      detail: false,
      search: false,
      edit: false,
      add: false,
      list: false,
      del: false
    },
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
    pageSizes: [10, 20, 50, 100],
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

const methods = {
  formatSts: function (row, column, cellValue, index) {
    return cellValue === '1' ? '正常' : '停用'
  },
  formatTime: function (row, column, cellValue, index) {
    return this.$dateFormat.format(new Date(cellValue))
  },
  pageChange: function (pageNum) {
    this.params.pageNum = pageNum
    this.search()
  },
  sizeChange: function (pageSize) {
    this.params.pageSize = pageSize
    this.search()
  },
  searchDateChange: function () {
    this.params.startTime = this.selectDate[0]
    this.params.endTime = this.selectDate[1]
  },
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
  showDetail: function (row) {
    this.showDialogWindow('detail')
    services.selectOne({orgId: row.orgId})
      .then((success) => {
        this.currentVo = {}
        this.currentVo = success.data.data.orgInfo
      })
      .catch((fail) => {

      })
      .finally(() => {
        this.loading.detail = false
      })
  },
  editShow: function (row) {
    this.showDialogWindow('edit')
    services.selectOne({orgId: row.orgId})
      .then((success) => {
        this.currentVo = {}
        this.currentVo = success.data.data.orgInfo
      })
      .catch((fail) => {

      })
      .finally(() => {
      })
  },
  edit: function (data) {
    console.log(data)
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
  cancelShowWindow: function (type) {
    if (type === 'add') {
      this.showDialog.add = !this.showDialog.add
    } else if (type === 'edit') {
      this.showDialog.edit = !this.showDialog.edit
    } else if (type === 'detail') {
      this.showDialog.detail = !this.showDialog.detail
    }
  },
  changeStatus: function (row) {
    if (row.orgSts === '1') {
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
    } else {
      services.changeStatusStart({orgId: row.orgId})
        .then((success) => {
          this.$message({
            message: '启用成功',
            type: 'success'
          })
          this.search();
        })
        .catch((fail) => {

        })
        .finally(() => {
        })
    }
  },
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

const computed = {

}

const components = {
  'org-edit': orgEdit,
  'org-add': orgAdd,
  'org-detail': orgDetail
}

const watch = {

}

function created () {
  services.$axios = this.$axios
}

function mounted () {
  this.search()
}

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
