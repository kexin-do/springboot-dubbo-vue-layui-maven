<template>
  <el-form :model="loginInfo"  ref="loginInfo" label-position="left" label-width="0px" class="login-container">
    <h3 class="title">系统登录</h3>
    <el-form-item prop="username">
      <el-input type="text" v-model="loginInfo.username" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="loginInfo.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item style="height: 20px;"></el-form-item>
    <el-form-item>
      <el-button type="primary" style="width:100%;" @click.native.prevent="login" :loading="logining">登录</el-button>
      <!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
    </el-form-item>
  </el-form>
</template>

<script>
/* eslint-disable */
export default {
  name: 'login',
  data() {
    return {
      logining: false,
      checked: false,
      loginInfo: {username: '', password: ''},
      responseResult: []
    }
  },
  methods: {
    login () {
      this.logining = true;
      this.$axios({
        method: 'post',
        url: '/csmsp/login',
        data: {
          'userCode': this.loginInfo.username,
          'passWord': this.loginInfo.password
        }
      })
        .then(successResponse => {
          this.logining = false;
          this.responseResult = JSON.stringify(successResponse.data)
          if (successResponse.data.statusCode === '211') {
            this.$store.dispatch('setMenuInfo', JSON.parse(successResponse.data.data.menu))
            this.$router.replace({path: '/index'})
          }else {
            this.$message({
              message: successResponse.data.statusInfo,
              type: 'error',
              showClose: true,
              offset: '120'
            })
          }
        })
        .catch(failResponse => {
        })
    }
  }
}
</script>

<style scoped lang="scss">
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0 0 35px 0;
    }
  }
</style>
