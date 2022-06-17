package com.cj.modular.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Caoj
 * @since 2022-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_movies")
@Accessors(chain = true)
public class Movies implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 中文名
     */
    private String name;

    /**
     * 影片原名
     */
    private String alias;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 地区
     */
    private String location;

    /**
     * 类型
     */
    private String type;

    /**
     * 豆瓣评分
     */
    private Float douban;

    /**
     * imdb评分
     */
    private Float imdb;

    /**
     * 蓝光原盘链接
     */
    private String blueray;

    /**
     * REMUX链接
     */
    private String remux;

    /**
     * 其他种子链接
     */
    private String other;

    /**
     * 是否是4K资源
     */
    @TableField("is4K")
    private String is4k;

    /**
     * 豆瓣地址
     */
    private String url;

    /**
     * 导演
     */
    private String director;

    /**
     * 编剧
     */
    private String scriptWriter;

    /**
     * 主演
     */
    private String cast;

    /**
     * 喜爱
     */
    private Integer favorite;

    /**
     * 海报
     */
    private String poster;

    /**
     * 时长
     */
    private String duration;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
