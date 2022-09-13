package com.example.netty.netty.server;

import com.alibaba.fastjson.JSONObject;
import com.example.netty.protocol.command.*;
import com.example.netty.protocol.command.req.LoginRequestPacket;
import com.example.netty.protocol.command.req.MessageRequestPacket;
import com.example.netty.protocol.command.resp.LoginResponsePacket;
import com.example.netty.protocol.command.resp.MessageResponsePacket;
import com.example.netty.protocol.command.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    public static volatile Integer loginSuccss  = 0;

    /**
     * 读取连接发送的数据
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
    }

    private boolean loginValid(LoginRequestPacket loginRequestPacket) {
        String password = loginRequestPacket.getPassword();
        if("wll920816wll".equals(password) && loginRequestPacket.getUserId() == 920816){
            return true;
        }
        //TODO 登陆校验逻辑 此处忽略DB的数据校验逻辑
        return false;
    }


    /**
     * 发送消息
     * @param ctx
     */
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) {
//        System.out.println(new Date() + ": 服务端写出数据");
//        // 1. 获取数据
//        ByteBuf buffer = ctx.alloc().buffer();
//        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
//        if(loginSuccss.equals(1)){
//            loginResponsePacket.setSuccess(true);
//        }else{
//            loginResponsePacket.setSuccess(false);
//            loginResponsePacket.setReason("密码错误，请从新登陆。。。");
//        }
//        buffer.writeBytes(PacketCodeC.INSTANCE.encode(ctx.alloc(),loginResponsePacket));
//        // 2. 把数据写到服务端
//        ctx.channel().writeAndFlush(buffer);
//    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 1. 获取二进制抽象 ByteBuf  ctx.alloc() 获取到一个 ByteBuf 的内存管理器，
        ByteBuf buffer = ctx.alloc().buffer();
        // 2. 准备数据，指定字符串的字符集为 utf-8
        byte[] bytes = "server-你好，闪电侠!".getBytes(Charset.forName("utf-8"));
        // 3. 填充数据到 ByteBuf
        buffer.writeBytes(bytes);
        return buffer;
    }
}
