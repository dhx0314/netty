package com.nettydome.nettydemo.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description: 用于检测channel的心跳handler 
 * 				 继承ChannelInboundHandlerAdapter，从而不需要实现channelRead0方法
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		
		// 判断evt是否是IdleStateEvent（用于触发用户事件，包含 读空闲/写空闲/读写空闲 ）
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent)evt;		// 强制类型转换
			
			if (event.state() == IdleState.READER_IDLE) {
				log.info("进入读空闲...");
			} else if (event.state() == IdleState.WRITER_IDLE) {
				log.info("进入写空闲...");
			} else if (event.state() == IdleState.ALL_IDLE) {

				log.info("触发了读写空闲");
				log.info("channel关闭前，dtuDevice的数量为：" + DtuServerHandler.users.size());
				
				Channel channel = ctx.channel();
				// 关闭无用的channel，以防资源浪费
				channel.close();



				log.info("channel关闭后，dtuDevice的数量为：" + DtuServerHandler.users.size());
			}
		}
		
	}
	
}
