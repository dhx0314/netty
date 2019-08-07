package com.nettydome.nettydemo.netty;

import com.nettydome.nettydemo.Dao.DtuDao;
import com.nettydome.nettydemo.entity.Dtu;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName EchoServerHandler
 * @Description TODO
 * @Author Lei
 * @Date 2019/8/2 11:01
 * @Version 1.0
 **/
@Component
@ChannelHandler.Sharable
@Slf4j
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    //用于记录和管理所有客户端的channel
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Autowired
    private UserChannelRel userChannelRel;

    @Autowired
    private SetDtuIp setDtuIp;

    @Autowired
    private DtuDao dtuDao;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        setDtuIp.setDtu();//初始化DTU设备的IP
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        userChannelRel.put(clientIP, ctx.channel());
        System.out.println(clientIP);
        Dtu dtu = dtuDao.selectOneByIp(clientIP);
        if (dtu!=null) {
            System.out.println("来自设备 " + dtu.getId() + "发送的数据");
            for (byte aByte : bytes) {
                System.out.print(aByte + " ");
            }
            System.out.println();
            //调用netWeight方法，每次返回一个净重
//        double netWeight = netWeight(bytes);
//        //   调用tareWeigh方法，每次返回一个净重
//        String tareWeigh = tareWeigh(bytes);
//        System.out.print(tareWeigh);
//        System.out.print("====");
//        System.out.print(netWeight);
//        System.out.print("****");
        }else {
            System.out.println("未知设备");
        }
}

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
        log.info("客户端连接，channel对应的长id为： " + ctx.channel().id().asLongText());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //ChannelGroup是自动管理，当浏览器关闭或者刷新页面，channel会被自动移除
        //当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
        //clients.remove(ctx.channel());
        log.info("客户端断开，channel对应的长id为： " + ctx.channel().id().asLongText());
        log.info("客户端断开，channel对应的短id为： " + ctx.channel().id().asShortText());
        users.remove(ctx.channel());
        UserChannelRel.deleteChanel(ctx.channel());
        UserChannelRel.output();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("channel捕获到了异常: " + cause.toString());
        // 发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }

    //    净重
    public static double netWeight(byte[] b) {
        String sum = "";
        for (int i = 4; i <= 9; i++) {
            sum += (char) b[i];
        }
        double abc = Double.valueOf(sum);
        return abc;
    }

    //    皮重
    public static String tareWeigh(byte[] b) {
        String sum = "";
        for (int i = 12; i <= 17; i++) {
            sum += (char) b[i];
        }
        return sum;

    }


}
