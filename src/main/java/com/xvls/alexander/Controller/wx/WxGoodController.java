package com.xvls.alexander.Controller.wx;

import com.xvls.alexander.entity.wx.Good;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 点赞信息
 */
@RestController
@RequestMapping("/wx/good")
public class WxGoodController {

    /**
     * 对动态内容进行点赞,需要点赞信息
     * @return
     */
    @RequestMapping("add")
    public Object addGood(@RequestBody Good good, HttpServletRequest httpServletRequest){
        if(good==null||good.getUserId()==null||good.getGoodId()==null||good.getGoodTime()==null||good.getGoodType()==null){
            return WeChatResponseUtil.badArgumentValue();
        }
        /*判断点赞类型*/
        if(good.getGoodType().equals("A")){

        }else{

        }
        return null;
    }
}
