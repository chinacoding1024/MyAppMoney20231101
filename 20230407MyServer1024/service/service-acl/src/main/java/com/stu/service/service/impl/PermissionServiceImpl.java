package com.stu.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stu.service.entity.Permission;
import com.stu.service.entity.RolePermission;
import com.stu.service.entity.User;
import com.stu.service.mapper.PermissionMapper;
import com.stu.service.service.IPermissionService;
import com.stu.service.service.IRolePermissionService;
import com.stu.service.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author 程序员小明1024
 * @since 2023-08-07
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Autowired
    private IUserService userService;

    /***********************************
     * 用途说明:获取全部菜单
     * 返回值说明:
     * @return List<Permission>
     ***********************************/
    @Override
    public List<Permission> listPermissions() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        //把查询的所有菜单用递归的方式生成结构化菜单
        return buildPermissions(baseMapper.selectList(queryWrapper));
    }

    /***********************************
     * 用途说明:获取全部菜单
     * 返回值说明:
     * @return List<Permission>
     ***********************************/
    private List<Permission> buildPermissions(List<Permission> list) {
        //创建rootList集合，最终数据封装
        List<Permission> rootList = new ArrayList<>();

        //遍历所有菜单，得到顶层菜单 pid==0，设置leve =1
        for (Permission permission : list) {
            //获取顶层菜单，pid=0
            if ("0".equals(permission.getPid())) {
                //设置level = 1
                permission.setLevel(1);
                //根据顶层菜单，添加子菜单，封装到rootList
                rootList.add(selectChildrenMenu(permission, list));

            }
        }

        return rootList;
    }

    /***********************************
     * 用途说明:递归查询下级菜单
     * @param root
     * @param list
     * 返回值说明:
     * @return com.stu.service.entity.Permission
     ***********************************/
    private Permission selectChildrenMenu(Permission root, List<Permission> list) {
        //因为要往下级菜单房新的菜单，需要初始化
        root.setChildren(new ArrayList<Permission>());
        //遍历所有菜单，比较当前对象的id和遍历的子菜单的pid是否相同
        for (Permission permission : list) {
            if (root.getId().equals(permission.getPid())) {
                permission.setLevel(root.getLevel() + 1);
                //递归，查询出来的子菜单放到父菜单里
                root.getChildren().add(selectChildrenMenu(permission, list));
            }
        }
        return root;
    }

    /***********************************
     * 用途说明:查询所有权限菜单
     * 返回值说明:
     * @return java.util.List<com.stu.service.acl.entity.Permission>
     ***********************************/
    @Override
    public List<Permission> ListAllPermissions() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return bulidPermission(baseMapper.selectList(queryWrapper));
    }

    /***********************************
     * 用途说明:把返回所有菜单list集合进行封装的方法
     * @param list
     * 返回值说明:
     * @return java.util.List<com.stu.service.acl.entity.Permission>
     ***********************************/
    private List<Permission> bulidPermission(List<Permission> list) {
        //创建list集合，用于数据最终封装
        List<Permission> finalNode = new ArrayList<>();

        //把所有菜单list集合遍历，得到顶层菜单 pid=0菜单，设置level是1
        for (Permission permission : list) {
            //得到顶层菜单 pid=0菜单
            if ("0".equals(permission.getPid())) {
                permission.setLevel(1);
                //根据顶层菜单，向里面进行查询子菜单，封装到finalNode里面
                finalNode.add(selectChildren(permission, list));
            }
        }

        return finalNode;
    }

    /***********************************
     * 用途说明:递归查询下级菜单
     * @param permission
     * @param list
     * 返回值说明:
     * @return com.stu.service.acl.entity.Permission
     ***********************************/
    private Permission selectChildren(Permission permission, List<Permission> list) {
        //1 因为向一层菜单里面放二层菜单，二层里面还要放三层，把对象初始化
        permission.setChildren(new ArrayList<Permission>());
        //2 遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
        for (Permission child : list) {
            if (permission.getId().equals(child.getPid())) {
                child.setLevel(permission.getLevel() + 1);
                if (child.getChildren() == null) {
                    child.setChildren(new ArrayList<>());
                }
//                permission.getChildren().add(child);
//                selectChildren(child,list);
                permission.getChildren().add(selectChildren(child, list));
            }
        }
        return permission;

    }

    /***********************************
     * 用途说明:递归删除菜单
     * @param id
     * 返回值说明:
     * @return boolean
     ***********************************/
    @Override
    public boolean removeChildById(String id) {
        List<String> idList = new ArrayList<>();
        selectChildListById(id, idList);
        idList.add(id);
        return baseMapper.deleteBatchIds(idList) > 0;
    }

    /***********************************
     * 用途说明:根據角色獲取菜單
     * @param id
     * 返回值说明:
     * @return java.util.List<com.stu.service.acl.entity.Permission>
     ***********************************/
    @Override
    public List<Permission> listAllMenu(String id) {
        //获取所有菜单
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<>());
        //根据角色id呼气角色权限列表
        List<RolePermission> rolePermissionsList = rolePermissionService
                .list(new QueryWrapper<RolePermission>().eq("role_id", id));
        //遍历所有菜单，获取每一项，看是否在权限列表，如果在，就标记
        List<String> permissionIdList = rolePermissionsList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
        allPermissionList.forEach(permission -> {
            if (permissionIdList.contains(permission.getId())) {
                permission.setHasSelect(true);
            } else {
                permission.setHasSelect(false);
            }
        });
        /*for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if(rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }*/
        return bulidPermission(allPermissionList);
    }

    /***********************************
     * 用途说明:给角色分配菜单权限
     * @param roleId
     * @param permissionId
     * 返回值说明:
     * @return boolean
     ***********************************/
    @Override
    public boolean saveRolePermissionrelationShip(String roleId, String[] permissionId) {

        //删除旧的权限
        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", roleId));
        if (null != permissionId && permissionId.length > 0) {
            List<RolePermission> list = new ArrayList<>();
            for (String id : permissionId) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(id);
                list.add(rolePermission);
            }

            return rolePermissionService.saveBatch(list);
        }
        return true;
    }

    /***********************************
     * 用途说明:根据用户id查询有权限的菜单
     * @param id
     * 返回值说明:
     * @return java.util.List<java.lang.String>
     ***********************************/
    @Override
    public List<String> selectPermissionValueListByUserId(String id) {
        List<String> list;
        if (checkAdmin(id)) {
            //如果是超级管理员获取所有权限
            list = baseMapper.selectAllPermissionValue();
        } else {
            //根据用户id查询所有权限
            list = baseMapper.selectPermissionValueByUserId(id);
        }
        return list;
    }

    /***********************************
     * 用途说明:根据用户id查询所有权限的菜单详细列表
     * @param userId
     * 返回值说明:
     * @return java.util.List<org.json.JSONObject>
     ***********************************/
    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if (checkAdmin(userId)) {
            //如果是超级管理员获取所有权限
            selectPermissionList = baseMapper.selectList(null);
        } else {
            //根据用户id查询所有权限
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }
        //先转换成树状
        List<Permission> permissionList = bulidPermission(selectPermissionList);
        //然后转化成前端需要的格式
        List<JSONObject> result = bulidJson(permissionList);

        return result;
    }

    /***********************************
     * 用途说明:转化成前端需要的格式
     * @param permissionList
     * 返回值说明:
     * @return java.util.List<org.json.JSONObject>
     ***********************************/
    private List<JSONObject> bulidJson(List<Permission> permissionList) {
        List<JSONObject> menus = new ArrayList<>();
        if (permissionList.size() == 1) {
            Permission topNode = permissionList.get(0);
            //组建左侧一级菜单
            List<Permission> oneMenuList = topNode.getChildren();
            for (Permission one : oneMenuList) {
                JSONObject oneMenu = new JSONObject();
                oneMenu.put("path", one.getPath());
                oneMenu.put("component", one.getComponent());
                oneMenu.put("redirect", "noredirect");//第一级不需要重定向
                oneMenu.put("name", "name_" + one.getId());
                oneMenu.put("title", one.getName());
                oneMenu.put("label", one.getName());
                oneMenu.put("icon", one.getIcon());
                oneMenu.put("hidden", false);//一级不需要因此，3级需要
                JSONObject oneMeta = new JSONObject();
                oneMeta.put("title", one.getName());
                oneMeta.put("icon", one.getIcon());
                oneMenu.put("meta", oneMeta);

                List<JSONObject> children = new ArrayList<>();
                List<Permission> twoMenuList = one.getChildren();//二级菜单
                for (Permission two : twoMenuList) {
                    JSONObject twoMenu = new JSONObject();
                    twoMenu.put("path", two.getPath());
                    twoMenu.put("component", two.getComponent());
                    twoMenu.put("title", two.getName());
                    twoMenu.put("label", two.getName());
                    twoMenu.put("icon", two.getIcon());
//                    twoMenu.put("redirect", "noredirect");//第一级不需要重定向
                    twoMenu.put("name", "name_" + two.getId());
                    twoMenu.put("hidden", false);//一级不需要因此，3级需要
                    JSONObject twoMeta = new JSONObject();
                    twoMeta.put("title", two.getName());
                    twoMeta.put("icon", two.getIcon());
                    twoMenu.put("meta", twoMeta);
                    children.add(twoMenu);

                    //功能按钮
                    List<Permission> threeMenuList = two.getChildren();
                    for (Permission three : threeMenuList) {
                        if (StringUtils.isEmpty(three.getPath())) {
                            continue;
                        }
                        JSONObject threeMenu = new JSONObject();
                        threeMenu.put("path", three.getPath());
                        threeMenu.put("component", three.getComponent());
//                        threeMenu.put("redirect", "noredirect");//第一级不需要重定向
                        threeMenu.put("name", "name_" + three.getId());
                        threeMenu.put("title", three.getName());
                        threeMenu.put("label", three.getName());
                        threeMenu.put("icon", three.getIcon());
                        threeMenu.put("hidden", true);//一级不需要因此，3级需要
                        JSONObject threeMeta = new JSONObject();
                        threeMeta.put("title", three.getName());
                        threeMeta.put("icon", three.getIcon());
                        threeMenu.put("meta", threeMeta);

                        children.add(threeMenu);
                    }

                }
                oneMenu.put("children", children);
                menus.add(oneMenu);

            }
        }
        return menus;
    }

    /***********************************
     * 用途说明:判断是否管理员
     * @param id
     * 返回值说明:
     * @return boolean
     ***********************************/
    private boolean checkAdmin(String id) {
        User user = userService.getById(id);
        if (user != null && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    /***********************************
     * 用途说明:根据当前菜单id查询他的子子孙孙id，封装到list集合
     * @param id
     * @param idList
     * 返回值说明:
     ***********************************/
    private void selectChildListById(String id, List<String> idList) {
        //查询当前菜单的下级
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", id);
        queryWrapper.select("id");
        List<Permission> childList = baseMapper.selectList(queryWrapper);
        //把childIdList里面菜单id值获取出来，封装idList里面，做递归查询
        childList.forEach(item -> {
            idList.add(item.getId());
            selectChildListById(item.getId(), idList);
        });

    }
}
