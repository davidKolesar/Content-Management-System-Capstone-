///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.capstoneproject.test;
//
//import com.mycompany.capstoneproject.dao.BlogPostDao;
//import com.mycompany.capstoneproject.dao.HashtagDao;
//import com.mycompany.capstoneproject.dao.UserDao;
//import com.mycompany.capstoneproject.dto.BlogPost;
//import com.mycompany.capstoneproject.dto.BlogPostCategory;
//import com.mycompany.capstoneproject.dto.Hashtag;
//import com.mycompany.capstoneproject.dto.User;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.jdbc.core.JdbcTemplate;
//
///**
// *
// * @author apprentice
// */
//
//public class HashtagDaoTest {
//    
//    private UserDao userDao;
//    private HashtagDao hashtagDao;
//    private BlogPostDao blogPostDao;
//
//    private User bob;
//    private User addedBob;
//    private User martin;
//    private User addedMartin;
//    private User steve;
//    private User addedSteve;
//    
//    private Hashtag RallyTogether;
//    private Hashtag ALLin216;
//
//    private BlogPostCategory algebra;
//    private BlogPostCategory baseball;
//
//    private BlogPost sportsPost;
//    private BlogPost algebraPost;
//    private BlogPost xboxPost;
//    
//    public HashtagDaoTest() {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence-test.xml");
//        this.userDao = ctx.getBean("userDao", UserDao.class);
//        this.hashtagDao = ctx.getBean("hashtagDao", HashtagDao.class);
//        this.blogPostDao = ctx.getBean("blogPostDao", BlogPostDao.class);
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
//        // Users
//        bob = new User();
//        bob.setUserName("bob17");
//        bob.setPassword("password");
//        bob.setRoleName("ROLE_CONTRACTOR");
//        bob.setEnabled(1);
//        addedBob = userDao.create(bob);
//
//        martin = new User();
//        martin.setUserName("martinnine");
//        martin.setPassword("better_password");
//        martin.setRoleName("ROLE_CONTRACTOR");
//        martin.setEnabled(1);
//        addedMartin = userDao.create(martin);
//
//        steve = new User();
//        steve.setUserName("steve");
//        steve.setPassword("steve!");
//        steve.setRoleName("ROLE_CONTRACTOR");
//        steve.setEnabled(1);
//        addedSteve = userDao.create(steve);
//        
//        // Hashtags
//        RallyTogether = new Hashtag();
//        RallyTogether.setTag("#RallyTogether");
//        
//        ALLin216 = new Hashtag();
//        ALLin216.setTag("#ALLin216");
//
//        // BlogPostCategories
//        algebra = new BlogPostCategory();
//        algebra.setId(1);
//
//        baseball = new BlogPostCategory();
//        baseball.setId(2);
//
//        // BlogPosts
//        List<BlogPostCategory> categoryList = new ArrayList();
//        categoryList.add(algebra);
//        categoryList.add(baseball);
//
//        sportsPost = new BlogPost();
//        sportsPost.setTitle("Playoffs Begin");
//        sportsPost.setHtmlContent("Go Indians!");
//        sportsPost.setPublishDate(new Date(116, 11, 6));
//        sportsPost.setExpirationDate(new Date(116, 11, 7));
//        sportsPost.setUser(addedMartin);
//        sportsPost.setBlogPostCategories(categoryList);
//
//        algebraPost = new BlogPost();
//        algebraPost.setTitle("Solving Equations");
//        algebraPost.setHtmlContent("The key to solving equations is ... ");
//        algebraPost.setUser(addedBob);
//        algebraPost.setBlogPostCategories(categoryList);
//
//        xboxPost = new BlogPost();
//        xboxPost.setTitle("New Halo Doesn't Have Split Screen");
//        xboxPost.setHtmlContent("Why?");
//        xboxPost.setUser(addedMartin);
//        
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    @Test
//    public void testCreate() {
//        
//        Hashtag addedHashtag = hashtagDao.create(RallyTogether);
//        
//        Assert.assertNotNull(addedHashtag);
//        Assert.assertNotNull(addedHashtag.getId());
//        
//        Assert.assertEquals("#RallyTogether", addedHashtag.getTag());
//        
//    }
//    
//    @Test
//    public void testRead() {
//        
//        Hashtag addedTribeTag = hashtagDao.create(RallyTogether);
//        Hashtag addedCavsTag = hashtagDao.create(ALLin216);
//        
//        Hashtag readHashtag = hashtagDao.read(addedCavsTag.getId());
//        
//        Assert.assertNotNull(readHashtag);
//        Assert.assertNotNull(readHashtag.getId());
//        
//        Assert.assertEquals("#ALLin216", readHashtag.getTag());
//        
//    }
//    
//    @Test
//    public void testUpdate() {
//        
//        Hashtag addedTribeTag = hashtagDao.create(RallyTogether);
//        Hashtag addedCavsTag = hashtagDao.create(ALLin216);
//        
//        Hashtag readTag = hashtagDao.read(addedTribeTag.getId());
//        readTag.setTag("#RollTribe");
//        
//        hashtagDao.update(readTag);
//        
//        Hashtag updatedTag = hashtagDao.read(readTag.getId());
//        
//        Assert.assertNotNull(updatedTag);
//        
//        Assert.assertEquals("#RollTribe", updatedTag.getTag());
//        
//    }
//    
//    @Test
//    public void testDelete() {
//        
//        Hashtag addedTribeTag = hashtagDao.create(RallyTogether);
//        Hashtag addedCavsTag = hashtagDao.create(ALLin216);
//        
//        Hashtag readTribeTag = hashtagDao.read(addedTribeTag.getId());
//        
//        hashtagDao.delete(readTribeTag);
//        
//        Hashtag deletedTribeTag = hashtagDao.read(readTribeTag.getId());
//        Hashtag notDeletedCavsTag = hashtagDao.read(addedCavsTag.getId());
//        
//        Assert.assertNull(deletedTribeTag);
//        Assert.assertNotNull(notDeletedCavsTag);
//        
//    }
//    
//    @Test
//    public void testList() {
//        
//        Hashtag addedTribeTag = hashtagDao.create(RallyTogether);
//        Hashtag addedCavsTag = hashtagDao.create(ALLin216);
//        
//        List<Hashtag> hashtagList = hashtagDao.list();
//        
//        Assert.assertEquals(2, hashtagList.size());
//        
//    }
//        
//}
