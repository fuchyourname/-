package com.and.music.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Date;

@Configuration
public class MybatisHandler implements MetaObjectHandler {

    private Date date;

    @Override
    public void insertFill(MetaObject metaObject) {
        this.date = new Date();
        this.strictInsertFill(metaObject, "createTime", Date.class, date);  // 创建时间
        this.strictInsertFill(metaObject, "updateTime", Date.class, date);  // 更新时间
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.date = new Date();
        this.strictUpdateFill(metaObject, "updateTime", Date.class, date);  // 更新时间
    }
}
