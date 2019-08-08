package com.nettydome.nettydemo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ClassName DtuServer
 * @Description TODO
 * @Author Lei
 * @Date 2019/8/2 10:44
 * @Version 1.0
 **/
@Component
@Slf4j
public class DtuServer {

    /**
     * NETT服务器配置类
     */


    @Value("${netty.port}")
    private Integer port;

    @Autowired
    private DtuServerChildHandler dtuServerChildHandler;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void bind(){
//        int port = nettyConfig.getPort();
//        配置服务端的Nio线程组
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //接收缓冲区 ,发送缓冲区
//            bootstrap.option(ChannelOption.SO_SNDBUF, 16*1024)
//                    .option(ChannelOption.SO_RCVBUF, 16*1024)
//                    .option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.group(bossGroup,workGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG,1024)
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(dtuServerChildHandler);

            log.info("【服务器启动成功========端口："+port+"】");
//            绑定端口，同步等待成功
            ChannelFuture future = bootstrap.bind(port).sync();
//            等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

//    将netty服务加进springboot中
    @PostConstruct()
    public void init(){
        //需要开启一个新的线程来执行netty server 服务器
        new Thread(new Runnable() {
            @Override
            public void run() {
                bind();
            }
        }).start();
    }

}
