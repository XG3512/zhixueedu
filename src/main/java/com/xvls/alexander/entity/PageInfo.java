package com.xvls.alexander.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo implements Serializable {

    private Integer pageNum;//页数
    private Integer pageSize;//每页显示的条数
}
