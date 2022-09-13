package com.example.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.netty.protocol.command.PacketCodeC;
import com.example.netty.protocol.command.req.LoginRequestPacket;
import com.example.netty.protocol.command.resp.LoginResponsePacket;
import com.example.netty.until.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        System.out.println(new Date() + ": 客户端读到登陆响应数据 。。。-> " + JSONObject.toJSONString(loginResponsePacket));
        if (loginResponsePacket.isSuccess()) {
            System.out.println(new Date() + ": 客户端登录成功");
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(920816);
        loginRequestPacket.setUsername("danque");
        loginRequestPacket.setPassword("wll920816wll");
        System.out.println(new Date() + ": 客户端写出数据" + JSONObject.toJSONString(loginRequestPacket));
        // 编码
        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);
        // 2. 把数据写到服务端
        ctx.channel().writeAndFlush(buffer);
    }
}


