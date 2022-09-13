package com.example.netty.until;

/**
 * @description:
 * @ClassName:1
 * @author: danque
 * @date: 2022/9/8 15:28
 */
public class GenerateSql {
    public static void main(String[] args) {
        for(int i = 1; i <= 50; i++ ){
            String str = "CREATE INDEX idx_created ON JGCombine_1000.dbo.FInOut_"+ i + " (created);";
            System.out.println(str);
        }
    }
}
