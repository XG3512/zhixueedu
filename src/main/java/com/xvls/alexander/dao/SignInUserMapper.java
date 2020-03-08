package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.SignIn_user;
import com.xvls.alexander.entity.SignIn_userList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignInUserMapper extends BaseMapper<SignIn_user> {

    /**
     * 通过 siiId 删除对应的SignIn_user
     * @param siiId
     */
    void deleteSignIn_user(Integer siiId);

    /**
     * 通过 siiId 获得 签到学生的列表
     * @param pageInfo
     * @param siiId
     * @return
     */
    List<SignIn_userList> getSignIn_userList(PageInfo pageInfo, Integer siiId);
}
