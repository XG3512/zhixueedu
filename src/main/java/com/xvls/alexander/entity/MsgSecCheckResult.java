package com.xvls.alexander.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgSecCheckResult {
    /**
     * 0	    内容正常
     * 87014	内容含有违法违规内容
     */
    private Integer errcode;//错误码

    /**
     * "ok"	           内容正常
     * "risky content" 内容含有违法违规内容
     */
    private String errMsg;//错误信息
}
