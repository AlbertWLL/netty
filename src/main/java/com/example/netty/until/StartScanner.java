//package com.example.netty.until;
//
//import com.example.netty.protocol.command.PacketCodeC;
//import com.example.netty.protocol.command.req.LoginRequestPacket;
//import com.example.netty.protocol.command.req.MessageRequestPacket;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.Channel;
//import org.springframework.util.StringUtils;
//
//import java.util.Scanner;
//
///**
// * @description:
// * @ClassName:StartScanner
// * @author: danque
// * @date: 2022/9/19 23:13
// */
//public class StartScanner {
//
//    public static void generateMessageForServer(Channel channel) {
//        if(SessionUtil.hasLogin(channel)){
//            System.out.println("输入消息发送至服务端: ");
//            //开启控制台
//            Scanner sc = new Scanner(System.in);
//            String messgae = sc.nextLine();
//            //如果已经登录就发送消息，否则进行登录
//            if(StringUtils.isEmpty(messgae)){
//                System.out.println("输入的消息不能为空: ");
//                generateMessageForServer(channel);
//            }
//            MessageRequestPacket packet = new MessageRequestPacket();
//            packet.setMessage(messgae);
//            ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(), packet);
//            channel.writeAndFlush(byteBuf);
//            generateMessageForServer(channel);
//        }else{
//            System.out.println("请输入用户名，进行登录~");
//            //开启控制台
//            Scanner sc = new Scanner(System.in);
//            String messgae = sc.nextLine();
//            //如果已经登录就发送消息，否则进行登录
//            if(StringUtils.isEmpty(messgae)){
//                System.out.println("输入的消息不能为空: ");
//                generateMessageForServer(channel);
//            }
//            LoginRequestPacket loginRequestPacket = LoginRequestPacket.builder().userName(messgae).build();
//            ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(), loginRequestPacket);
//            channel.writeAndFlush(byteBuf);
//            generateMessageForServer(channel);
//        }
//    }
//
//}
