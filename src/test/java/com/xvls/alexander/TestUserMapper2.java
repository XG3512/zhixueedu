package com.xvls.alexander;

import com.xvls.alexander.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlexanderApplication.class)
public class TestUserMapper2 {

    @Test
    public void testSelectById(){
        User user = new User();
        user.setId(2L);

        User user2 = user.selectById();
        System.out.println(user2);
    }
}
