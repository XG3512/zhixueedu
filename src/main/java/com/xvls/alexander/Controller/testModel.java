package com.xvls.alexander.Controller;

import com.google.gson.Gson;
import com.xvls.alexander.entity.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试
 */
@Controller
@RequestMapping("/testModel")
public class testModel {

    @RequestMapping("/test")
    @ResponseBody
    public String testModel(Model model){
        model.addAttribute("name","zhangsan");
        return "testModel";
    }
    @RequestMapping("/test2")
    @ResponseBody
    public String test2(){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(2);
        pageInfo.setPageNum(10);
        Gson gson = new Gson();
        return gson.toJson(pageInfo);
    }

}
