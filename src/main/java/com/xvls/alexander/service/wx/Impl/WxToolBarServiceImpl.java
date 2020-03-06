package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.WxFollow_schoolMapper;
import com.xvls.alexander.dao.WxFollow_teacherMapper;
import com.xvls.alexander.dao.WxGoodMapper;
import com.xvls.alexander.entity.wx.Follow_school;
import com.xvls.alexander.entity.wx.Follow_teacher;
import com.xvls.alexander.entity.wx.Good;
import com.xvls.alexander.entity.wx.SchoolList;
import com.xvls.alexander.service.wx.*;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxToolBarServiceImpl implements WxToolBarService {
    @Autowired
    WxGoodMapper wxGoodMapper;
    @Autowired
    WxArticleService wxArticleService;
    @Autowired
    WxVideoService wxVideoService;
    @Autowired
    WxNoticeService wxNoticeService;
    @Autowired
    WxFollow_schoolMapper wxFollow_schoolMapper;
    @Autowired
    WxFollow_teacherMapper wxFollow_teacherMapper;
    @Autowired
    UsersService usersService;
    @Autowired
    WxSchoolService wxSchoolService;

    /**
     * 对动态进行点赞 , 在前端进行判断是否已经点赞
     * @param good
     */
    @Override
    public Object addArticleGoodNum(Good good) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_type",good.getGoodType())
                .eq("good_id",good.getGoodId())
                .eq("wx_user_id",good.getWxUserId());
        Integer count = good.selectCount(queryWrapper);
        if(count>0){
            return WeChatResponseUtil.fail(-1,"已经点过赞了~");
        }else{
            good.insert();
            wxArticleService.addArticleGoodNum(good.getGoodId());
            return WeChatResponseUtil.ok();
        }
    }

    /**
     * 对视频进行点赞
     * @param good
     */
    @Override
    public Object addVideoGoodNum(Good good) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_type",good.getGoodType())
                .eq("good_id",good.getGoodId())
                .eq("wx_user_id",good.getWxUserId());
        Integer count = good.selectCount(queryWrapper);
        if(count>0){
            return WeChatResponseUtil.fail(-1,"已经点过赞了~");
        }else{
            good.insert();
            wxVideoService.addVideoGoodNum(good.getGoodId());
            return WeChatResponseUtil.ok();
        }
    }

    /**
     * 公告点赞数增加
     * @param good
     */
    @Override
    public Object addNoticeGoodNum(Good good) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_type",good.getGoodType())
                .eq("good_id",good.getGoodId())
                .eq("wx_user_id",good.getWxUserId());
        Integer count = good.selectCount(queryWrapper);
        if(count>0){
            return WeChatResponseUtil.fail(-1,"已经点过赞了~");
        }else{
            good.insert();
            wxNoticeService.addNoticeGoodNum(good.getGoodId());
            return WeChatResponseUtil.ok();
        }
    }

    /**
     * 动态取消点赞 , 在前端进行判断是否已经点赞
     * @param good
     */
    @Override
    public Object decreaseArticleGoodNum(Good good) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_type",good.getGoodType())
                .eq("good_id",good.getGoodId())
                .eq("wx_user_id",good.getWxUserId());
        Integer count = good.selectCount(queryWrapper);
        if(count==0){
            return WeChatResponseUtil.fail(-1,"失败~");
        }else{
            wxArticleService.decreaseArticleGoodNum(good.getGoodId());
            boolean delete = this.deleteGood(good);
            return WeChatResponseUtil.ok();
        }
    }

    /**
     * 视频点赞数减少
     * @param good
     */
    @Override
    public Object decreaseVideoGoodNum(Good good) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_type",good.getGoodType())
                .eq("good_id",good.getGoodId())
                .eq("wx_user_id",good.getWxUserId());
        Integer count = good.selectCount(queryWrapper);
        if(count==0){
            return WeChatResponseUtil.fail(-1,"失败~");
        }else{
            wxVideoService.decreaseVideoGoodNum(good.getGoodId());
            boolean delete = this.deleteGood(good);
            return WeChatResponseUtil.ok();
        }
    }

    /**
     * 通知点赞数减少
     * @param good
     */
    @Override
    public Object decreaseNoticeGoodNum(Good good) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_type",good.getGoodType())
                .eq("good_id",good.getGoodId())
                .eq("wx_user_id",good.getWxUserId());
        Integer count = good.selectCount(queryWrapper);
        if(count==0){
            return WeChatResponseUtil.fail(-1,"失败~");
        }else{
            wxNoticeService.decreaseNoticeGoodNum(good.getGoodId());
            boolean delete = this.deleteGood(good);
            return WeChatResponseUtil.ok();
        }
    }

    /**
     * 动态评论数增加
     * @param good
     */
    @Override
    public void addArticleCommentNum(Good good) {
        wxArticleService.addArticleCommentNum(good.getGoodId());
    }

    /**
     * 视频评论数增加
     * @param good
     */
    @Override
    public void addVideoCommentNum(Good good) {
        wxVideoService.addVideoCommentNum(good.getGoodId());
    }

    /**
     * 动态评论数减少
     * @param good
     */
    @Override
    public void decreaseArticleCommentNum(Good good) {
        wxArticleService.decreaseArticleCommentNum(good.getGoodId());
    }

    /**
     * 视频评论数减少
     * @param good
     */
    @Override
    public void decreaseVideoCommentNum(Good good) {
        wxVideoService.decreaseVideoCommentNum(good.getGoodId());
    }

    /**
     * 动态阅读量增加
     * @param id
     */
    @Override
    public void addArticleReadNum(Integer id) {
        wxArticleService.addArticleReadNum(id);
    }

    /**
     * 视频阅读量增加
     * @param id
     */
    @Override
    public void addVideoPlayNum(Integer id) {
        wxVideoService.addVideoPlayNum(id);
    }

    /**
     * 通知阅读量增加
     * @param id
     */
    @Override
    public void addNoticeReadNum(Integer id) {
        wxNoticeService.addNoticeReadNum(id);
    }


    /**
     * 动态收藏数增加
     * @param id
     */
    @Override
    public void addArticleCollectionNum(Integer id) {
        wxArticleService.addArticleCollectionNum(id);
    }

    @Override
    public void decreaseArticleCollectionNum(Integer id) {
        wxArticleService.decreaseArticleCollectionNum(id);
    }

    @Override
    public void addVideoCollectionNum(Integer id) {
        wxVideoService.addVideoCollectionNum(id);
    }

    @Override
    public void decreaseVideoCollectionNum(Integer id) {
        wxVideoService.decreaseVideoCollectionNum(id);
    }

    @Override
    public boolean deleteGood(Good good) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_type",good.getGoodType())
                .eq("good_id",good.getGoodId())
                .eq("wx_user_id",good.getWxUserId());
        return good.delete(queryWrapper);
    }

    /**
     * 关注学校
     * @param follow_school
     */
    @Override
    public void addFollowSchool(Follow_school follow_school) {
        wxFollow_schoolMapper.insert(follow_school);
        wxSchoolService.addSchoolFansNum(follow_school.getSchoolId());
    }

    /**
     * 取消关注
     * @param follow_school
     */
    @Override
    public void cancelFollowSchool(Follow_school follow_school) {
        QueryWrapper<Follow_school> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wx_user_id",follow_school.getWxUserId())
                .eq("school_id",follow_school.getSchoolId());
        wxFollow_schoolMapper.delete(queryWrapper);
        wxSchoolService.decreaseSchoolFansNum(follow_school.getSchoolId());
    }

    /**
     * 关注老师
     * @param follow_teacher
     */
    @Override
    public void addFollowTeacher(Follow_teacher follow_teacher) {
        wxFollow_teacherMapper.insert(follow_teacher);
        usersService.addFansNum(follow_teacher.getTeacherId());
    }

    /**
     * 取消教师的关注
     * @param follow_teacher
     */
    @Override
    public void cancelFollowTeacher(Follow_teacher follow_teacher) {
        QueryWrapper<Follow_teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wx_user_id",follow_teacher.getWxUserId())
                .eq("teacher_id",follow_teacher.getTeacherId());
        wxFollow_teacherMapper.delete(queryWrapper);
        usersService.decreaseFansNum(follow_teacher.getTeacherId());
    }

    /**
     * 通过 wxUserId 获得用户关注学校数量
     * @param wxUserId
     * @return
     */
    @Override
    public Integer getFollowSchoolCount(Integer wxUserId) {
        QueryWrapper<Follow_school> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wx_user_id",wxUserId);
        return wxFollow_schoolMapper.selectCount(queryWrapper);
    }

}
