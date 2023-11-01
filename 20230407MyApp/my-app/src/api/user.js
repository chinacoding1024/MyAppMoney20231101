import http from "@/utils/request";
const apiName = '/admin/sys/user';
//请求首页的数据
export const getData = () => {
    return http.get('/home/getData');
}

export const pageList = (page, limit, user) => {
    return http({
        url: `${apiName}/pageList/${page}/${limit}`,
        method: 'get',
        params: user
    })
}
export const removeById = (id) => {
    return http({
        url: `${apiName}/deleteUser/${id}`,
        method: 'delete'
    })
}
export const save = (user) => {
    return http({
        url: `${apiName}/addUser`,
        method: 'post',
        data: user
    })
}
//用户详情
export const getDetail = (id) => {
    return http({
        url: `${apiName}/get/${id}`,
        method: 'get'
    })
}
export const update = (user) => {
    return http({
        url: `${apiName}/updateUser`,
        method: 'post',
        data: user
    })
}

//取得用户的集合
export const listAllUsers = () => {
    return http({
        url: `${apiName}/findAll`,
        method: 'get'
    })
}
//根据角色获取菜单
export const toAssign = (userId) => {
    return http({
        url: `${apiName}/toAssign/${userId}`,
        method: 'get'
    })
}
//给角色分配菜单权限
export const doAssign = (userId, permissionIds) => {
    return http({
        url: `${apiName}/doAssign`,
        method: 'post',
        params: { userId, permissionIds }
    })
}










