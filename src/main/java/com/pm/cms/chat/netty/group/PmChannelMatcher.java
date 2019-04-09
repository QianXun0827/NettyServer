package com.pm.cms.chat.netty.group;

import com.pm.cms.chat.netty.session.Session;
import com.pm.cms.chat.netty.session.SessionManager;
import com.pm.cms.service.CoreService;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author derekhehe@yahoo.com
 * @Date: Created in 2018/8/3 09:47
 * @Description: 普铭channelgroup匹配类
 * @Modified By:
 */
@Service("pmChannelMatcher")
public class PmChannelMatcher implements ChannelMatcher {

    @Autowired
    private CoreService coreService;

    @Override
    public boolean matches(Channel channel) {
        Session session = SessionManager.get(channel);
        if (session != null) {
            String iccid = session.getIccid();
            return coreService.getAuth(iccid) != 0;
        }
        return false;
    }
}
