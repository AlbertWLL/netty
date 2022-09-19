package com.example.netty.protocol.command.req;

import com.example.netty.protocol.command.Packet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.netty.protocol.command.Command.LOGIN_REQUEST;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestPacket extends Packet {
    private Integer userId;

    private String userName;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
