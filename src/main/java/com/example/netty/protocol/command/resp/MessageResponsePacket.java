package com.example.netty.protocol.command.resp;

import com.example.netty.protocol.command.Packet;
import lombok.Data;

import static com.example.netty.protocol.command.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}
