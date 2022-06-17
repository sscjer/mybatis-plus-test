package com.cj.modular.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 地区码表
 * </p>
 *
 * @author caoj
 * @since 2021-08-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class TArea implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 地区编码
     */
    private String areaCode;

    /**
     * 地区名
     */
    private String areaName;

    /**
     * 地区级别（1:省份province,2:市city,3:区县district）
     */
    private Integer level;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 城市中心点（即：经纬度坐标）
     */
    private String center;

    /**
     * 地区父节点
     */
    private Integer parentId;

    /**
     * 子节点
     */
    @TableField(exist = false)
    private List<TArea> child;
}
