import Vue from 'vue'
import Vuex from 'vuex'
import menu from './modules/menu'
import user from './modules/user'
import getters from './getters'
Vue.use(Vuex)

//创建vuex的实例
const store = new Vuex.Store({
    modules: {
        menu,
        user,
    },
    getters
})
export default store