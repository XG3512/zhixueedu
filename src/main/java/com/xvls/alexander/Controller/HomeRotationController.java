package com.xvls.alexander.Controller;

import com.google.common.collect.Maps;
import com.xvls.alexander.annotation.SysLog;
import com.xvls.alexander.entity.wx.WxHomeRotation;
import com.xvls.alexander.service.wx.WxHomeRotationService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.SystemResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/system/homeRotation")
public class HomeRotationController {

    @Autowired
    WxHomeRotationService wxHomeRotationService;

    /**
     * 获得轮播图信息
     * @return
     */
    @RequiresPermissions("home_rotation:select")
    @RequestMapping("getAllHomeRotation")
    public Object getAllHomeRotation(){
        List<WxHomeRotation> homeRotations = wxHomeRotationService.getAllHomeRotation();
        Map result = Maps.newHashMap();
        result.put("homeRotations",homeRotations);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 homeRotationId，belongType，belongId 更新轮播图信息
     * @param body
     * @return
     */
    @RequiresPermissions("home_rotation:update")
    @RequestMapping("updateHomeRotationInfo")
    public Object updateHomeRotationInfo(@RequestBody String body){
        Integer homeRotationId = null;
        String belongType = null;
        Integer belongId = null;
        try {
            homeRotationId = JacksonUtil.parseInteger(body,"homeRotationId");
            belongType = JacksonUtil.parseString(body,"belongType");
            belongId = JacksonUtil.parseInteger(body,"belongId");
            if(homeRotationId == null || belongType == null || belongId == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        wxHomeRotationService.updateHomeRotationInfo(homeRotationId,belongType,belongId);
        return SystemResponse.ok();
    }

}
