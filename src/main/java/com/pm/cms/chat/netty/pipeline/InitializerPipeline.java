package com.pm.cms.chat.netty.pipeline;

import com.pm.cms.chat.netty.handler.cms.*;
import com.pm.cms.chat.netty.handler.core.LoginHandler;
import com.pm.cms.chat.netty.handler.core.MsgChatHandler;
import com.pm.cms.chat.netty.handler.core.PingHandler;
import com.pm.cms.chat.netty.pojo.Msg;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 管道处理链配置类
 *
 * @Author: LIU XILIN
 * @Date: Created in 2018/07/30 20:14
 */
@Service("initializerPipeline")
public class InitializerPipeline extends ChannelInitializer<SocketChannel> {

    @Autowired
    private MsgChatHandler msgChatHandler;
    @Autowired
    private PingHandler pingHandler;
    @Autowired
    private LoginHandler loginHandler;
    @Autowired
    private FodderPublishMessageHandler fodderPublishMessageHandler;
    @Autowired
    private FodderDownMessageHandler fodderDownMessageHandler;
    @Autowired
    private AdsSettingMessageHandler adsSettingMessageHandler;

    /**
     * 耗时任务
     */
    static final EventExecutorGroup MSGCHATGROUP = new DefaultEventExecutorGroup(2);
    static final EventExecutorGroup PINGCAHTGROUP = new DefaultEventExecutorGroup(2);
    static final EventExecutorGroup LOGINGROUP = new DefaultEventExecutorGroup(2);
    static final EventExecutorGroup ADSMESSAGE = new DefaultEventExecutorGroup(2);
    static final EventExecutorGroup ADSDOWNMESSAGE = new DefaultEventExecutorGroup(2);
    static final EventExecutorGroup ADSSETTINGMESSAGE = new DefaultEventExecutorGroup(2);

    public InitializerPipeline() {
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //心跳的定时触发器
        pipeline.addLast(new IdleStateHandler(41, 0, 0, TimeUnit.SECONDS));
        //防止TCP丢包编解码
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
        //ProtoBuf解码器
        pipeline.addLast(new ProtobufDecoder(Msg.Message.getDefaultInstance()));
        //TCP丢包解码器
        pipeline.addLast(new LengthFieldPrepender(2));
        //ProtoBuf编码器
        pipeline.addLast(new ProtobufEncoder());
        //所有消息先到MsgHandler中进行分发
        pipeline.addLast(MSGCHATGROUP, msgChatHandler);
        //心跳处理
        pipeline.addLast(PINGCAHTGROUP, pingHandler);
        //登陆处理
        pipeline.addLast(LOGINGROUP, loginHandler);
        //广告屏上架信息处理
        pipeline.addLast(ADSMESSAGE, fodderPublishMessageHandler);
        //广告屏下架信息处理
        pipeline.addLast(ADSDOWNMESSAGE, fodderDownMessageHandler);
        //广告屏设置信息处理
        pipeline.addLast(ADSSETTINGMESSAGE, adsSettingMessageHandler);
    }
}
