package com.xvls.alexander;

import com.xvls.alexander.dao.WxArticleMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlexanderApplication.class)
public class TestArticleMapper {

    @Autowired
    WxArticleMapper wxArticleMapper;

    @Test
    public void testGetArticleByPage(){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(0);
        pageInfo.setPageSize(3);
        List<Article> articles = wxArticleMapper.getArticleByPage(pageInfo,1);
        for (Article article : articles){
            System.out.println(article);
            System.out.println(article.getSchool());
        }
    }

}
