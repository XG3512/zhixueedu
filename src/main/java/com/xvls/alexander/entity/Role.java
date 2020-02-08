package com.xvls.alexander.entity;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

    private Integer roleId;
    private String roleName;

    private Set<Permission> permissions  = Sets.newHashSet();//权限列表
}
