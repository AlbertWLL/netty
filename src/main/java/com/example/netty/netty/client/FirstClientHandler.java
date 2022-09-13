package com.example.netty.netty.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.netty.protocol.command.*;
import com.example.netty.protocol.command.req.LoginRequestPacket;
import com.example.netty.protocol.command.resp.LoginResponsePacket;
import com.example.netty.protocol.command.resp.MessageResponsePacket;
import com.example.netty.protocol.command.PacketCodeC;
import com.example.netty.until.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        for (int i = 0; i < 1000; i++) {
            ByteBuf buffer = getByteBuf(ctx);
            ctx.channel().writeAndFlush(buffer);
        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "你好，欢迎关注我的微信公众号，《闪电侠的博客》!".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);
        return buffer;
    }

    /**
     *
     * 首先我们需要获取一个 netty 对二进制数据的抽象 ByteBuf，
     * 上面代码中, ctx.alloc() 获取到一个 ByteBuf 的内存管理器，这个 内存管理器的作用就是分配一个 ByteBuf，
     * 然后我们把字符串的二进制数据填充到 ByteBuf，这样我们就获取到了 Netty 需要的一个数据格式，
     * 最后我们调用 ctx.channel().writeAndFlush() 把数据写到服务端
     * @param ctx
     * @return
     */
//    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
//        // 1. 获取二进制抽象 ByteBuf  ctx.alloc() 获取到一个 ByteBuf 的内存管理器，
//        ByteBuf buffer = ctx.alloc().buffer();
//        // 2. 准备数据，指定字符串的字符集为 utf-8
//        byte[] bytes = "client-你好，闪电侠!".getBytes(Charset.forName("utf-8"));
//        // 3. 填充数据到 ByteBuf
//        buffer.writeBytes(bytes);
//        return buffer;
//    }



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if(packet instanceof LoginResponsePacket){
             System.out.println(new Date() + ": 客户端读到登陆响应数据 。。。-> " + JSONObject.toJSONString(packet));
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            System.out.println("LoginResponsePacket >>>> " +  JSON.toJSONString(loginResponsePacket));
            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + ": 客户端登录成功");
                LoginUtil.markAsLogin(ctx.channel());
            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
        }else if(packet instanceof MessageResponsePacket){
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + ": 接收到服务端回复的消息: " + messageResponsePacket.getMessage());
        }
    }

}
