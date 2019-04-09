package com.pm.cms.chat.ws.pipeline;

import com.pm.cms.chat.ws.handler.cms.CmsFodderDownHandler;
import com.pm.cms.chat.ws.handler.cms.CmsFodderPublishHandler;
import com.pm.cms.chat.ws.handler.cms.CmsAdsSettingHandler;
import com.pm.cms.chat.ws.handler.core.HttpRequestHandler;
import com.pm.cms.chat.ws.handler.core.LoginWsHandler;
import com.pm.cms.chat.ws.handler.core.WebSocketFrameHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wsInitializerPipeline")
public class WsInitializerPipeline extends ChannelInitializer<SocketChannel> {

    @Autowired
    private HttpRequestHandler httpRequestHandler;
    @Autowired
    private WebSocketFrameHandler webSocketFrameHandler;
    @Autowired
    private LoginWsHandler loginWsHandler;
    @Autowired
    private CmsAdsSettingHandler cmsAdsSettingHandler;
    @Autowired
    private CmsFodderDownHandler cmsFodderDownHandler;
    @Autowired
    private CmsFodderPublishHandler cmsFodderPublishHandler;

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
//        SSLContext sslContext = SslConfig.createSSLContext("PKCS12", "/mnt/file/cert/1658860_www.16made.com.pfx", "k5OAA3r1");
//        SSLEngine engine = sslContext.createSSLEngine();
//        engine.setUseClientMode(false);

        ChannelPipeline pipeline = ch.pipeline();

//        pipeline.addLast(new SslHandler(engine));
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(httpRequestHandler);
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(webSocketFrameHandler);

        pipeline.addLast(loginWsHandler);
        pipeline.addLast(cmsAdsSettingHandler);
        pipeline.addLast(cmsFodderPublishHandler);
        pipeline.addLast(cmsFodderDownHandler);
    }

}