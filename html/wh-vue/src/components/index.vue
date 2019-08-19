<template>
  <div class="layui-layout-body">
    <div class="layui-layout layui-layout-admin" >
        <!-- 头部区域（可配合layui已有的水平导航） -->
      <menu-tree></menu-tree>
      <div class="layui-body">
        <div style="padding: 15px;">
          <router-view/>
        </div>
      </div>

      <div class="layui-footer">
        <!-- 底部固定区域 -->
        © wh
      </div>
    </div>
  </div>
</template>

<script>
import MenuTree from './menuTree'
export default {
  name: 'index',
  components: {MenuTree},
  created () {
    if (sessionStorage.getItem('store')) {
      this.$store.replaceState(Object.assign({}, this.$store.state, JSON.parse(sessionStorage.getItem('store'))))
    }

    window.addEventListener('beforeunload', () => {
      sessionStorage.setItem('store', JSON.stringify(this.$store.state))
    })
  }
}
</script>

<style scoped>

</style>
