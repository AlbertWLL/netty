package com.example.netty.protocol.command;

/**
 * 指令
 */
public interface Command {

    /**
     * 登录指令
     */
    Byte LOGIN_REQUEST = 1;

    /**
     * 登陆相应指令
     */
    Byte LOGIN_RESPONSE = 2;

    /**
     * 客户端发送消息指令
     */
    Byte MESSAGE_REQUEST = 3;

    /**
     * 服务端发送至客户端的消息指令
     */
    Byte MESSAGE_RESPONSE = 4;
}
