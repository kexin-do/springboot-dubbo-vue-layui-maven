import Vue from 'vue'
import Vuex from 'vuex'
import actions from './actions'
import getters from './getters'

Vue.use(Vuex)

const state = {
  menuInfo: JSON.parse(sessionStorage.getItem('menuInfo')) || []
}

const mutations = {
  setMenuInfo (state, menu) {
    sessionStorage.setItem('menuInfo', JSON.stringify(menu))
    state.menuInfo = menu
  }
}

const store = new Vuex.Store({
  // 定义状态
  state: state,
  mutations: mutations,
  getters: getters,
  actions: actions
})

export default store
