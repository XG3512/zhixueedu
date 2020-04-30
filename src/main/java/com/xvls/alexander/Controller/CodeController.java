package com.xvls.alexander.Controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import com.xvls.alexander.utils.Constants;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.SystemResponse;
import com.xvls.alexander.utils.VerifyCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码
 */
//@CrossOrigin(allowCredentials ="true")//设置是否允许客户端发送cookie信息。默认是false
@CrossOrigin
@RestController
@RequestMapping("/system/code")
public class CodeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeController.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取验证码图片和文本(验证码文本会保存在HttpSession中)
     * @param body
     * @param response
     * @throws IOException
     */
    @RequestMapping("getCode")
    public Object getCode(@RequestBody String body, HttpServletResponse response) throws IOException {
        String verifyCodeId = null;
        try {
            verifyCodeId = JacksonUtil.parseString(body,"verifyCodeId");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return SystemResponse.fail(-1,e.getMessage());
        }
        if(verifyCodeId == null){
            System.out.println(verifyCodeId);
            return SystemResponse.badArgument();
        }else{
            //设置页面不缓存
            //response.setHeader("Pragma", "no-cache");
            //response.setHeader("Cache-Control", "no-cache");
            //response.setDateHeader("Expires", 0);
            //String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_ALL_MIXED, 4, null);
            //定义图形验证码的长和宽
            ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 6);
            String verifyCode = captcha.getCode();
            //将验证码放到HttpSession里面
            //request.getSession().setAttribute(Constants.VALIDATE_CODE, verifyCode);
            redisTemplate.opsForValue().set(verifyCodeId,verifyCode,30, TimeUnit.MINUTES);
            LOGGER.info("本次生成的验证码为[" + verifyCode + "],已存放到redis中,verifyCodeId为："+verifyCodeId);
            //设置输出的内容的类型为JPEG图像
            //response.setContentType("image/jpeg");
            //BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 116, 36, 5, true, new Color(249,205,173), null, null);
            //写给浏览器
            //ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());

            captcha.write(response.getOutputStream());
            response.getOutputStream().close();
            return null;
        }

    }
}
