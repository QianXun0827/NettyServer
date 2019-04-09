package com.pm.cms.chat.ws.listener;

import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;

public class WebSocketListener implements GenericFutureListener<ChannelFuture> {

	@Override
	public void operationComplete(ChannelFuture future) throws Exception {
		future.sync();
	}

}