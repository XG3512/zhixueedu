package com.xvls.alexander.dao;

import com.xvls.alexander.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    User queryUserByName(String name);
}
