package com.example.netty.handler;

import com.example.netty.protocol.command.resp.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageReponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageRequestPacket) {
        System.out.println("收到服务端回复的消息：" + messageRequestPacket.getMessage());
    }
}
