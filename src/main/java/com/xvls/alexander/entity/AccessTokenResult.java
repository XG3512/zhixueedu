package com.xvls.alexander.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResult {
    private String access_token;//获取到的凭证
    private Integer expires_in;//凭证有效时间，单位：秒。目前是7200秒之内的值。
    private Integer errcode;//错误码
    private String errmsg;//错误信息
}
