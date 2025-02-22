package cn.lili.modules.message.mapper;

import cn.lili.modules.message.entity.dos.WechatMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;

/**
 * 微信消息 Dao层
 * @author Chopper
 */
public interface WechatMessageMapper extends BaseMapper<WechatMessage> {

    @Delete("delete from li_wechat_message")
    void deleteAll();
}