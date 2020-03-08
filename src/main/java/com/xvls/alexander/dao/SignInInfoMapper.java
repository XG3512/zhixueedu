package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.SignIn_info;
import com.xvls.alexander.entity.SignIn_infoList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignInInfoMapper extends BaseMapper<SignIn_info> {

    /**
     * 通过 pageInfo，userId 获得签到信息列表
     * @param pageInfo
     * @param userId
     * @return
     */
    List<SignIn_infoList> getSignInInfoList(PageInfo pageInfo, Integer userId);

    /**
     * 通过 siiId 删除对应的SignIn_info
     * @param siiId
     */
    void deleteSignIn_info(Integer siiId);
}
