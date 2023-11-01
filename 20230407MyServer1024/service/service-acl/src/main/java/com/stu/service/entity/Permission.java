package com.stu.service.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 权限
 * </p>
 *
 * @author 公众号 小明的学习圈子
 * @since 2023-10-26
 */
@Getter
@Setter
@TableName("acl_permission")
@ApiModel(value = "Permission对象", description = "权限")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    private String id;

    @ApiModelProperty("所属上级")
    private String pid;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("类型(1:菜单,2:按钮)")
    private Integer type;

    @ApiModelProperty("权限值")
    private String permissionValue;

    @ApiModelProperty("访问路径")
    private String path;

    @ApiModelProperty("组件路径")
    private String component;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("状态(0:禁止,1:正常)")
    private Integer status;

    @ApiModelProperty("逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer isDeleted;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    //递归遍历菜单用到的3个属性
    @ApiModelProperty(value = "层级")
    @TableField(exist = false)
    private Integer level;

    @ApiModelProperty(value = "下级，子集")
    @TableField(exist = false)
    private List<Permission> children;

    @ApiModelProperty(value = "是否选中")
    @TableField(exist = false)
    private boolean hasSelect;
}
