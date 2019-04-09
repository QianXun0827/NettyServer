package com.pm.cms.chat.util;

import com.pm.cms.chat.netty.session.Session;
import com.pm.cms.chat.netty.session.SessionManager;
import com.pm.cms.chat.ws.pojo.LoginMsg;
import com.pm.cms.mapper.PmUserMapper;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: derekhehe@yahoo.com
 * @Date: Created in 2018/8/22 09:54
 * @Description:
 * @Modified By:
 */
@Slf4j
@Service
public class WSUtils {
    @Autowired
    private PmUserMapper pmUserMapper;

    /**
     * ws登录认证
     *
     * @param loginMsg
     * @param ctx
     * @return
     */
    public boolean loginAuth(LoginMsg loginMsg, ChannelHandlerContext ctx) {
        String userId = loginMsg.getUserId() + "";
        String userName = pmUserMapper.getUserName(loginMsg.getUserId());
        int flag = pmUserMapper.auth(Integer.valueOf(userId));
        if (flag >= 1) {
            Session session = SessionManager.get(userId);
            if (session != null) {
                SessionManager.remove(session);
                session.getChannel().close();
                session = new Session(ctx.channel(), userId);
                SessionManager.add(userId, session);
            } else {
                session = new Session(ctx.channel(), userId);
                SessionManager.add(userId, session);
            }
            log.info("webSocket登陆成功，登陆用户：{}", userName);
            return true;
        } else {
            return false;
        }
    }
}
