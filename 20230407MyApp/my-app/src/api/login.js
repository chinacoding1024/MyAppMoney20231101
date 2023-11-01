import request from '@/utils/request'

export function login11(username, password) {
  //   return request({
  //     url: '/admin/edu//user/login',
  //     method: 'post',
  //     data: {
  //       username,
  //       password
  //     }
  //   })
  // }

  // export function getInfo(token) {
  //   return request({
  //     url: '/admin/edu//user/getUserInfo',
  //     method: 'get',
  //     params: { token }
  //   })
  // }

  // export function logout() {
  //   return request({
  //     url: '/admin/edu//user/logout',
  //     method: 'post'
  //   })
  // }
  return request({
    url: '/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/getUserInfo',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/admin/sys/index/logout',
    method: 'post'
  })
}

export function getMenu() {
  return request({
    url: '/admin/sys/index/menu',
    method: 'get'
  })
}