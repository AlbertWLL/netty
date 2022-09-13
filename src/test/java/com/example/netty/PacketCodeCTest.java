package com.example.netty;

import com.example.netty.protocol.command.req.LoginRequestPacket;
import com.example.netty.protocol.command.Packet;
import com.example.netty.serialize.Serializer;
import com.example.netty.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;

public class PacketCodeCTest {
    @Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId(123);
        loginRequestPacket.setUsername("zhangsan");
        loginRequestPacket.setPassword("password");

//        com.example.netty.protocol.command.PacketCodeCTest packetCodeC = new com.example.netty.protocol.command.PacketCodeCTest();
//        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
//        Packet decodedPacket = packetCodeC.decode(byteBuf);

//        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }
}
