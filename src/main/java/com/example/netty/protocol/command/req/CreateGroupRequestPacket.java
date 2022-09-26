package com.example.netty.protocol.command.req;

import com.example.netty.protocol.command.Packet;
import lombok.Data;

import java.util.List;

import static com.example.netty.protocol.command.Command.CREATE_GROUP;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<Integer> userIdList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP;
    }
}
