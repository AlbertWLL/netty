package com.example.netty.netty.client;

import com.example.netty.codec.PacketDecoder;
import com.example.netty.codec.PacketEncoder;
import com.example.netty.codec.Spliter;
import com.example.netty.handler.LoginResponseHandler;
import com.example.netty.handler.MessageReponseHandler;
import com.example.netty.protocol.command.PacketCodeC;
import com.example.netty.protocol.command.req.LoginRequestPacket;
import com.example.netty.protocol.command.req.MessageRequestPacket;
import com.example.netty.until.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClientOne {

    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1.指定线程模型
                .group(workerGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 绑定自定义属性到 channel
                .attr(AttributeKey.newInstance("clientName"), "nettyClient")
                // 设置TCP底层属性
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                //tcp底层心跳机制
                .option(ChannelOption.SO_KEEPALIVE, true)
                //表示是否开始 Nagle 算法，true 表示关闭，false 表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就设置为 true 关闭，
                //如果需要减少发送次数减少网络交互，就设置为 false 开启
                .option(ChannelOption.TCP_NODELAY, true)
                // 3.IO 处理逻辑 这里主要就是定义连接的业务处理逻辑
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //责任链模式
                        ch.pipeline().addLast(new Spliter())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginResponseHandler())
                                .addLast(new MessageReponseHandler())
                                .addLast(new PacketEncoder());
                    }
                });
        // 4.建立连接
        bootstrapConnect(bootstrap,"127.0.0.1", 8000, MAX_RETRY);
    }

    private static void bootstrapConnect(Bootstrap bootstrap, String host, int port,  int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
                Channel channel = ((ChannelFuture) future).channel();
                // 连接成功之后，启动控制台线程
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                // bootstrap.config().group() 返回的就是我们在一开始的时候配置的线程模型 workerGroup，调 workerGroup 的 schedule 方法即可实现定时任务逻辑。
                bootstrap.config().group().schedule(new Runnable() {
                    @Override
                    public void run() {
                        bootstrapConnect(bootstrap, host, port, retry - 1);
                    }
                }, delay, TimeUnit.SECONDS);
            }
        });
    }


    /**
     * 开发控制台
     * @param channel
     */
    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                generateMessageForServer(channel);
            }
        }).start();
    }

    public static void generateMessageForServer(Channel channel) {
        if(SessionUtil.hasLogin(channel)){
            System.out.println("输入消息发送至服务端: ");
            //开启控制台
            Scanner sc = new Scanner(System.in);
            String messgae = sc.nextLine();
            //如果已经登录就发送消息，否则进行登录
            if(StringUtils.isEmpty(messgae)){
                System.out.println("输入的消息不能为空: ");
                generateMessageForServer(channel);
            }
            MessageRequestPacket packet = new MessageRequestPacket();
            packet.setMessage(messgae);
            ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(), packet);
            channel.writeAndFlush(byteBuf);
            generateMessageForServer(channel);
        }else{
            System.out.println("请输入用户名，进行登录~");
            //开启控制台
            Scanner sc = new Scanner(System.in);
            String messgae = sc.nextLine();
            //如果已经登录就发送消息，否则进行登录
            if(StringUtils.isEmpty(messgae)){
                System.out.println("输入的消息不能为空: ");
                generateMessageForServer(channel);
            }
            LoginRequestPacket loginRequestPacket = LoginRequestPacket.builder().userName(messgae).build();
            ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(), loginRequestPacket);
            channel.writeAndFlush(byteBuf);
            generateMessageForServer(channel);
        }
    }
}
