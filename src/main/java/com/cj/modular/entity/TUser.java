package com.cj.modular.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author caoj
 * @since 2021-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 手机号
     */
    private Long phone;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 阴历生日
     */
    private Date lunar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 居住地址
     */
    private String residentialAddress;

    /**
     * 用户类型
     */
    private Integer type;

    /**
     * 身份证号
     */
    private String idCard;

    /**
      备注
     */
    private String remark;

    /**
     * 公司名
     */
    private String company;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUser;


}
