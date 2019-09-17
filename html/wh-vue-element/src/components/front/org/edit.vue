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
export default {
  data: function () {
    return {
      yesOrNo: [{label: '是', val: '0'}, {label: '否', val: '1'}],
      status: [{label: '正常', val: '1'}, {label: '停用', val: '2'}, {label: '注销', val: '3'}]
    }
  },
  props: ['data', 'loading', 'checkRules'],
  methods: {
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
    cancelDialog: function () {
      this.$emit('cancelOp', 'edit')
    }
  },
  mounted () {
  }
}
</script>
