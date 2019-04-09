package com.pm.cms.chat.netty.plugin;

import com.pm.cms.chat.netty.listener.ChatListener;
import com.pm.cms.chat.netty.pipeline.InitializerPipeline;
import com.pm.cms.common.content.PropertiesContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Netty插件的配置
 *
 * @Author: LIU XILIN
 * @Date: Created in 2018/07/30 20:08
 */
@Service("chatPlugin")
public class ChatPlugin {
    private static Logger log = Logger.getLogger(com.pm.cms.chat.netty.plugin.ChatPlugin.class);

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup(8);

    @Autowired
    private InitializerPipeline initializerPipeline;
    @Autowired
    private PropertiesContext propertiesContext;

    @PostConstruct
    public void serviceStart() {
        int port = propertiesContext.getNettyPort();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
            server.childHandler(initializerPipeline);

            server.option(ChannelOption.SO_BACKLOG, 1024);
            server.option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_RCVBUF, 10 * 1024)
                    .option(ChannelOption.SO_SNDBUF, 10 * 1024)
                    .option(EpollChannelOption.SO_REUSEPORT, true);

            server.childOption(ChannelOption.SO_KEEPALIVE, true);
            server.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            server.childOption(ChannelOption.TCP_NODELAY, true);

            Channel ch = server.bind(port).sync().channel();
            ch.closeFuture().addListener(new ChatListener());
            log.info("netty start port:"+port);
        } catch (Exception e) {
            log.error("服务器启动异常：", e);
        }
    }

    @PreDestroy
    public void serviceStop() {
        log.info("关闭服务器....");
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
