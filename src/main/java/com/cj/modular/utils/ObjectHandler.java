package com.cj.modular.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * auto insert updateTime and createTime
 * </p>
 *
 * @author Caoj
 * @since 2021-10-07 17:28
 */
@Component
public class ObjectHandler implements MetaObjectHandler {
    @Override
    // 插入时更新字段
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
    }

    @Override
    // 修改时更新字段
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
