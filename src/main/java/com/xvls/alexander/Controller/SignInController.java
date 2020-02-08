package com.xvls.alexander.Controller;

import com.xvls.alexander.entity.SignIn_info;
import com.xvls.alexander.entity.SignIn_user;
import com.xvls.alexander.service.SignInService;
import com.xvls.alexander.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 签到
 */
@RestController
@RequestMapping("/SignIn")
public class SignInController {

    @Autowired
    SignInService signInService;

    @RequestMapping("GetSignInImage")
    public RestResponse GetSignInImage(@RequestBody SignIn_info signIn_info,HttpServletRequest httpServletRequest){
        System.out.println(signIn_info);
        String url = null;
        try {
            url = signInService.GetSignInImage(signIn_info);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.failure(e.toString());
        }
        return RestResponse.success().setData(url);
    }

    /**
     * 扫描二维码进行签到,将siiId,userId,siTime传入
     * @param signIn_user
     * @return
     */
    @RequestMapping("SignIn")
    public RestResponse SignIn(@RequestBody SignIn_user signIn_user,HttpServletRequest httpServletRequest){
        System.out.println(signIn_user);
        if(signIn_user==null){
            return RestResponse.failure("签到失败");
        }
        signInService.SignIn(signIn_user);
        return RestResponse.success("签到成功");
    }
}
