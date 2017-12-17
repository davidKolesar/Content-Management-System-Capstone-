//package com.mycompany.capstoneproject.test;
//
//import com.mycompany.capstoneproject.controllers.BlogPostController;
//import com.mycompany.capstoneproject.dao.BlogPostCategoryDao;
//import com.mycompany.capstoneproject.dao.BlogPostDao;
//import com.mycompany.capstoneproject.dao.HashtagDao;
//import com.mycompany.capstoneproject.dao.UserDao;
//import com.mycompany.capstoneproject.dto.BlogPost;
//import com.mycompany.capstoneproject.dto.Hashtag;
//import com.mycompany.capstoneproject.dto.User;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by paulharding on 10/9/16.
// */
//public class BlogPostControllerTest {
//
//    private ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence-test.xml");
//
//    private UserDao userDao = ctx.getBean("userDao", UserDao.class);
//    private BlogPostDao blogPostDao = ctx.getBean("blogPostDao", BlogPostDao.class);
//    private HashtagDao hashtagDao = ctx.getBean("hashtagDao", HashtagDao.class);
//    private BlogPostCategoryDao categoryDao = ctx.getBean("blogPostCategoryDao", BlogPostCategoryDao.class);
//
//    private BlogPostController controller = new BlogPostController(userDao, blogPostDao, hashtagDao, categoryDao);
//
//    private User francisco;
//    private BlogPost indiansPost;
//    private BlogPost brownsPost;
//    private BlogPost cavsPost;
//    private BlogPost zipsPost;
//
//    public BlogPostControllerTest() {
//
//    }
//
//    @Before
//    public void setUp() {
//
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence-test.xml");
//
//        JdbcTemplate cleaner = (JdbcTemplate) ctx.getBean("jdbcTemplate");
//        cleaner.execute("DELETE FROM categoryblogpost_blogpost;");
//        cleaner.execute("DELETE FROM blogpost_hashtag;");
//        cleaner.execute("DELETE FROM hashtag;");
//        cleaner.execute("DELETE FROM categoryblogpost;");
//        cleaner.execute("DELETE FROM blogpost;");
//        cleaner.execute("DELETE FROM staticpage");
//        cleaner.execute("DELETE FROM useraccess;");
//
//        User user2 = new User();
//        user2.setUserName("martinnine");
//        user2.setPassword("better_password");
//        user2.setRoleName("ROLE_CONTRACTOR");
//        user2.setEnabled(1);
//        francisco = userDao.create(user2);
//
//        Hashtag hashtag = new Hashtag();
//        hashtag.setTag("#Test");
//        hashtagDao.create(hashtag);
//
//        BlogPost post1 = new BlogPost();
//        post1.setTitle("Playoffs Begin");
//        post1.setHtmlContent("Go Indians! The #Windians are off on another playoff run :) #RallyTogether");
//        post1.setPublishDate(new Date(116, 11, 6));
//        post1.setExpirationDate(new Date(116, 11, 7));
//        post1.setUser(francisco);
//        indiansPost = blogPostDao.create(post1);
//
//        BlogPost post2 = new BlogPost();
//        post2.setTitle("New Season");
//        post2.setHtmlContent("#Browns Time for a #win");
//        post2.setPublishDate(new Date(116, 11, 6));
//        post2.setExpirationDate(new Date(116, 11, 7));
//        post2.setUser(francisco);
//        brownsPost = blogPostDao.create(post2);
//
//        BlogPost post3 = new BlogPost();
//        post3.setTitle("New Season");
//        post3.setHtmlContent("<p>Go #Cavs</p>#Test #Cavs");
//        post3.setPublishDate(new Date(116, 11, 6));
//        post3.setExpirationDate(new Date(116, 11, 7));
//        post3.setUser(francisco);
//        cavsPost = blogPostDao.create(post3);
//
//        BlogPost post4 = new BlogPost();
//        post4.setTitle("Yay Zips!");
//        post4.setHtmlContent("#ZipsGameday#Akron #zips!!! #Zippers</p> #Zips, #akron... #win/ :)");
//        post4.setPublishDate(new Date(116, 11, 6));
//        post4.setExpirationDate(new Date(116, 11, 7));
//        post4.setUser(francisco);
//        zipsPost = blogPostDao.create(post4);
//
//    }
//
//
//    @Test
//    public void getHashtagsFromPostIndians() {
//
//        List<Hashtag> hashtagsInIndiansPost = controller.getHashtagsFromHTML(indiansPost.getHtmlContent());
//
//        Assert.assertEquals(2, hashtagsInIndiansPost.size());
//
//        Assert.assertEquals("#Windians", hashtagsInIndiansPost.get(0).getTag());
//        Assert.assertEquals("#RallyTogether", hashtagsInIndiansPost.get(1).getTag());
//
//    }
//
//    @Test
//    public void getHashtagsFromPostBrowns() {
//
//        List<Hashtag> hashtagsInBrownsPost = controller.getHashtagsFromHTML(brownsPost.getHtmlContent());
//
//        Assert.assertEquals(2, hashtagsInBrownsPost.size());
//
//        Assert.assertEquals("#Browns", hashtagsInBrownsPost.get(0).getTag());
//        Assert.assertEquals("#win", hashtagsInBrownsPost.get(1).getTag());
//
//
//    }
//
//    @Test
//    public void getHashtagsFromPostHTMLTags() {
//
//        List<Hashtag> hashtagsInCavsPost = controller.getHashtagsFromHTML(cavsPost.getHtmlContent());
//
//        Assert.assertEquals(2, hashtagsInCavsPost.size());
//
//        Assert.assertEquals("#Cavs", hashtagsInCavsPost.get(0).getTag());
//
//        List<Hashtag> fullHashtagList = hashtagDao.list();
//
//        Assert.assertEquals(2, fullHashtagList.size());
//
//    }
//
//    @Test
//    public void getHashtagsFromPostCharacters() {
//
//        List<Hashtag> hashtagsInZipsPost = controller.getHashtagsFromHTML(zipsPost.getHtmlContent());
//
//        Assert.assertEquals(7, hashtagsInZipsPost.size());
//
//        Assert.assertEquals("#ZipsGameday", hashtagsInZipsPost.get(0).getTag());
//        Assert.assertEquals("#Akron", hashtagsInZipsPost.get(1).getTag());
//        Assert.assertEquals("#zips", hashtagsInZipsPost.get(2).getTag());
//        Assert.assertEquals("#Zippers", hashtagsInZipsPost.get(3).getTag());
//        Assert.assertEquals("#Zips", hashtagsInZipsPost.get(4).getTag());
//        Assert.assertEquals("#akron", hashtagsInZipsPost.get(5).getTag());
//        Assert.assertEquals("#win", hashtagsInZipsPost.get(6).getTag());
//
//    }
//
//}