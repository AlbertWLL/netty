package com.example.netty.handler;

import com.example.netty.protocol.command.req.MessageRequestPacket;
import com.example.netty.protocol.command.resp.MessageResponsePacket;
import com.example.netty.until.Session;
import com.example.netty.until.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        // 1.拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());
        // 2.通过消息发送方的会话信息构造要发送的消息
        Integer toUserId = Integer.valueOf(messageRequestPacket.getMessage().substring(0, messageRequestPacket.getMessage().indexOf("@")));
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setToUserId(toUserId);
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());
        log.info("服务收到给【{}】的短信：【{}】",toUserId,messageRequestPacket.getMessage());
        // 服务器接收到消息后转发给客户端用户
        Channel toUserChannel = SessionUtil.getChannel(toUserId);
        if(toUserChannel != null && SessionUtil.hasLogin(toUserChannel)){
            toUserChannel.writeAndFlush(messageResponsePacket);
        }else {
            log.warn("【" + toUserId + "】 不在线，发送失败!");
        }
    }
}
