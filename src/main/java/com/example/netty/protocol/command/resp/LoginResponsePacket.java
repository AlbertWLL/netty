
package com.example.netty.protocol.command.resp;

import com.example.netty.protocol.command.Packet;
import lombok.Data;

import static com.example.netty.protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
