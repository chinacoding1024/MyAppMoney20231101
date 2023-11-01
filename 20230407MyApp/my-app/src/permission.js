import router from './router'
import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import http from "@/utils/request";
import qs from "qs";
import store from './store'

const whiteList = ['/login', '/auth-redirect'] // no redirect whitelist
router.beforeEach(async (to, from, next) => {


  const hasToken = getToken()
  debugger
  if (hasToken) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next()

    } else {
      // determine whether the user has obtained his permission roles through getInfo
      const hasRoles = store.getters.roles && store.getters.roles.length > 0
      if (hasRoles) {
        next()
      } else {
        // get user info
        // note: roles must be a object array! such as: ['admin'] or ,['developer','editor']
        const response = await store.dispatch('GetInfo')
        if (response.code === 20000) {
          const data = response.data
          if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            store.commit('SET_ROLES', data.roles)
          }

          const buttonAuthList = []
          const menuData = data.permissionList
          store.commit('setMenuList', menuData)

          //权限用户
          localStorage.setItem("menus", JSON.stringify(menuData))
          data.permissionValueList.forEach(button => {
            if (button) {
              buttonAuthList.push(button)
            }

          })

          store.commit('SET_NAME', data.name)
          //commit('SET_AVATAR', data.avatar)
          store.commit('SET_BUTTONS', buttonAuthList)


        }
        next()
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next({ path: '/' })
    }
  }

})

