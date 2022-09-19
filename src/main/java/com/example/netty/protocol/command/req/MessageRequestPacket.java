package com.example.netty.protocol.command.req;

import com.example.netty.protocol.command.Packet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.netty.protocol.command.Command.MESSAGE_REQUEST;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestPacket extends Packet {
    private Integer toUserId;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
