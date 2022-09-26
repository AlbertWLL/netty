package com.example.netty.protocol.command.resp;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @ClassName:
 * @author: danque
 * @date: 2022/9/22 23:12
 */
@Data
public class CreateGroupResponsePacket {

    private boolean success;

    private String groupId;

    private List<String> userNameList;
}
