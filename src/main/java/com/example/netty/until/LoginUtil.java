package com.example.netty.until;

import com.example.netty.protocol.command.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;


public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        //设置数据属性
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }
}
