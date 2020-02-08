package com.xvls.alexander.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission implements Serializable {

    private Integer permissionId;
    private String permissionName;
    private String permissionCode;
}
