package com.cj.modular.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class TPhoneNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号前7位
     */
    @TableId(value = "number", type = IdType.AUTO)
    private Integer number;

    /**
     * 省份
     */
    private String province;

    /**
     * 市
     */
    private String city;


}
