package com.xvls.alexander;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xvls.alexander.dao.testDao;
import com.xvls.alexander.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlexanderApplication.class)
public class TestUserMapper {

    private testDao testd;

    @Test
    public void testInsert(){
        User user = new User();
        user.setId(null);
        user.setUName("Glitter");
        user.setPassword("1102837040");
        int result = this.testd.insert(user);
        System.out.println("数据库受影响的行数："+result);

        //获取自增长后的id值，自增长后的id值会回填到user对象中
        System.out.println("id => "+user.getId());
    }

    @Test
    public void testUpdateById(){
        User user = new User();
        user.setId(2L);//条件，根据id更新
        user.setUName("GlitterL");//需要更新的字段
        int result = this.testd.updateById(user);
        System.out.println("result => "+result);
    }

    @Test
    public void testUpdate(){
        User user = new User();
        user.setUName("GlitterS");//更新的字段

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id",2);//更新的条件

        //根据条件做更新
        int result = this.testd.update(user, wrapper);
        System.out.println("result => "+ result);
    }
    @Test
    public void testUpdate2(){

        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.set("uname","GlitterR").set("password","111111")//更新的字段
        .eq("id",2L);//更新的条件

        int result = this.testd.update(null,wrapper);
        System.out.println("result => "+ result);
    }

    @Test
    public void testDeleteById(){
        int result = this.testd.deleteById(2L);
        System.out.println("reslut => "+ result);
    }

    @Test
    public void testDeleteByMap(){
        Map<String , Object> map = new HashMap<>();
        map.put("uname","Glitter");
        map.put("password","1102837040");

        //根据map删除数据，多条件之间是and关系
        int result = this.testd.deleteByMap(map);
        System.out.println("reslut => "+ result);
    }

    @Test
    public void testDeleteByBatchIds(){
        //根据id集合批量删除
        int result = this.testd.deleteBatchIds(Arrays.asList(1L,2L,3L));
        System.out.println("reslut => "+ result);
    }

    public void testselectOne(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("uname","GlitterL");
        User user = this.testd.selectOne(wrapper);
        System.out.println(user);
        //查询超过一条时会报错
    }

    @Test
    public void testSelectCount(){

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.gt("age",20);//条件：年龄大于20岁的用户会被查询出来
        Integer selectCount = this.testd.selectCount(wrapper);

        System.out.println("count => "+ selectCount);
    }

    @Test
    public void testSelectList(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("uname","litter");//like '%litter%'

        List<User> users = this.testd.selectList(wrapper);
        for(User user : users){
            System.out.println(user);
        }
    }

    @Test
    public void testSelectPage(){

        Page<User> page = new Page<>(1,1);//查询第一页，查询1条数据
        /*page.setCurrent(1);
        page.setSize(1);*/

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("uname","litter");//like '%litter%'

        //这里使用的是 limit 进行分页的
        IPage<User> iPage = this.testd.selectPage(page, wrapper);
        System.out.println("数据总条数："+ iPage.getTotal());
        System.out.println("数据总页数："+ iPage.getPages());
        System.out.println("当前页数："+ iPage.getCurrent());
        List<User> records = iPage.getRecords();
        for(User user:records){
            System.out.println(user);
        }
    }

    /*逻辑查询*/
    @Test
    public void testOr(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("uname","Glitter").or().eq("id",2);

        List<User> users = this.testd.selectList(wrapper);
        for (User user: users) {
            System.out.println(user);
        }
    }

}
