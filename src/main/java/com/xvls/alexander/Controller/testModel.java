package com.xvls.alexander.Controller;

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

}
