<template>
  <el-row class="container">
    <el-col :span="24" class="header">
      <el-col :span="10" class="logo" :class="collapsed?'logo-collapse-width':'logo-width'">
        {{collapsed?'外汇':sysName}}
      </el-col>
      <el-col :span="1">
        <div class="tools" @click.prevent="collapse">
          <i class="el-icon-s-fold"></i>
        </div>
      </el-col>
      <el-col :span="17">
        <el-menu
          :default-active="'0'"
          mode="horizontal"
          class="topMenu"
          text-color="#fff"
          active-text-color="#9DD3FA"
          background-color="#1F6FB5"
          @select="handleSelectTop">
          <el-menu-item v-for="(topMenu, index) in menus" :index="index+''" :key="topMenu.id">
            {{topMenu.name}}
          </el-menu-item>
        </el-menu>
      </el-col>
      <el-col :span="1" class="userinfo">
        <el-dropdown trigger="hover">
          <span class="el-dropdown-link userinfo-inner"><img :src="this.sysUserAvatar"/> {{sysUserName}}</span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>我的消息</el-dropdown-item>
            <el-dropdown-item>设置</el-dropdown-item>
            <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
    </el-col>
    <el-col :span="24" class="main">
      <aside :class="collapsed?'menu-collapsed':'menu-expanded'">
        <!--导航菜单-->
        <el-menu :default-active="$route.path"
                 background-color="#525558"
                 text-color="#fff"
                 active-text-color="#2494F2"
                 collapse-transition
                 unique-opened router v-show="!collapsed">
          <div v-for="(item,index) in currentSecondMenu" :key="item.id" v-on:click="secondIndex=index;">
            <el-submenu :index="index+''" style="border-bottom: 1px solid #737373">
              <template  v-on:click="thirdIndex=0;" slot="title"><i class="el-icon-plus"></i>{{item.name}}</template>
              <el-menu-item  v-for="(child, third) in item.submenu" v-on:click="thirdIndex=third" :index="child.url" :key="child.path">
                {{child.name}}
              </el-menu-item>
            </el-submenu>
          </div>
        </el-menu>
        <!--导航菜单-折叠后-->
        <ul class="el-menu collapsed" v-show="collapsed" ref="menuCollapsed">
          <li v-for="(item,index) in currentSecondMenu" :key="item.id" class="el-submenu item">
            <div class="el-submenu__title" style="padding-left: 20px;" @mouseover="showMenu(index,true)"
                 @mouseout="showMenu(index,false)"><i :class="'el-icon-plus'"></i></div>
            <ul class="el-menu submenu" :class="'submenu-hook-'+index" @mouseover="showMenu(index,true)"
                @mouseout="showMenu(index,false)">
              <li v-for="(child, childIndex) in item.submenu" :key="child.id"
                  style="padding-left: 40px;" :class="$route.path===child.url?'el-menu-item is-active':'el-menu-item in-show'"
                  @click="menuJump(child.url, index, childIndex)">{{child.name}}
              </li>
            </ul>
          </li>
        </ul>
      </aside>
      <section class="content-container">
        <div class="grid-content bg-purple-light">
          <el-col :span="24" class="breadcrumb-container">
            <strong class="title">{{menus[topIndex].submenu[secondIndex].submenu[thirdIndex].name}}</strong>
            <el-breadcrumb separator="/" class="breadcrumb-inner">
              <el-breadcrumb-item v-for="item in [menus[topIndex].name,menus[topIndex].submenu[secondIndex].name,menus[topIndex].submenu[secondIndex].submenu[thirdIndex].name]" :key="item.path">
                {{ item }}
              </el-breadcrumb-item>
            </el-breadcrumb>
          </el-col>
          <el-col :span="24" class="content-wrapper">
            <transition name="fade" mode="out-in">
              <router-view></router-view>
            </transition>
          </el-col>
        </div>
      </section>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: 'index',
  data () {
    return {
      sysName: '外汇后台管理系统',
      collapsed: false,
      sysUserName: '',
      sysUserAvatar: '../assets/logo.png',
      menus: this.$store.state.menuInfo,
      topIndex: 0,
      secondIndex: 0,
      thirdIndex: 0,
      currentSecondMenu: []
    }
  },
  methods: {
    handleSelectTop (index, indexPath) {
      this.topIndex = index
      this.secondIndex = 0
      this.thirdIndex = 0
      this.currentSecondMenu = this.menus[this.topIndex].submenu
      this.$router.push(this.currentSecondMenu[this.secondIndex].submenu[this.thirdIndex].url)
    },
    collapse () {
      this.collapsed = !this.collapsed
    },
    showMenu (i, status) {
      this.$refs.menuCollapsed.getElementsByClassName('submenu-hook-' + i)[0].style.display = status ? 'block' : 'none'
    },
    menuJump (url, index, thridIndex) {
      this.secondIndex = index
      this.thirdIndex = thridIndex
      this.$router.push(url)
    }
  },
  created () {
    this.topIndex = 0
    this.secondIndex = 0
    this.thirdIndex = 0
    this.currentSecondMenu = this.menus[this.topIndex].submenu
    this.$router.push(this.currentSecondMenu[this.secondIndex].submenu[this.thirdIndex].url)
  },
  mounted () {
  }
}
</script>

