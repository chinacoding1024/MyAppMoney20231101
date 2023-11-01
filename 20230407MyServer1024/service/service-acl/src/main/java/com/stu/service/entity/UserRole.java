package com.stu.service.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 公众号 小明的学习圈子
 * @since 2023-10-26
 */
@Getter
@Setter
  @TableName("acl_user_role")
@ApiModel(value = "UserRole对象", description = "")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("主键id")
        private String id;

      @ApiModelProperty("角色id")
      private String roleId;

      @ApiModelProperty("用户id")
      private String userId;

      @ApiModelProperty("逻辑删除 1（true）已删除， 0（false）未删除")
      private Integer isDeleted;

      @ApiModelProperty("创建时间")
        @TableField(fill = FieldFill.INSERT)
      private LocalDateTime gmtCreate;

      @ApiModelProperty("更新时间")
        @TableField(fill = FieldFill.INSERT_UPDATE)
      private LocalDateTime gmtModified;


}
