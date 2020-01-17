package com.xvls.alexander;

import com.xvls.alexander.dao.testDao;
import com.xvls.alexander.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserMapper {

    @Autowired
    private testDao testd;

    @Test
    public void testInsert(){
        User user = new User();
        user.setId(null);
        user.setUname("Glitter");
        user.setPassword("1102837040");
        int result = this.testd.insert(user);
        System.out.println("数据库受影响的行数："+result);

        //获取自增长后的id值，自增长后的id值会回填到user对象中
        System.out.println("id => "+user.getId());
    }
}
