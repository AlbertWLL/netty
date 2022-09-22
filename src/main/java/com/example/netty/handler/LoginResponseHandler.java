package com.example.netty.handler;

import com.example.netty.netty.client.NettyClientOne;
import com.example.netty.protocol.command.resp.LoginResponsePacket;
import com.example.netty.until.Session;
import com.example.netty.until.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.isSuccess()) {
            System.out.println(new Date() + ": 客户端登录成功~");
            System.out.println(new Date() + ": 用户的userId为： " + loginResponsePacket.getUserId());
            Session session = Session.builder().userId(loginResponsePacket.getUserId()).userName(loginResponsePacket.getUserName()).build();
            SessionUtil.bindSession(session,ctx.channel());
//            NettyClientOne.generateMessageForServer(ctx.channel());
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//        loginRequestPacket.setUserId(920816);
//        loginRequestPacket.setUsername("danque");
//        loginRequestPacket.setPassword("wll920816wll");
//        System.out.println(new Date() + ": 客户端写出数据" + JSONObject.toJSONString(loginRequestPacket));
//        // 编码
//        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);
//        // 2. 把数据写到服务端
//        ctx.channel().writeAndFlush(buffer);
//    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        //用户断线之后取消绑定
        SessionUtil.unBindSession(ctx.channel());
        System.out.println("客户端连接被关闭!");
    }
}


