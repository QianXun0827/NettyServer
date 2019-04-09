package com.pm.cms.chat.ws.plugin;

import com.pm.cms.common.content.PropertiesContext;
import com.pm.cms.chat.ws.listener.WebSocketListener;
import com.pm.cms.chat.ws.pipeline.WsInitializerPipeline;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * WebSocke插件的配置
 *
 * @Author: LIU XILIN
 * @Date: Created in 2018/07/30 20:28
 */
@Service("webSocketPlugin")
public class WebSocketPlugin {
    private static Logger logger = LoggerFactory.getLogger(WebSocketPlugin.class);

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup(4);

    @Autowired
    private WsInitializerPipeline webSocketServerInitializer;
    @Autowired
    private PropertiesContext propertiesContext;

    public WebSocketPlugin() {

    }

    @PostConstruct
    public void serviceStart() {
        int port = propertiesContext.getWebsocketPort();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
            b.childHandler(webSocketServerInitializer);

            Channel ch = b.bind(port).sync().channel();
            logger.info("服务器启动端口:=====[{}]=====", port);
            ch.closeFuture().addListener(new WebSocketListener());
        } catch (Exception e) {
            logger.error("服务器启动异常:", e);
            e.printStackTrace();

        }
    }

    @PreDestroy
    public void serviceStop() {
        logger.info("关闭WebSocket...");
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
