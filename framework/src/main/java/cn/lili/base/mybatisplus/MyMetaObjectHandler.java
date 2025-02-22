package cn.lili.base.mybatisplus;

import cn.lili.common.security.AuthUser;
import cn.lili.common.security.context.UserContext;
import cn.lili.common.utils.SnowFlake;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 字段填充审计
 *
 * @author lili
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        AuthUser authUser = UserContext.getCurrentUser();
        if (authUser != null) {
            this.setFieldValByName("createBy", authUser.getUsername(), metaObject);
        }
        this.setFieldValByName("createTime", new Date(), metaObject);
        //有值，则写入
        if (metaObject.hasGetter("deleteFlag")) {
            this.setFieldValByName("deleteFlag", false, metaObject);
        }
        if (metaObject.hasGetter("id")) {
            this.setFieldValByName("id", String.valueOf(SnowFlake.getId()), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        AuthUser authUser = UserContext.getCurrentUser();
        if (authUser != null) {
            this.setFieldValByName("updateBy", authUser.getUsername(), metaObject);
        }
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}

