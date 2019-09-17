<template>
  <div>
    <el-form :model="data" label-position="right" :status-icon="true" :inline="true" size="small" label-width="100px">
      <el-form-item label="用户名称" prop="userName">
        <el-input v-model="data.userName" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="用户代码" prop="userNo">
        <el-input v-model="data.userNo"></el-input>
      </el-form-item>
      <el-form-item label="身份证号" prop="idNo">
        <el-input v-model="data.idNo"></el-input>
      </el-form-item>
      <el-form-item label="用户密码" prop="userPwd">
        <el-input v-model="data.userPwd"></el-input>
      </el-form-item>
      <el-form-item label="绑定地址" prop="bindAddressNum">
        <el-input v-model="data.bindAddressNum"></el-input>
      </el-form-item>
      <el-form-item label="机构编号" prop="orgCode">
        <el-input v-model="data.orgCode"></el-input>
      </el-form-item>
      <el-form-item label="机构名称" prop="orgName">
        <el-input v-model="data.orgName"></el-input>
      </el-form-item>
      <el-form-item label="所属机构" prop="pOrgName">
        <el-input v-model="data.pOrgName"></el-input>
      </el-form-item>
      <el-form-item label="用户状态" prop="orgSts">
        <el-select v-model="data.userSts" placeholder="请选择" style="width: 200px">
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
      <el-form-item label="用户级别" prop="userLevel">
        <el-select v-model="data.userLevel" placeholder="请选择" style="width: 200px">
          <el-option v-for="info in userLevel"
                     :key="info.val"
                     :label="info.label"
                     :value="info.val">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="启停变更日期" prop="lstTime">
        <el-input v-model="data.startTime"></el-input>
      </el-form-item>
      <el-form-item label="最后变更人" prop="lstUser">
        <el-input v-model="data.startUser"></el-input>
      </el-form-item>
      <el-form-item label="最后变更日期" prop="lstTime">
        <el-input v-model="data.lstTime"></el-input>
      </el-form-item>
      <el-form-item label="最后变更人" prop="lstUser">
        <el-input v-model="data.lstUser"></el-input>
      </el-form-item>
      <el-form-item label="创建人" prop="createUser">
        <el-input v-model="data.createUser"></el-input>
      </el-form-item>
      <el-form-item label="创建人" prop="createTime">
        <el-input v-model="data.createTime"></el-input>
      </el-form-item>
      <el-form-item label="最后登录时间" prop="lstLoginDate">
        <el-input v-model="data.lstLoginDate"></el-input>
      </el-form-item>
      <el-form-item label="密码变更时间" prop="userPwdDay">
        <el-input v-model="data.userPwdDay"></el-input>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer button-align">
      <el-button type="primary" @click="cancelDialog">关闭</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      yesOrNo: [{label: '是', val: '0'}, {label: '否', val: '1'}],
      status: [{label: '正常', val: '1'}, {label: '停用', val: '2'}],
      userLevel: [{label: '超级管理员', val: 'S'}, {label: '管理员', val: 'A'}, {label: '业务员', val: 'B'}]
    }
  },
  props: ['data'],
  methods: {
    cancelDialog: function () {
      this.$emit('cancelOp', 'detail')
    }
  },
  mounted () {
  }
}
</script>
