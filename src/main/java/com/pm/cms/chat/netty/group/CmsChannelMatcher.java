package com.pm.cms.chat.netty.group;

import com.pm.cms.chat.netty.session.Session;
import com.pm.cms.chat.netty.session.SessionManager;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hepeng
 * @ProjectName mc
 * @date 2018/8/15/9:40
 * @Description:
 */
@Service("cmsChannelMatcher")
public class CmsChannelMatcher implements ChannelMatcher {

    public static List<String> iccids;

    @Override
    public boolean matches(Channel channel) {
        Session session = SessionManager.get(channel);
        String iccid;
        if(session!= null){
            iccid= session.getIccid();
            if(!iccids.isEmpty()){
                if (iccids.contains(iccid)){
                    return true;
                }
            }
        }
        return false;
    }
}
