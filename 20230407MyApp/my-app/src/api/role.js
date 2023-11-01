import request from '@/utils/request'
const apiName = '/admin/sys/role';

export default {
    pageList(page, limit, Role) {
        return request({
            url: `${apiName}/pageList/${page}/${limit}`,
            method: 'get',
            params: Role
        })
    },
    removeById(id) {
        return request({
            url: `${apiName}/deleteRole/${id}`,
            method: 'delete'
        })
    },
    save(role) {
        return request({
            url: `${apiName}/addRole`,
            method: 'post',
            data: role
        })
    },
    //角色详情
    getDetail(id) {
        return request({
            url: `${apiName}/get/${id}`,
            method: 'get'
        })
    },
    update(role) {
        return request({
            url: `${apiName}/updateRole`,
            method: 'post',
            data: role
        })
    },

    //取得角色的集合
    listAllRoles() {
        return request({
            url: '${apiName}/findAll',
            method: 'get'
        })
    },
}