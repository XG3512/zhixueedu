package com.xvls.alexander.utils;

import org.springframework.stereotype.Component;

/**
 * 数据计算工具
 */
@Component
public class CalculateUtil {

    /**
     * 计算数据
     * @param num
     * @return
     */
    public static String CalculateNum(Integer num){
        if(num/10000!=0){
            num=num/1000;
            String result = String.valueOf(num.doubleValue()/10f);
            result=result+"万";
            return result;
        }else{
            return num.toString();
        }
    }
}
