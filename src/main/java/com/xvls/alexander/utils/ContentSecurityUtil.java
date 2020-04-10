package com.xvls.alexander.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import com.xvls.alexander.entity.AccessTokenResult;
import com.xvls.alexander.entity.MsgSecCheckResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 使用微信官方内容安全api，进行内容安全监测
 */
@Component
public class ContentSecurityUtil {

    @Value("${xvls_weixin.appid}")
    private String appid;
    @Value("${xvls_weixin.secret}")
    private String secret;

    private static String grant_type = "client_credential";

    /**
     * 获取 accessToken
     */
    public AccessTokenResult getAccessToken(){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("grant_type",grant_type);
        paramMap.put("appid",appid);
        paramMap.put("secret",secret);
        //String params = "grant_type="+grant_type+"&appid="+appid+"&secret="+secret;
        String result = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token", paramMap);
        Gson gson = new Gson();
        AccessTokenResult accessTokenResult = gson.fromJson(result, AccessTokenResult.class);
        //System.out.println(accessTokenResult);
        return accessTokenResult;
    }

    /**
     * 检查一段文本是否含有违法违规内容。
     * @param content
     * @return
     */
    public Object msgSecCheck(String content){
        AccessTokenResult accessTokenResult = this.getAccessToken();
        if(accessTokenResult.getAccess_token()!=null){
            String url = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token="+accessTokenResult.getAccess_token();
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("content", content);
            String result = HttpRequest.post(url)
                    .body(JacksonUtil.to(paramMap))
                    .execute().body();
            Gson gson = new Gson();
            MsgSecCheckResult msgSecCheckResult = gson.fromJson(result, MsgSecCheckResult.class);
            return SystemResponse.ok(msgSecCheckResult);
        }else{
            System.out.println("accessToken获取错误："+accessTokenResult);
            return SystemResponse.fail(accessTokenResult.getErrcode(),accessTokenResult.getErrmsg());
        }
    }
}
