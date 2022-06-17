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
public class TIdCard implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 身份证号码前6位
     */
    @TableId(value = "id_code", type = IdType.AUTO)
    private Integer idCode;

    /**
     * 省/直辖市
     */
    private String province;

    /**
     * 市/直辖市的区
     */
    private String city;

    /**
     * 区/直辖市无此列
     */
    private String area;


}
