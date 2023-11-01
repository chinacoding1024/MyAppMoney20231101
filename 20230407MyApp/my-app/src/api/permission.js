import http from "@/utils/request";
const url = '/admin/sys/permission';
//请求首页的数据
export const getData = () => {
    return http.get('/admin/sys/permission/getAllMenuList');
}

//更新菜单数据
export const updateMenuSubmit = (permission) => {
    return http({
        url: "/admin/sys/permission/update",
        method: "put",
        data: permission
    })
}
//更新菜单数据
export const deleteSubmit = (id) => {
    return http({
        url: `${url}/delete/${id}`,
        method: 'delete'

    })
}


//获取全部权限菜单
export const ListAllPermissions = () => {
    return http({
        url: `${url}` + '/ListAllPermissions',
        method: 'get'
    })
}
export const saveMenu = (menu) => {
  
    return http({
        url: `${url}` + '/save',
        method: 'post',
        data: menu
    })
}
export const update = (menu) => {
 
    return http({
        url: `${url}'/update`,
        method: 'put',
        data: menu
    })
}
export const removeChildById = (id) => {
 
    return http({
        url: `${url}/delete/${id}`,
        method: 'delete'
    })
}
//根据角色获取菜单
export const toAssign = (roleId) => {
    return http({
        url: `${url}/toAssign/${roleId}`,
        method: 'get'
    })
}
//给角色分配菜单权限
export const doAssign = (roleId, permissionId) => {
    return http({
        url: `${url}/doAssign`,
        method: 'post',
        params: { roleId, permissionId }
    })
}