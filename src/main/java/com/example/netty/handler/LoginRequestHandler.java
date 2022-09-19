package com.example.netty.handler;

import com.example.netty.protocol.command.req.LoginRequestPacket;
import com.example.netty.protocol.command.resp.LoginResponsePacket;
import com.example.netty.until.Session;
import com.example.netty.until.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        Channel channel = ctx.channel();
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        if (loginValid(loginRequestPacket)) {
            Integer userId = new Random().nextInt(10000);
            loginResponsePacket.setSuccess(true);
            loginResponsePacket.setUserId(userId);
            loginResponsePacket.setUserName(loginRequestPacket.getUserName());
            Session session = Session.builder().userId(loginResponsePacket.getUserId()).userName(loginResponsePacket.getUserName()).build();
            SessionUtil.bindSession(session,ctx.channel());
            System.out.println("登陆检验成功！");
        } else {
            // 校验失败
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("用户名或密码错误，请从新登陆。。。");
            System.out.println("登陆检验失败！");
        }
        channel.writeAndFlush(loginResponsePacket);

//        ctx.channel().writeAndFlush(login(loginRequestPacket,ctx));
    }

//    private LoginResponsePacket login(LoginRequestPacket loginRequestPacket,ChannelHandlerContext ctx) {
////        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
////        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
////        if (loginValid(loginRequestPacket)) {
////            Integer userId = Math.abs(1000);
////            loginResponsePacket.setSuccess(true);
////            loginResponsePacket.setUserId(userId);
////            Session session = Session.builder().userId(userId).userName(loginRequestPacket.getUsername()).build();
////            SessionUtil.bindSession(session,ctx.channel());
////            System.out.println("登陆检验成功！");
////        } else {
////            // 校验失败
////            loginResponsePacket.setSuccess(false);
////            loginResponsePacket.setReason("用户名或密码错误，请从新登陆。。。");
////            System.out.println("登陆检验失败！");
////        }
////        return loginResponsePacket;
////    }

    private boolean loginValid(LoginRequestPacket loginRequestPacket) {
        //TODO 登陆校验逻辑 此处忽略DB的数据校验逻辑
        return true;
    }
}
