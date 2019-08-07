package com.nettydome.nettydemo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName EchoServerChildHandler
 * @Description TODO
 * @Author Lei
 * @Date 2019/8/6 13:38
 * @Version 1.0
 **/
@Component
public class EchoServerChildHandler extends ChannelInitializer<SocketChannel> {


    private HeartBeatHandler heartBeatHandler;
    @Autowired
    private EchoServerHandler echoServerHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
//        ByteBuf byteBuf = Unpooled.copiedBuffer("\r\n".getBytes());
//        pipeline.addLast(new DelimiterBasedFrameDecoder(1024,byteBuf));
        pipeline.addLast(echoServerHandler);
        pipeline.addLast(new IdleStateHandler(18, 28, 38));
        pipeline.addLast(new HeartBeatHandler());

    }
}
