package com.xvls.alexander.Controller.wx;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.xvls.alexander.dao.WxCollectionMapper;
import com.xvls.alexander.dao.WxFollow_schoolMapper;
import com.xvls.alexander.dao.WxFollow_teacherMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.*;
import com.xvls.alexander.service.wx.*;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    WxArticleService wxArticleService;
    @Autowired
    WxVideoService wxVideoService;
    @Autowired
    WxCollectionMapper wxCollectionMapper;
    @Autowired
    WxFollow_schoolMapper wxFollow_schoolMapper;
    @Autowired
    WxFollow_teacherMapper wxFollow_teacherMapper;

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
        if(good.getGoodTime()==null){
            good.setGoodTime(new Date());
        }
        /*判断点赞类型*/
        try {
            if(good.getGoodType().equals("A")){//article
                return wxToolBarService.addArticleGoodNum(good);
            }else if(good.getGoodType().equals("V")){//video
                return wxToolBarService.addVideoGoodNum(good);
            }else if(good.getGoodType().equals("N")){//notice
                return wxToolBarService.addNoticeGoodNum(good);
            }else{
                System.out.println("参数值未能匹配");
                return WeChatResponseUtil.badArgumentValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return WeChatResponseUtil.updatedDataFailed();
        }
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
                return wxToolBarService.decreaseArticleGoodNum(good);
            }else if(good.getGoodType().equals("V")){//video
                return wxToolBarService.decreaseVideoGoodNum(good);
            }else if(good.getGoodType().equals("N")){//notice
                return wxToolBarService.decreaseNoticeGoodNum(good);
            }else{
                System.out.println("参数值未能匹配");
                return WeChatResponseUtil.badArgumentValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return WeChatResponseUtil.updatedDataFailed();
        }
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
            } else{
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
            QueryWrapper<WxCollection> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("collection_type",wxCollection.getCollectionType())
                    .eq("collection_id",wxCollection.getCollectionId())
                    .eq("wx_user_id",wxCollection.getWxUserId());
            Integer count = wxCollection.selectCount(queryWrapper);
            if(count>0){

                return WeChatResponseUtil.fail(-1,"收藏错误~");

            } else if(collectionType.equals("A")){//动态
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
     * 取消收藏功能
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
            QueryWrapper<WxCollection> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("collection_type",wxCollection.getCollectionType())
                    .eq("collection_id",wxCollection.getCollectionId())
                    .eq("wx_user_id",wxCollection.getWxUserId());
            Integer count = wxCollection.selectCount(queryWrapper);
            if(count==0){

                return WeChatResponseUtil.fail(-1,"取消收藏错误");

            } else if(collectionType.equals("A")){//动态
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

    /**
     * 通过 wxUserId,collectionType,pageInfo 获得 收藏 信息列表（ A , V ）
     * @param body
     * @return
     */
    @RequestMapping("collection/getCollectionList")
    public Object getCollectionList(@RequestBody String body){

        Integer wxUserId = null;
        String collectionType = null;
        PageInfo pageInfo = null;

        try {
            wxUserId = JacksonUtil.parseInteger(body,"wxUserId");
            collectionType = JacksonUtil.parseString(body,"collectionType");
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
        } catch (Exception e) {
            return WeChatResponseUtil.badArgument();
        }
        if(wxUserId == null || collectionType == null || pageInfo == null){
            return WeChatResponseUtil.badArgumentValue();
        }

        if(collectionType.equals("A")){
            List<Article> articleCollectionList = wxArticleService.getArticleCollectionList(wxUserId, pageInfo);
            Map result = Maps.newHashMap();
            result.put("articleCollectionList",articleCollectionList);
            return WeChatResponseUtil.ok(result);
        }else if (collectionType.equals("V")){
            List<Video_main> videoCollectionList = wxVideoService.getVideoCollectionList(wxUserId, pageInfo);
            Map result = Maps.newHashMap();
            result.put("videoCollectionList",videoCollectionList);

            return WeChatResponseUtil.ok(result);
        }else if (collectionType.equals("N")){
            return WeChatResponseUtil.fail(403,"未开发此功能");
        }else{
            return WeChatResponseUtil.badArgumentValue();
        }
    }

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
            QueryWrapper<Follow_school> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("wx_user_id",follow_school.getWxUserId())
                    .eq("school_id",follow_school.getSchoolId());
            Integer count = wxFollow_schoolMapper.selectCount(queryWrapper);
            if(count > 0){
                return WeChatResponseUtil.fail(-1,"关注错误~");
            }
            else{
                wxToolBarService.addFollowSchool(follow_school);
            }

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
            QueryWrapper<Follow_school> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("wx_user_id",follow_school.getWxUserId())
                    .eq("school_id",follow_school.getSchoolId());
            Integer count = wxFollow_schoolMapper.selectCount(queryWrapper);
            if(count == 0){
                return WeChatResponseUtil.fail(-1,"取消关注错误~");
            }
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
            QueryWrapper<Follow_teacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("wx_user_id",wxUserId)
                    .eq("teacher_id",teacherId);
            Integer count = wxFollow_teacherMapper.selectCount(queryWrapper);
            if(count > 0){
                return WeChatResponseUtil.fail(-1,"关注错误~");
            }
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
            QueryWrapper<Follow_teacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("wx_user_id",wxUserId)
                    .eq("teacher_id",teacherId);
            Integer count = wxFollow_teacherMapper.selectCount(queryWrapper);
            if(count == 0){
                return WeChatResponseUtil.fail(-1,"取消关注错误~");
            }
            wxToolBarService.cancelFollowTeacher(follow_teacher);
        } catch (Exception e) {
            e.printStackTrace();
            return WeChatResponseUtil.fail(-1,"取消关注失败");
        }
        return WeChatResponseUtil.ok();
    }

    /**
     * 通过 wxUserId goodType 和 pageInfo 获得点赞列表
     * @param body
     * @return
     */
    @RequestMapping("getGoodListById")
    public Object getGoodListById(@RequestBody String body){

        String goodType = null;
        Integer wxUserId = null;
        PageInfo pageInfo = null;

        try {
            goodType = JacksonUtil.parseString(body,"goodType");
            wxUserId = JacksonUtil.parseInteger(body,"wxUserId");
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
        } catch (Exception e) {
            return WeChatResponseUtil.badArgument();
        }
        if(wxUserId == null || pageInfo == null || goodType == null){
            return WeChatResponseUtil.badArgumentValue();
        }

        if(goodType.equals("A")){
            List<Article> articleList = wxArticleService.getArticleGoodList(wxUserId, pageInfo);
            Map result = Maps.newHashMap();
            result.put("articleList",articleList);
            return WeChatResponseUtil.ok(result);
        }else if (goodType.equals("V")){
            List<Video_main> videoGoodList = wxVideoService.getVideoGoodList(wxUserId, pageInfo);
            Map result = Maps.newHashMap();
            result.put("videoGoodList",videoGoodList);
            return WeChatResponseUtil.ok(result);
        }else if (goodType.equals("N")){
            return WeChatResponseUtil.fail(403,"未开发此功能");
        }else{
            return WeChatResponseUtil.badArgumentValue();
        }
    }

    /**
     * 通过 wxUserId 获得用户关注学校的数量
     * @return
     */
    @RequestMapping("getFollowSchoolCount")
    public Object getFollowSchoolCount(@RequestParam("wxUserId") Integer wxUserId){

        if(wxUserId == null){
            return WeChatResponseUtil.badArgumentValue();
        }

        Integer followSchoolCount = wxToolBarService.getFollowSchoolCount(wxUserId);
        Map result = Maps.newHashMap();
        result.put("followSchoolCount",followSchoolCount);

        return WeChatResponseUtil.ok(result);
    }
}
