package com.xvls.alexander.Controller;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.SignIn_info;
import com.xvls.alexander.entity.SignIn_user;
import com.xvls.alexander.service.SignInService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.RestResponse;
import com.xvls.alexander.utils.SystemResponse;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 签到
 */
@RestController
@RequestMapping("/SignIn")
public class SignInController {

    @Autowired
    SignInService signInService;

    @RequestMapping("GetSignInImage")
    public Object GetSignInImage(@RequestBody String body){
        SignIn_info signIn_info = null;
        try {
            signIn_info = JacksonUtil.parseObject(body,SignIn_info.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return SystemResponse.fail(-1,"json转换出错");
        }
        if(signIn_info==null || signIn_info.getStartTime()==null || signIn_info.getEndTime()==null || signIn_info.getTcId()==null || signIn_info.getTeacherId()==null){
            return SystemResponse.badArgument();
        }
        String url = null;
        try {
            url = signInService.GetSignInImage(signIn_info);
        } catch (Exception e) {
            e.printStackTrace();
            return SystemResponse.fail(-1,e.getMessage());
        }
        Map result = Maps.newHashMap();
        result.put("url",url);
        result.put("startTime",signIn_info.getStartTime());
        result.put("endTime",signIn_info.getEndTime());
        return SystemResponse.ok(result);
    }

    /**
     * 扫描二维码进行签到,将siiId,userId,siTime传入
     * @param signIn_user
     * @return
     */
    @RequestMapping("SignIn")
    public Object SignIn(@RequestBody SignIn_user signIn_user,HttpServletRequest httpServletRequest){
        System.out.println(signIn_user);
        if(signIn_user==null){
            return WeChatResponseUtil.fail(-1,"签到失败");
        }
        Integer count = signInService.selectSignIn_user(signIn_user);
        if(count > 0){
            Map result = Maps.newHashMap();
            result.put("result","请勿重复签到");
            return WeChatResponseUtil.ok(result);
        }else{
            signInService.SignIn(signIn_user);
            Map result = Maps.newHashMap();
            result.put("result","签到成功");
            return WeChatResponseUtil.ok(result);
        }
    }
}
