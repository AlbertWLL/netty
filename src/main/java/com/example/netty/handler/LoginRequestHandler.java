package com.example.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.netty.protocol.command.PacketCodeC;
import com.example.netty.protocol.command.req.LoginRequestPacket;
import com.example.netty.protocol.command.resp.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        ctx.channel().writeAndFlush(login(loginRequestPacket));
    }

    private LoginResponsePacket login(LoginRequestPacket loginRequestPacket) {
        System.out.println(new Date() + ": 服务端读到登陆请求。。。 -> " + JSONObject.toJSONString(loginRequestPacket));
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        if (loginValid(loginRequestPacket)) {
            // 校验成功
            loginResponsePacket.setSuccess(true);
            System.out.println("登陆检验成功！");
        } else {
            // 校验失败
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("密码错误，请从新登陆。。。");
            System.out.println("登陆检验失败！");
        }
        return loginResponsePacket;
    }

    private boolean loginValid(LoginRequestPacket loginRequestPacket) {
        String password = loginRequestPacket.getPassword();
        if ("wll920816wll".equals(password) && loginRequestPacket.getUserId() == 920816) {
            return true;
        }
        //TODO 登陆校验逻辑 此处忽略DB的数据校验逻辑
        return false;
    }
}
