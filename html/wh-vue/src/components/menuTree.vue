<template>
  <div>
    <div class="layui-header">
      <div class="layui-logo">外汇后台管理系统</div>
      <ul class="layui-nav layui-layout-left">
        <li class="layui-nav-item" v-for="item in menus" :key="item.id" :id="item.id">
          <a href="#" @click="jumpTo(item.id)">{{item.name}}</a>
        </li>
      </ul>
      <ul class="layui-nav layui-layout-right">
        <li class="layui-nav-item">
          <a href="javascript:;">
            <img src="" class="layui-nav-img">
            用户
          </a>
          <dl class="layui-nav-child">
            <dd><a href="">基本信息</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item"><a href="">退出</a></li>
      </ul>
    </div>
   <div class="layui-side layui-bg-black">
      <div class="layui-side-scroll">
        <ul class="layui-nav layui-nav-tree" id="nav" lay-filter="test">
          <li class="layui-nav-item" :index="index" v-for="(secondMenu,index) in currentSecondMenu" :key="secondMenu.id" >
            <a href="javascript:;">{{secondMenu.name}}</a>
            <dl class="layui-nav-child">
                <router-link tag="dd" exact-active-class="layui-this" exact v-for="thirdMenu in secondMenu.submenu" :key="thirdMenu.id" :to="thirdMenu.url">
                  <a href="#">
                    {{thirdMenu.name}}
                  </a>
                </router-link>
            </dl>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
export default {
  name: 'menuTree',
  data () {
    return {
      menus: this.$store.state.menuInfo,
      currentSecondMenu:[]
    }
  },
  methods:{
    jumpTo (id) {
      $(".layui-nav-item").removeClass('layui-this')
      $("#"+id).addClass('layui-this')
      for (let i = 0; i < this.menus.length; i++){
        let data = this.menus[i];
        if(id == data.id){
          $("#nav").find('span.layui-nav-bar').remove();
          this.currentSecondMenu = data.submenu;
          setTimeout(function(){//暂停等待渲染页面
            $('[index="0"]').addClass("layui-nav-itemed")
            layui.use('element', function () {
              let element = layui.element;
              element.render('nav', 'test');
            })
          },100);
          console.log(this.currentSecondMenu[0].submenu[0].url)
          this.$router.replace({'path': this.currentSecondMenu[0].submenu[0].url})
          break;
        }
      }

    }
  },
  created() {
    this.currentSecondMenu = this.menus[0].submenu;
  },
  mounted() {
    let that = this;
    layui.use('element', function () {
      $('[index="0"]').addClass("layui-nav-itemed")
      that.$router.replace({'path': that.currentSecondMenu[0].submenu[0].url})
      layui.element.render('nav','test')
    });
  }
}
</script>

<style scoped>

</style>
