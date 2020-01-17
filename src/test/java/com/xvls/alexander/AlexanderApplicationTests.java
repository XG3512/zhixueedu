package com.xvls.alexander;

import com.xvls.alexander.dao.testDao;
import com.xvls.alexander.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class AlexanderApplicationTests {

    @Autowired
    private testDao testd;

    @Test
    void contextLoads() {
        List<User> users = testd.selectAll();
        for(User user : users){
            System.out.println(user);
        }
    }

}
