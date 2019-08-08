package com.nettydome.nettydemo.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName DtuServerChildHandler
 * @Description TODO
 * @Author Lei
 * @Date 2019/8/6 13:38
 * @Version 1.0
 **/
@Component
public class DtuServerChildHandler extends ChannelInitializer<SocketChannel> {


    private HeartBeatHandler heartBeatHandler;
    @Autowired
    private DtuServerHandler dtuServerHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //分隔符解码器
//        ByteBuf byteBuf = Unpooled.copiedBuffer("\r\n".getBytes());
//        pipeline.addLast(new DelimiterBasedFrameDecoder(1024,byteBuf));
        pipeline.addLast(dtuServerHandler);
        pipeline.addLast(new IdleStateHandler(8, 14, 18));
        pipeline.addLast(new HeartBeatHandler());

    }
}
