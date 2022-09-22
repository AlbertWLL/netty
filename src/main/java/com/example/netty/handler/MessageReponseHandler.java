package com.example.netty.handler;

import com.example.netty.protocol.command.resp.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageReponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) {
        System.out.println("收到自来【" + messageResponsePacket.getFromUserId() + "】的消息："
                + messageResponsePacket.getMessage().substring(
                        messageResponsePacket.getMessage().indexOf("@") + 1,messageResponsePacket.getMessage().length()
        ));
    }
}
