import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/views/Home.vue'
import User from '@/views/sys/user/User.vue'
import Main from '@/views/Main.vue'
import store from '@/store'
import { getToken } from '@/utils/auth'
import { login, logout, getInfo } from '@/api/login'


//模拟菜单数据
const authoritys = ['sys:user:list', "sys:user:save", "sys:user:delete"]
const menuData = [
  // {
  //   path: "/",
  //   name: "home",
  //   label: "首页",
  //   icon: "s-home",
  //   url: "Home/Home",
  // },
  {
    label: "权限管理",
    name: "system",
    icon: "location",
    children: [
      {
        path: "/user",
        name: "user",
        label: "用户管理",
        icon: "setting",
        component: "sys/user/User",
      },
      {
        path: '/sys/role/distribution/:id',
        name: '角色权限',
        component: 'sys/role/roleForm',
        meta: { title: '角色权限', icon: 'table' },
        hidden: true
      },
      {
        path: '/userForm/:id',
        name: '角色权限',
        component: 'sys/user/userForm',
        meta: { title: '角色权限', icon: 'table' },
        hidden: true
      },
      {
        path: "/role",
        name: "role",
        label: "角色管理",
        icon: "setting",
        component: "sys/role/Role",
      },
      {
        path: "/menu",
        name: "menu",
        label: "菜单管理",
        icon: "setting",
        component: "sys/menu/Menu",
      },
    ],
  },
]

// 拼装动态路由
const manageRoute = {
  path: '/',
  component: Main,
  redirect: '/login',
  children: [
    //子路由
    { path: 'home', name: "home", component: Home },//首页

  ]
}
//获取数据
//菜单
store.commit('setMenuList', menuData)

//权限用户
localStorage.setItem("menus", JSON.stringify(menuData))
// 注意：刷新页面会导致页面路由重置
const setRoutes = () => {

  const storeMenus = localStorage.getItem("menus");

  if (storeMenus) {

    // 获取当前的路由对象名称数组
    const currentRouteNames = router.getRoutes().map(v => v.name)
    if (!currentRouteNames.includes('home')) {

      const menus = JSON.parse(storeMenus)
      menus.forEach(item => {

        if (item.path) {  // 当且仅当path不为空的时候才去设置路由
          let flag = false;
          if (item.hidden) {

            flag = true;
          }
          let itemMenu = {
            path: item.path.replace("/", ""),
            name: item.name,
            hidden: flag,
            component: () => import('../views/' + item.component + '.vue')
          }

          manageRoute.children.push(itemMenu)
        } else if (item.children.length) {
          item.children.forEach(item => {
            let flag = false;
            if (item.hidden) {

              flag = true;
            }
            if (item.path) {
              let itemMenu = {
                path: item.path.replace("/", ""),
                name: item.name,
                hidden: flag,
                component: () => import('../views/' + item.component + '.vue')
              }

              manageRoute.children.push(itemMenu)
            }
          })
        }
      })
      // 动态添加到现在的路由对象中去
      router.addRoute(manageRoute)
    }

  }
}

// 重置我就再set一次路由
setRoutes()

//转成路由
// const menuToRoute = (item) => {

//   if (!item.component) {
//     return null
//   }
//   // let route = {
//   //   name: item.name,
//   //   path: item.path.replace("/", ""),

//   //   meta: {
//   //     icon: item.icon,
//   //     title: item.title,

//   //   }
//   // }
//   // route.component= () => import('../views/' + item.component + '.vue')
//   // route.component=()=>import('@/views/'+item.component+'.vue');
//   // route.component = () => import('@/views/' + item.component + '.vue')
//   let route = { path: item.path.replace("/", ""), name: item.name, component: () => import('../views/' + item.component + '.vue') }
//   return route
// }



router.beforeEach((to, from, next) => {

  let that = this

  getInfo(store.state.token).then(response => {

    const data = response.data
    if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
      store.commit('SET_ROLES', data.roles)
    }

    const buttonAuthList = []

    console.log(data)
    data.permissionValueList.forEach(button => {
      if (button) {
        buttonAuthList.push(button)
      }

    })

    store.commit('SET_NAME', data.name)
    //commit('SET_AVATAR', data.avatar)
    store.commit('SET_BUTTONS', buttonAuthList)

  }).catch(error => {

  })
  next()
})
