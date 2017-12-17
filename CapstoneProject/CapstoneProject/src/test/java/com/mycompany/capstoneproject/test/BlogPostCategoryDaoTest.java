/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.test;

import com.mycompany.capstoneproject.dao.BlogPostCategoryDao;
import com.mycompany.capstoneproject.dao.BlogPostDao;
import com.mycompany.capstoneproject.dao.HashtagDao;
import com.mycompany.capstoneproject.dao.UserDao;
import com.mycompany.capstoneproject.dto.BlogPost;
import com.mycompany.capstoneproject.dto.BlogPostCategory;
import com.mycompany.capstoneproject.dto.Hashtag;
import com.mycompany.capstoneproject.dto.User;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author apprentice
 */

public class BlogPostCategoryDaoTest {

    private UserDao userDao;
    private BlogPostCategoryDao categoryDao;
    private HashtagDao hashtagDao;
    private BlogPostDao blogPostDao;

    private User al;
    private User francisco;
    private User john;

    private BlogPostCategory algebraCategory;
    private BlogPostCategory baseballCategory;
    private BlogPostCategory videoGamesCategory;

    private BlogPost algebraPost;
    private BlogPost baseballPost;
    private BlogPost videoGamesPost;

    private Hashtag algebraTag;
    private Hashtag baseballTag;
    private Hashtag videogamesTag;

    public BlogPostCategoryDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence-test.xml");
        this.userDao = ctx.getBean("userDao", UserDao.class);
        this.categoryDao = ctx.getBean("blogPostCategoryDao", BlogPostCategoryDao.class);
        this.hashtagDao = ctx.getBean("hashtagDao", HashtagDao.class);
        this.blogPostDao = ctx.getBean("blogPostDao", BlogPostDao.class);
    }

    @Before
    public void setUp() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence-test.xml");

        JdbcTemplate cleaner = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        cleaner.execute("DELETE FROM categoryblogpost_blogpost;");
        cleaner.execute("DELETE FROM blogpost_hashtag;");
        cleaner.execute("DELETE FROM hashtag;");
        cleaner.execute("DELETE FROM categoryblogpost;");
        cleaner.execute("DELETE FROM blogpost;");
        cleaner.execute("DELETE FROM staticpage");
        cleaner.execute("DELETE FROM useraccess;");

        // Users
        User user1 = new User();
        user1.setUserName("bob17");
        user1.setPassword("password");
        user1.setRoleName("ROLE_CONTRACTOR");
        user1.setEnabled(1);
        al = userDao.create(user1);

        User user2 = new User();
        user2.setUserName("martinnine");
        user2.setPassword("better_password");
        user2.setRoleName("ROLE_CONTRACTOR");
        user2.setEnabled(1);
        francisco = userDao.create(user2);

        User user3 = new User();
        user3.setUserName("john");
        user3.setPassword("john!");
        user3.setRoleName("ROLE_CONTRACTOR");
        user3.setEnabled(1);
        john = userDao.create(user3);

        // BlogPostCategories
        BlogPostCategory category1 = new BlogPostCategory();
        category1.setCategoryName("Algebra");
        algebraCategory = categoryDao.create(category1);

        BlogPostCategory category2 = new BlogPostCategory();
        category2.setCategoryName("Baseball");
        baseballCategory = categoryDao.create(category2);

        BlogPostCategory category3 = new BlogPostCategory();
        category3.setCategoryName("Video Games");
        videoGamesCategory = categoryDao.create(category3);

        // Hashtags
        Hashtag tag1 = new Hashtag();
        tag1.setTag("#EquationSolved");
        algebraTag = hashtagDao.create(tag1);

        Hashtag tag2 = new Hashtag();
        tag2.setTag("#RallyTogether");
        baseballTag = hashtagDao.create(tag2);

        Hashtag tag3 = new Hashtag();
        tag3.setTag("#Halo");
        videogamesTag = hashtagDao.create(tag3);

        // BlogPosts
        List<BlogPostCategory> categoryList1 = new ArrayList();
        categoryList1.add(algebraCategory);
        categoryList1.add(baseballCategory);
        categoryList1.add(videoGamesCategory);

        List<BlogPostCategory> categoryList2 = new ArrayList();
        categoryList2.add(algebraCategory);
        categoryList2.add(baseballCategory);

        List<Hashtag> hashtagList = new ArrayList();
        hashtagList.add(algebraTag);
        hashtagList.add(baseballTag);
        hashtagList.add(videogamesTag);

        BlogPost post1 = new BlogPost();
        post1.setTitle("Playoffs Begin");
        post1.setHtmlContent("Go Indians!");
        post1.setPublishDate(new Date(116, 11, 6));
        post1.setExpirationDate(new Date(116, 11, 7));
        post1.setUser(francisco);
        post1.setBlogPostCategories(categoryList2);
        post1.setTagsWithinPost(hashtagList);
        baseballPost = blogPostDao.create(post1);

        BlogPost post2 = new BlogPost();
        post2.setTitle("Solving Equations");
        post2.setHtmlContent("The key to solving equations is ... ");
        post2.setUser(al);
        post2.setBlogPostCategories(categoryList1);
        algebraPost = blogPostDao.create(post2);

        BlogPost post3 = new BlogPost();
        post3.setTitle("New Halo Doesn't Have Split Screen");
        post3.setHtmlContent("Why?");
        post3.setUser(john);
        post3.setTagsWithinPost(hashtagList);
        videoGamesPost = blogPostDao.create(post3);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreate() {

        Assert.assertNotNull(algebraCategory);
        Assert.assertNotNull(algebraCategory.getId());

        Assert.assertNotSame(0, algebraCategory.getId());

        Assert.assertEquals("Algebra", algebraCategory.getCategoryName());

    }

    @Test
    public void testRead() {

        BlogPostCategory readAlgebra = categoryDao.read(algebraCategory.getId());

        Assert.assertNotNull(readAlgebra);
        Assert.assertNotNull(readAlgebra.getId());

        Assert.assertEquals("Algebra", readAlgebra.getCategoryName());

    }

    @Test
    public void testUpdate() {

        BlogPostCategory readAlgebra = categoryDao.read(algebraCategory.getId());

        readAlgebra.setCategoryName("Geometry");

        categoryDao.update(readAlgebra);

        BlogPostCategory updatedCategory = categoryDao.read(readAlgebra.getId());

        Assert.assertNotNull(updatedCategory);

        Assert.assertEquals("Geometry", updatedCategory.getCategoryName());

    }

    @Test
    public void testDelete() {

        BlogPostCategory readAlgebra = categoryDao.read(algebraCategory.getId());

        categoryDao.delete(readAlgebra);

        BlogPostCategory deletedAlgebra = categoryDao.read(readAlgebra.getId());

        Assert.assertNull(deletedAlgebra);

    }

    @Test
    public void testList() {

        List<BlogPostCategory> categoryList = categoryDao.list();

        Assert.assertEquals(3, categoryList.size());

    }

}
