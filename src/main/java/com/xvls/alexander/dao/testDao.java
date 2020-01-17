package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.User;

import java.util.List;

public interface testDao extends BaseMapper<User> {

    List<User> selectAll();

}
