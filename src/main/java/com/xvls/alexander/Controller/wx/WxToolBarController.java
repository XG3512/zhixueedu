package com.xvls.alexander.Controller.wx;

import com.xvls.alexander.entity.wx.*;
import com.xvls.alexander.service.wx.CollectionService;
import com.xvls.alexander.service.wx.WxToolBarService;
import com.xvls.alexander.service.wx.WxV_historyService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
    @Autowired
    CollectionService collectionService;

    /**
     * 点赞增加
     * @param good
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("good/add")
    public Object addGood(@RequestBody Good good, HttpServletRequest httpServletRequest){
        if(good==null||good.getWxUserId()==null||good.getGoodId()==null||good.getGoodType()==null){
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
        if(good==null||good.getWxUserId()==null||good.getGoodId()==null||good.getGoodType()==null){
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

    /**
     * 添加收藏功能
     * @return
     */
    @RequestMapping("collection/add")
    public Object addCollection(@RequestParam("collectionType") String collectionType,
                                @RequestParam("collectionId") Integer collectionId,
                                @RequestParam("wxUserId") Integer wxUserId,
                                @RequestParam(value = "groupId",required = false) Integer groupId,
                                HttpServletRequest httpServletRequest){
        if(collectionType==null||collectionId==null||wxUserId==null){
            return WeChatResponseUtil.badArgument();
        }
        if(groupId==null){
            groupId=1;
        }
        WxCollection wxCollection = new WxCollection();
        wxCollection.setCollectionType(collectionType);
        wxCollection.setCollectionId(collectionId);
        wxCollection.setWxUserId(wxUserId);
        wxCollection.setGroupId(groupId);
        wxCollection.setCollectionDate(new Date());
        try{
            if(collectionType.equals("A")){//动态
                wxToolBarService.addArticleCollectionNum(collectionId);
                //向收藏记录中添加数据
                collectionService.addCollection(wxCollection);
            }else if(collectionType.equals("V")){//视频
                wxToolBarService.addVideoCollectionNum(collectionId);
                //添加记录
                collectionService.addCollection(wxCollection);
            }else if(collectionType.equals("N")){//公告
                return WeChatResponseUtil.fail(403,"未开发此功能");
            }else{
                return WeChatResponseUtil.badArgumentValue();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return WeChatResponseUtil.ok();
    }


    /**
     * 添加收藏功能
     * @return
     */
    @RequestMapping("collection/decrease")
    public Object decreaseCollection(@RequestParam("collectionType") String collectionType,
                                @RequestParam("collectionId") Integer collectionId,
                                @RequestParam("wxUserId") Integer wxUserId,
                                HttpServletRequest httpServletRequest){
        if(collectionType==null||collectionId==null||wxUserId==null){
            return WeChatResponseUtil.badArgument();
        }
        WxCollection wxCollection = new WxCollection();
        wxCollection.setCollectionType(collectionType);
        wxCollection.setCollectionId(collectionId);
        wxCollection.setWxUserId(wxUserId);
        wxCollection.setCollectionDate(new Date());
        try{
            if(collectionType.equals("A")){//动态
                wxToolBarService.decreaseArticleCollectionNum(collectionId);
                //删除收藏记录
                collectionService.deleteCollection(collectionType,collectionId,wxUserId);
            }else if(collectionType.equals("V")){//视频
                wxToolBarService.decreaseVideoCollectionNum(collectionId);
                //删除收藏记录
                collectionService.deleteCollection(collectionType,collectionId,wxUserId);
            }else if(collectionType.equals("N")){//公告
                return WeChatResponseUtil.fail(403,"未开发此功能");
            }else{
                return WeChatResponseUtil.badArgumentValue();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return WeChatResponseUtil.ok();
    }

    // TODO: 2020/2/14 关注

    /**
     * 关注学校
     * @param wxUserId
     * @param schoolId
     * @return
     */
    @RequestMapping("addFollowSchool")
    public Object addFollowSchool(@RequestParam("wxUserId")Integer wxUserId,@RequestParam("schoolId")Integer schoolId){
        if(wxUserId==null||schoolId==null){
            return WeChatResponseUtil.badArgument();
        }
        Follow_school follow_school = new Follow_school();
        follow_school.setWxUserId(wxUserId);
        follow_school.setSchoolId(schoolId);
        follow_school.setFollowSchoolDate(new Date());
        try {
            wxToolBarService.addFollowSchool(follow_school);
        } catch (Exception e) {
            e.printStackTrace();
            return WeChatResponseUtil.fail(-1,"关注失败");
        }
        return WeChatResponseUtil.ok();
    }

    /**
     * 取消关注学校
     * @param wxUserId
     * @param schoolId
     * @return
     */
    @RequestMapping("cancelFollowSchool")
    public Object cancelFollowSchool(@RequestParam("wxUserId")Integer wxUserId,@RequestParam("schoolId")Integer schoolId){
        if(wxUserId==null||schoolId==null){
            return WeChatResponseUtil.badArgument();
        }
        Follow_school follow_school = new Follow_school();
        follow_school.setWxUserId(wxUserId);
        follow_school.setSchoolId(schoolId);
        try {
            wxToolBarService.cancelFollowSchool(follow_school);
        } catch (Exception e) {
            e.printStackTrace();
            return WeChatResponseUtil.fail(-1,"取消关注失败");
        }
        return WeChatResponseUtil.ok();
    }

    /**
     * 关注老师
     * @param wxUserId
     * @param teacherId
     * @return
     */
    @RequestMapping("addFollowTeacher")
    public Object addFollowTeacher(@RequestParam("wxUserId")Integer wxUserId,@RequestParam("teacherId")Integer teacherId){
        if(wxUserId==null||teacherId==null){
            return WeChatResponseUtil.badArgument();
        }
        Follow_teacher follow_teacher = new Follow_teacher();
        follow_teacher.setWxUserId(wxUserId);
        follow_teacher.setTeacherId(teacherId);
        follow_teacher.setFollowTeacherDate(new Date());
        try {
            wxToolBarService.addFollowTeacher(follow_teacher);
        } catch (Exception e) {
            e.printStackTrace();
            return WeChatResponseUtil.fail(-1,"关注失败");
        }
        return WeChatResponseUtil.ok();
    }

    /**
     * 取消关注教师
     * @param wxUserId
     * @param teacherId
     * @return
     */
    @RequestMapping("cancelFollowTeacher")
    public Object cancelFollowTeacher(@RequestParam("wxUserId")Integer wxUserId,@RequestParam("teacherId")Integer teacherId){
        if(wxUserId==null||teacherId==null){
            return WeChatResponseUtil.badArgument();
        }
        Follow_teacher follow_teacher = new Follow_teacher();
        follow_teacher.setWxUserId(wxUserId);
        follow_teacher.setTeacherId(teacherId);
        try {
            wxToolBarService.cancelFollowTeacher(follow_teacher);
        } catch (Exception e) {
            e.printStackTrace();
            return WeChatResponseUtil.fail(-1,"取消关注失败");
        }
        return WeChatResponseUtil.ok();
    }
}
