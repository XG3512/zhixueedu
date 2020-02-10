package com.xvls.alexander.Controller.wx;

import com.xvls.alexander.entity.wx.Good;
import com.xvls.alexander.entity.wx.V_history;
import com.xvls.alexander.service.wx.WxToolBarService;
import com.xvls.alexander.service.wx.WxV_historyService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 点赞、评论、浏览、收藏信息的更新
 */
@RestController
@RequestMapping("/wx/toolbar")
public class WxToolBarController {

    @Autowired
    WxToolBarService wxToolBarService;
    @Autowired
    WxV_historyService wxV_historyService;

    /**
     * 点赞增加
     * @param good
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("good/add")
    public Object addGood(@RequestBody Good good, HttpServletRequest httpServletRequest){
        if(good==null||good.getUserId()==null||good.getGoodId()==null||good.getGoodType()==null){
            return WeChatResponseUtil.badArgumentValue();
        }
        /*判断点赞类型*/
        try {
            if(good.getGoodType().equals("A")){//article
                wxToolBarService.addArticleGoodNum(good);
            }else if(good.getGoodType().equals("V")){//video
                wxToolBarService.addVideoGoodNum(good);
            }else if(good.getGoodType().equals("N")){//notice
                wxToolBarService.addNoticeGoodNum(good);
            }else{
                System.out.println("参数值未能匹配");
                return WeChatResponseUtil.badArgumentValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return WeChatResponseUtil.updatedDataFailed();
        }
        return WeChatResponseUtil.ok();
    }

    /**
     * 点赞减少
     * @param good
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("good/decrease")
    public Object decreaseGood(@RequestBody Good good,HttpServletRequest httpServletRequest){
        if(good==null||good.getUserId()==null||good.getGoodId()==null||good.getGoodType()==null){
            return WeChatResponseUtil.badArgumentValue();
        }
        /*判断点赞类型*/
        try {
            if(good.getGoodType().equals("A")){//article
                wxToolBarService.decreaseArticleGoodNum(good);
            }else if(good.getGoodType().equals('V')){//video
                wxToolBarService.decreaseVideoGoodNum(good);
            }else if(good.getGoodType().equals("N")){//notice
                wxToolBarService.decreaseNoticeGoodNum(good);
            }else{
                System.out.println("参数值未能匹配");
                return WeChatResponseUtil.badArgumentValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return WeChatResponseUtil.updatedDataFailed();
        }
        return WeChatResponseUtil.ok();
    }

    /**
     * 阅读量增加
     * @param type
     * @param id
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("readNum/add")
    public Object addReadNum(@RequestParam("type") String type,@RequestParam("id") Integer id,HttpServletRequest httpServletRequest){
        if(type==null || id==null){
            return WeChatResponseUtil.badArgumentValue();
        }
        /*判断点赞类型*/
        try {
            if(type.equals("A")){//article
                wxToolBarService.addArticleReadNum(id);
            }else if(type.equals("V")){//video
                // TODO: 2020/2/9 向观看历史中添加记录
                V_history v_history = JacksonUtil.parseObject(httpServletRequest.getParameter("v_history"),V_history.class);
                v_history.setVHistoryId(id);
                System.out.println(v_history);
                if(v_history==null||v_history.getEpisodeId()==null||v_history.getUserId()==null||v_history.getVHistoryId()==null||v_history.getWatchTo()==null||v_history.getWatchDate()==null){
                    return WeChatResponseUtil.badArgumentValue();
                }
                wxV_historyService.addV_history(v_history);
                wxToolBarService.addVideoPlayNum(id);
            }else if(type.equals("N")){//notice
                wxToolBarService.addNoticeReadNum(id);
            }
            else{
                System.out.println("参数值未能匹配");
                return WeChatResponseUtil.badArgumentValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return WeChatResponseUtil.fail();
        }
        return WeChatResponseUtil.ok();
    }


}
