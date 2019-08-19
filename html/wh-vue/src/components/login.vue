<template>
  <div>
    <div>
      <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>登录</legend>
      </fieldset>
      <form class="layui-form" action="" lay-filter="loginFrom">
        <div class="layui-form-item">
          <label class="layui-form-label">用户名</label>
          <div class="layui-input-inline">
            <input type="text" name="username" v-model="loginInfo.username" lay-verify="required" placeholder="请输入"
                   autocomplete="off" class="layui-input">
          </div>
        </div>
        <div class="layui-form-item">
          <label class="layui-form-label">密码</label>
          <div class="layui-input-inline">
            <input type="password" v-model="loginInfo.password" placeholder="请输入密码" autocomplete="off"
                   class="layui-input">
          </div>
          <!--<div class="layui-form-mid layui-word-aux">请务必填写用户名</div>-->
        </div>
        <div class="layui-form-item">
          <button class="layui-btn" v-on:click="login">登录</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
export default {
  name: 'login',
  data() {
    return {
      loginInfo: {username: '', password: ''},
      responseResult: []
    }
  },
  methods: {
    login () {
      this.$axios({
        method: 'post',
        url: '/csmsp/login',
        data: this.$qs.stringify({
          'userCode': this.loginInfo.username,
          'passWord': this.loginInfo.password
        })
      })
        .then(successResponse => {
          this.responseResult = JSON.stringify(successResponse.data)
          if (successResponse.data.statusCode === '211') {
            this.$store.dispatch('setMenuInfo', JSON.parse(successResponse.data.data.menu))
            this.$router.replace({path: '/index'})
          }
        })
        .catch(failResponse => {
        })
    }
  }
}
</script>
