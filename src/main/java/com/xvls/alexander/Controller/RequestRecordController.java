package com.xvls.alexander.Controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xvls.alexander.entity.RequestRecord;
import com.xvls.alexander.utils.SystemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/system/requestRecord")
public class RequestRecordController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获得 近15天的 请求记录
     * @return
     * @throws ParseException
     */
    @RequestMapping("getRequestCount")
    public Object getRequestCount() throws ParseException {
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");

        //String key = "requireRecord";

        List<RequestRecord> countList = Lists.newArrayList();

        for (int i=0;i<15;i++){

            String date_temp = dateFormat.format(new Date(date.getTime() - i*24*60*60*1000));

            RequestRecord requestRecord = new RequestRecord();
            String key = "requireRecord"+date_temp;
            if(stringRedisTemplate.hasKey(key)){
                Integer count = Integer.valueOf(stringRedisTemplate.opsForValue().get(key));
                requestRecord.setCount(count);
            }else{
                requestRecord.setCount(0);
            }
            requestRecord.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date_temp));
            countList.add(requestRecord);
        }
        Map result = Maps.newHashMap();
        result.put("countList",countList);
        return SystemResponse.ok(result);
    }
}
