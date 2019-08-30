/** 处理页面跳转和页面参数传递 **/
import services from './orgService'

const name = 'user'

const data = function () {
  let checkSts = (rule, value, callback) => {
    if (!value) {
      return callback(new Error('机构状态不能为空'))
    }
    setTimeout(() => {
      if (!Number.isInteger(value)) {
        callback(new Error('请输入数字值'))
      } else {
        if (value > 2 && value < 0) {
          callback(new Error('可输入值必须为 0 和 1'))
        } else {
          callback()
        }
      }
    }, 1000)
  }
  return {
    params: {
      orgName: '',
      orgCode: '',
      pageSize: 10,
      pageNum: 1
    },
    addObj: {
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
      linkMan: ''
    },
    editFormRules: {
      orgName: [
        { required: true, message: '请输入机构名称', trigger: 'blur' }
      ],
      orgCode: [
        { required: true, message: '请输入机构编号', trigger: 'blur' },
        { min: 14, max: 14, message: '长度必须为14个字符', trigger: 'blur' }
      ],
      orgSts: [
        { validator: checkSts, trigger: 'blur' }
      ]
    },
    editObj: {
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
      linkMan: ''
    },
    loading: false,
    list: [],
    total: 0,
    editObjShow: false,
    addObjShow: false,
    editOrAddWidth: '80%',
    showLoading: false,
    searchLoading: false,
    editLoading: false,
    addLoading: false,
    listLoading: false,
    delLoading: false
  }
}

const methods = {
  formatSts: function (row, column, cellValue, index) {
    return cellValue === 0 ? '正常' : '停用'
  },
  formatTime: function (row, column, cellValue, index) {
    return this.$dateFormat.format(new Date(cellValue))
  },
  pageChange: function (pageNum) {
    this.params.pageNum = pageNum
  },
  search: function () {
    this.searchLoading = true
    services.list(this.params)
      .then((success) => {
        this.list = success.data.rows
        this.total = success.data.total
      })
      .catch((fail) => {

      })
      .finally(() => {
        this.searchLoading = false
      })
  },
  show: function (row) {
    this.showLoading = true
    console.log(JSON.stringify(row))
    services.selectOne({id: row.id})
      .then((success) => {

      })
      .catch((fail) => {

      })
      .finally(() => {
        this.showLoading = false
      })
  },
  editShow: function (row) {
    this.editObjShow = true
    services.selectOne({orgId: row.orgId})
      .then((success) => {
        this.editObj = success.data.data.orgInfo
      })
      .catch((fail) => {

      })
      .finally(() => {
      })
  },
  edit: function (formName) {
    console.log(formName)
    this.$refs[formName].validate((valid) => {
      console.log(valid)
      if (valid) {
        console.log('进行异步')
        this.$confirm('确认提交吗？', '提示', {}).then(() => {
          this.edit(this.editObj).then((success) => {
            this.editLoading = false
            this.$message({
              message: '提交成功',
              type: 'success'
            })
            this.$refs['editForm'].resetFields()
            this.editObjShow = false
            this.search()
          }).catch((fail) => {
          }).finally(() => {
          })
        })
      } else {
        return false
      }
    })
    services.edit(this.editObj)
      .then((success) => {
        this.editObjShow = false
      })
      .catch((fail) => {

      })
      .finally(() => {
        this.loading = false
      })
  },
  del: function (row) {
    this.delLoading = true
    services.del({id: row.id})
      .then((success) => {

      })
      .catch((fail) => {

      })
      .finally(() => {
        this.delLoading = false
      })
  },
  addShow: function (row) {
    this.addObjShow = true
  },
  add: function (row) {
    this.addLoading = true
    console.log(JSON.parse(row))
    services.add(this.addObj)
      .then((success) => {
      })
      .catch((fail) => {

      })
      .finally(() => {
        this.addLoading = false
      })
  }
}

const computed = {

}

const components = {
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
