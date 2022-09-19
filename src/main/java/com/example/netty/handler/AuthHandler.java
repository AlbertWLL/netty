package com.example.netty.handler;

import com.example.netty.until.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("AuthHandler-channelRead");
        if (!LoginUtil.hasLogin(ctx.channel())) {
//            ctx.channel().close();
        } else {
            // 一行代码实现逻辑的删除 从pipeline中移除当前的handler
//            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("AuthHandler-handlerRemoved");
        if (LoginUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }
}
