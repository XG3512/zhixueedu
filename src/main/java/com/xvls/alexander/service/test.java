package com.xvls.alexander.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class test {

    private String name;
    @RequestMapping("/test1")
    @ResponseBody
    public String test(){
        this.name="xvls";
        return "name:"+name;
    }
}
