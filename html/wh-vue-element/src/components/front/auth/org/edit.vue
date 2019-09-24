<template>
  <div>
    <el-form :model="data" label-position="right" :status-icon="true" :inline="true" size="small" label-width="100px" :rules="checkRules" ref="editForm">
      <el-form-item label="机构名称" prop="orgName">
        <el-input v-model="data.orgName" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="机构编码" prop="orgCode">
        <el-input v-model="data.orgCode"></el-input>
      </el-form-item>
      <el-form-item label="机构全称" prop="orgFName">
        <el-input v-model="data.orgFName"></el-input>
      </el-form-item>
      <el-form-item label="机构英文名" prop="orgEName">
        <el-input v-model="data.orgEName"></el-input>
      </el-form-item>
      <el-form-item label="所属机构名" prop="pOrgName">
        <el-input v-model="data.pOrgName"></el-input>
      </el-form-item>
      <el-form-item label="机构地址" prop="orgAddress">
        <el-input v-model="data.orgAddress"></el-input>
      </el-form-item>
      <el-form-item label="地区代码" prop="areaCode">
        <el-input v-model="data.areaCode"></el-input>
      </el-form-item>
      <el-form-item label="机构状态" prop="orgSts">
        <el-select v-model="data.orgSts" placeholder="请选择" style="width: 200px">
          <el-option v-for="info in status"
                     :key="info.val"
                     :label="info.label"
                     :value="info.val">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否可另存" prop="reportSaveRule">
        <el-select v-model="data.reportSaveRule" placeholder="请选择" style="width: 200px">
          <el-option v-for="info in yesOrNo"
                     :key="info.val"
                     :label="info.label"
                     :value="info.val">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否可打印" prop="reportPrintRule">
        <el-select v-model="data.reportPrintRule" placeholder="请选择" style="width: 200px">
          <el-option v-for="info in yesOrNo"
                     :key="info.val"
                     :label="info.label"
                     :value="info.val">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="查询时间段" prop="timeLimit">
        <el-input v-model="data.timeLimit"></el-input>
      </el-form-item>
      <el-form-item label="详细地址" prop="address">
        <el-input v-model="data.address"></el-input>
      </el-form-item>
      <el-form-item label="邮政编码" prop="zip">
        <el-input v-model="data.zip"></el-input>
      </el-form-item>
      <el-form-item label="联系人" prop="linkMan">
        <el-input v-model="data.linkMan"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" prop="linkMode">
        <el-input v-model="data.linkMode"></el-input>
      </el-form-item>
      <el-form-item label="其他联系方式" prop="otherLinkMode">
        <el-input v-model="data.otherLinkMode"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer button-align">
      <el-button type="primary" @click="cancelDialog">取消</el-button>
      <el-button type="primary" @click="sendData('editForm')" :loading="loading">提交</el-button>
    </div>
  </div>
</template>

<script>
/**
 * 父页面在导入该模块后使用的方法，向本页面传值，本页面向父页面传值，调用父页面的函数
 * 传递属性使用         冒号  [:]
 * 传递函数引用使用     艾特  [@]
 *
 * <org-edit :data="currentVo" :loading="loading.edit" :checkRules="formRules"
 *                                    @op="edit" @cancelOp="cancelShowWindow"></org-edit>
 * :data        对应props中的data 引用父页面的currentVo
 * :loading     对应props中的loading 引用父页面的loading.edit
 * :checkRules  对应props中的checkRules 引用父页面的formRules
 * @op          对应父页面中的edit方法，调用父页面的方法时需要this.$emit('op', 传递的参数)
 * @cancelOp    对应父页面中的cancelShowWindow方法，调用父页面方法时需要this.$emit('cancelOp', 传递的参数)
 */
export default {
  data: function () {
    return {
      yesOrNo: [{label: '是', val: '0'}, {label: '否', val: '1'}],
      status: [{label: '正常', val: '1'}, {label: '停用', val: '2'}, {label: '注销', val: '3'}]
    }
  },
  // 父页面传递过来的参数信息
  props: ['data', 'loading', 'checkRules'],
  methods: {
    // 将数据传回父页面
    sendData: function () {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.$emit('op', this.data)
          })
        } else {
          return false
        }
      })
    },
    // 关闭窗口，回调父页面方法
    cancelDialog: function () {
      this.$emit('cancelOp', 'edit')
    }
  },
  mounted () {
  }
}
</script>
