package com.example.netty.netty.server;

import com.example.netty.codec.PacketDecoder;
import com.example.netty.codec.PacketEncoder;
import com.example.netty.codec.Spliter;
import com.example.netty.handler.LoginRequestHandler;
import com.example.netty.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //引导类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                //设置IO模型 BIO: OioServerSocketChannel, NIO:  NioServerSocketChannel
                .channel(NioServerSocketChannel.class)
                //我们调用childHandler()方法，给这个引导类创建一个ChannelInitializer
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    //给这个引导类创建一个ChannelInitializer，这里主要就是定义后续每条连接的数据读写，业务处理逻辑
                    protected void initChannel(NioSocketChannel ch) {
                        //责任链模式
//                        ch.pipeline().addLast(new FirstServerHandler());
                        ch.pipeline().addLast(new Spliter())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginRequestHandler())
                                .addLast(new MessageRequestHandler())
                                .addLast(new PacketEncoder());
                    }
                });
        bind(serverBootstrap, 8000);
    }


    /**
     * 端口绑定失败后   端口号+1重新绑定端口
     *
     * @param serverBootstrap
     * @param port
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        if (port > 8088) {
            System.out.println("绑定端口失败次数过多请检查端口配置！");
            return;
        }
        //绑定端口 它是一个异步的方法， serverBootstrap.bind调用之后是立即返回的，他的返回值是一个ChannelFuture
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功!");
                } else {
                    System.err.println("端口[" + port + "]绑定失败!");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
