package com.example.netty.until;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Session {
    // 用户唯一性标识
    private Integer userId;

    private String userName;

    /**
     * 省略其他属性
     */
}
