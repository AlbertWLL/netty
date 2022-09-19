package com.example.netty.handler;

import com.example.netty.protocol.command.req.MessageRequestPacket;
import com.example.netty.protocol.command.resp.MessageResponsePacket;
import com.example.netty.until.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        //接收到客户端的消息之后   回复消息给客户端
        Integer userId = Integer.valueOf(messageRequestPacket.getMessage().substring(0, messageRequestPacket.getMessage().indexOf("@")));
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setToUserId(userId);
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());
        log.info("服务收到给【{}】的短信：【{}】",userId,messageRequestPacket.getMessage());
        // 服务器接收到消息后转发给客户端用户
        Channel channel = SessionUtil.getChannel(userId);
        channel.writeAndFlush(messageResponsePacket);
    }
}
