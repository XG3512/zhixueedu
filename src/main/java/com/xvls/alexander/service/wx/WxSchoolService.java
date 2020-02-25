package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.School;
import com.xvls.alexander.entity.wx.Province;
import com.xvls.alexander.entity.wx.SchoolList;

import java.util.List;

public interface WxSchoolService {

    /**
     * 通过学校schoolId，查询学校信息
     * @param schoolId
     * @return
     */
    School getSchoolInfo(Integer schoolId,Integer wxUserId);

    List<SchoolList> getSchoolList();

    List<Province> getSchoolListByProvince(Integer wxUserId);

    /**
     * 更新学校的背景图
     * @param schoolId
     * @param file_url
     */
    void updateSchoolBackground(Integer schoolId,String file_url);

    /**
     * 更新学校头像
     * @param schoolId
     * @param file_url
     */
    void updateSchoolHead(Integer schoolId,String file_url);

    /**
     * 关注部分的默认学校
     * @return
     */
    List<SchoolList> getDefaultSchoolList();

    /**
     * 通过 wxUserId 获得关注学校列表
     * @param wxUserId
     * @return
     */
    List<SchoolList> getFollowSchoolListById(Integer wxUserId);

    /**
     * 添加粉丝数
     * @param schoolId
     */
    void addSchoolFansNum(Integer schoolId);

    /**
     * 减少粉丝数
     * @param schoolId
     */
    void decreaseSchoolFansNum(Integer schoolId);

    /**
     * 更新学校的 点赞数和收藏数
     * @param schoolId
     */
    void updateSchoolGoodCollectionNum(Integer schoolId);
}