<style scoped lang="scss">

  .container {
    position: absolute;
    top: 0px;
    bottom: 0px;
    width: 100%;
    .header {
      height: 60px;
      line-height: 60px;
      background: $color-primary;
      color: #fff;
      .topMenu{
        background: $color-primary;
      }
      .userinfo {
        text-align: right;
        padding-right: 35px;
        float: right;
        .userinfo-inner {
          cursor: pointer;
          color: #fff;
          img {
            width: 40px;
            height: 40px;
            border-radius: 20px;
            margin: 10px 0 10px 10px;
            float: right;
          }
        }
      }
      .logo {
        //width:230px;
        height: 60px;
        font-size: 22px;
        padding-left: 20px;
        padding-right: 20px;
        border-color: rgba(238, 241, 146, 0.3);
        border-right-width: 1px;
        border-right-style: solid;
        img {
          width: 40px;
          float: left;
          margin: 10px 10px 10px 18px;
        }
        .txt {
          color: #fff;
        }
      }
      .logo-width {
        width: 230px;
      }
      .logo-collapse-width {
        width: 60px
      }
      .tools {
        padding: 0px 23px;
        width: 14px;
        height: 60px;
        line-height: 60px;
        cursor: pointer;
      }
    }
    .main {
      display: flex;
      // background: #324057;
      position: absolute;
      top: 60px;
      bottom: 0px;
      overflow: hidden;
      aside {
        flex: 0 0 230px;
        width: 230px;
        // position: absolute;
        // top: 0px;
        // bottom: 0px;
        .el-menu {
          width: 230px;
          height: 100%;
        }
        .collapsed {
          width: 60px;
          background-color: rgb(84, 92, 100);
          .item {
            position: relative;
            ul {
              background-color: #283948;
              border: 5px solid #FFF;
              color: #FFF;
              .is-active {
                color: rgb(36, 148, 242);
              }
              .in-show {
                color: #FFF;
              }
              .in-show:hover {
                background-color: #a6a3aa !important;
              }
            }
          }
          .submenu {
            position: absolute;
            top: 0;
            left: 60px;
            z-index: 99999;
            height: auto;
            display: none;
          }

        }
      }
      .menu-collapsed {
        flex: 0 0 60px;
        width: 60px;
      }
      .menu-expanded {
        flex: 0 0 230px;
        width: 230px;
      }
      .content-container {
        // background: #545c64;
        flex: 1;
        // position: absolute;
        // right: 0px;
        // top: 0px;
        // bottom: 0px;
        // left: 230px;
        overflow-y: scroll;
        padding: 20px;
        .breadcrumb-container {
          //margin-bottom: 15px;
          .title {
            width: 200px;
            float: left;
            color: #475669;
          }
          .breadcrumb-inner {
            float: right;
          }
        }
        .content-wrapper {
          background-color: #fff;
          box-sizing: border-box;
        }
      }
    }
  }
</style>
