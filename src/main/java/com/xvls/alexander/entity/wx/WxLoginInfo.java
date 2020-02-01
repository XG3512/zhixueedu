package com.xvls.alexander.entity.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class WxLoginInfo implements Serializable {
    private String code;
    private UserInfo userInfo;
}
