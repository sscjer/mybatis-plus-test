package com.cj.modular.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Caoj
 * @since 2022-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Temp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影名
     */
    private String title;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 海报
     */
    private String poster;

    /**
     * 导演
     */
    private String director;

    /**
     * 编剧
     */
    private String scriptWriter;

    /**
     * 演员表
     */
    private String cast;

    /**
     * 类型
     */
    private String type;

    /**
     * 国家
     */
    private String location;

    /**
     * 片长
     */
    private String duration;

    /**
     * 评分
     */
    private Float douban;


}
