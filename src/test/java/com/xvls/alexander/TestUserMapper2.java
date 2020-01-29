package com.xvls.alexander;

import com.xvls.alexander.entity.User;
import com.xvls.alexander.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlexanderApplication.class)
public class TestUserMapper2 {

    @Autowired
    UserServiceImpl userService;

    @Test
    public void testSelectById(){
        User user = new User();
        user.setId(2L);

        User user2 = user.selectById();
        System.out.println(user2);
    }

    @Test
    public void queryUserByName(){
        User user = userService.queryUserByName("GlitterR");
        System.out.println(user);
    }


}
