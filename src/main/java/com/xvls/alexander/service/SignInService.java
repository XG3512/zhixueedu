package com.xvls.alexander.service;

import com.xvls.alexander.entity.SignIn_info;
import com.xvls.alexander.entity.SignIn_user;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface SignInService {

    /**
     * 生成签到图片
     * @param signIn_info
     * @return
     */
    String GetSignInImage(SignIn_info signIn_info) throws IOException, NoSuchAlgorithmException;

    /**
     * 用户签到
     */
    void SignIn(SignIn_user signIn_user);
}
