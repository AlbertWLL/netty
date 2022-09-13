package com.example.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.netty.protocol.command.req.MessageRequestPacket;
import com.example.netty.protocol.command.resp.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        ctx.channel().writeAndFlush(receiveMessage(messageRequestPacket));
    }

    private Object receiveMessage( MessageRequestPacket messageRequestPacket) {
        System.out.println(new Date() + ": 服务端读到客户端发送的消息 -> " + JSONObject.toJSONString(messageRequestPacket));
        //接收到客户端的消息之后   回复消息给客户端
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
        return messageResponsePacket;
    }
}
