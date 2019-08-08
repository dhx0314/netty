package com.nettydome.nettydemo.netty;

import com.nettydome.nettydemo.Dao.DtuDao;
import com.nettydome.nettydemo.entity.Dtu;
import com.nettydome.nettydemo.util.DtuUtil;
import com.nettydome.nettydemo.util.FlowUtil;
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


/**
 * @ClassName DtuServerHandler
 * @Description TODO
 * @Author Lei
 * @Date 2019/8/2 11:01
 * @Version 1.0
 **/
@Component
@ChannelHandler.Sharable
@Slf4j
public class DtuServerHandler extends ChannelInboundHandlerAdapter {

    //用于记录和管理所有客户端的channel
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Autowired
    private DeviceConnectionManagement deviceConnectionManagement;

    @Autowired
    private DtuUtil dtuUtil;

    @Autowired
    private DtuDao dtuDao; //File -- Settings -- Inspections 修改Autowired检测的级别

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        dtuUtil.setDtu();//初始化DTU设备的IP
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        String dtuIp = DtuUtil.getDtuIp(ctx);
        Dtu dtu = dtuDao.selectOneByIp(dtuIp);

        // System.out.println(clientIP);
        if (dtu != null) {
            System.out.println("来自设备 " + dtu.getDtuId() + "发送的数据");
            if (dtu.getDtuId()==1) {
                //调用netWeight方法，每次返回一个净重
                double netWeight = FlowUtil.netWeight(bytes);
                //   调用tareWeigh方法，每次返回一个皮重
                String tareWeigh = FlowUtil.tareWeigh(bytes);
                System.out.print(tareWeigh);
                System.out.print("====");
                System.out.print(netWeight);
                System.out.print("****");
                System.out.println();
                DeviceConnectionManagement.onlineCount();
                DeviceConnectionManagement.output();
            }
            else {
                String s=new String(bytes);
                System.out.println(s);
            }
        } else {
            System.out.println("未知设备");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String dtuIp = DtuUtil.getDtuIp(ctx);
        Dtu dtu = dtuDao.selectOneByIp(dtuIp);
        String dtuId = Integer.toString(dtu.getDtuId());
        deviceConnectionManagement.put(dtuId, ctx.channel());


        users.add(ctx.channel());
        log.info("设备id: {},设备ip: {},设备mac: {}", dtu.getDtuId(), dtu.getDtuIp(), dtu.getDtuMac());
        log.info("设备id: {} 连接上线, channel对应的长id为: {}", dtu.getDtuId(), ctx.channel().id().asLongText());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //ChannelGroup是自动管理，当浏览器关闭或者刷新页面，channel会被自动移除
        //当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
        //clients.remove(ctx.channel());

        String dtuIp = DtuUtil.getDtuIp(ctx);
        Dtu dtu = dtuDao.selectOneByIp(dtuIp);
        log.info("设备id: {},设备ip: {},设备mac: {}", dtu.getDtuId(), dtu.getDtuIp(), dtu.getDtuMac());
        log.info("设备id: {} 断开连接,channel对应的长id为：{} ", dtu.getDtuId(), ctx.channel().id().asLongText());
        log.info("设备id: {} 断开连接,channel对应的短id为：{}", dtu.getDtuId(), ctx.channel().id().asShortText());
        users.remove(ctx.channel());
        DeviceConnectionManagement.deleteChanel(ctx.channel());
        DeviceConnectionManagement.output();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("channel捕获到了异常: " + cause.toString());
        // 发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }


}
