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
 * 用户表
 * </p>
 *
 * @author 公众号 小明的学习圈子
 * @since 2023-10-26
 */
@Getter
@Setter
  @TableName("acl_user")
@ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("会员id")
        private String id;

      @ApiModelProperty("微信openid")
      private String username;

      @ApiModelProperty("密码")
      private String password;

      @ApiModelProperty("昵称")
      private String nickName;

      @ApiModelProperty("用户头像")
      private String salt;

      @ApiModelProperty("用户签名")
      private String token;

      @ApiModelProperty("逻辑删除 1（true）已删除， 0（false）未删除")
      private Integer isDeleted;

      @ApiModelProperty("创建时间")
        @TableField(fill = FieldFill.INSERT)
      private LocalDateTime gmtCreate;

      @ApiModelProperty("更新时间")
        @TableField(fill = FieldFill.INSERT_UPDATE)
      private LocalDateTime gmtModified;


}
