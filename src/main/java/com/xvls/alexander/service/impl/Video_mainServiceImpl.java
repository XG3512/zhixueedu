package com.xvls.alexander.service.impl;

import com.xvls.alexander.dao.UsersMapper;
import com.xvls.alexander.dao.WxVideoMapper;
import com.xvls.alexander.entity.wx.Users;
import com.xvls.alexander.entity.wx.Video_main;
import com.xvls.alexander.service.UserService;
import com.xvls.alexander.service.Video_mainService;
import com.xvls.alexander.service.wx.UsersService;
import com.xvls.alexander.service.wx.WxVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Video_mainServiceImpl implements Video_mainService {

    @Autowired
    WxVideoMapper wxVideoMapper;
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    WxVideoService wxVideoService;

    /**
     * 上传 视频主页 信息
     * @param video_main
     */
    @Override
    public Integer uploadVideoMain(Video_main video_main) {
        Users users = usersMapper.selectById(video_main.getTeacherId());
        video_main.setSchoolId(users.getSchoolId());
        video_main.setHeatOfVideo(0.0);
        int i = wxVideoMapper.insert(video_main);
        return video_main.getVideoMainId();
    }
}
