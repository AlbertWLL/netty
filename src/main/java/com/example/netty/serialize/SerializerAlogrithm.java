package com.example.netty.serialize;

public interface SerializerAlogrithm {
    /**
     * json 序列化
     */
    byte JSON = 1;

    /**
     * hessian 序列化
     */
    byte HESSIAN = 2;
}
