package com.xvls.alexander.Controller;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.System_menu;
import com.xvls.alexander.utils.SystemResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/system/UUID")
public class GenerateUUIDController {

    @RequestMapping("getUUID")
    public Object getUUID(HttpServletRequest httpServletRequest){
        UUID uuid = UUID.randomUUID();
        Map result = Maps.newHashMap();
        result.put("uuid",uuid);
        return SystemResponse.ok(result);
    }
}
