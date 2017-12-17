///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.capstoneproject.test;
//
//import com.mycompany.capstoneproject.dao.StaticPageDao;
//import com.mycompany.capstoneproject.dao.UserDao;
//import com.mycompany.capstoneproject.dto.StaticPage;
//import com.mycompany.capstoneproject.dto.User;
//import org.junit.*;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
///**
// *
// * @author apprentice
// */
//
//public class StaticPageDaoTest {
//
//    private StaticPageDao pageDao;
//    private UserDao userDao;
//
//    private User al;
//    private User francisco;
//    private User john;
//
//    private StaticPage algebraPage;
//    private StaticPage baseballPage;
//    private StaticPage videogamesPage;
//    
//    public StaticPageDaoTest() {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence-test.xml");
//        this.userDao = ctx.getBean("userDao", UserDao.class);
//        this.pageDao = ctx.getBean("staticPageDao", StaticPageDao.class);
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
//        User user1 = new User();
//        user1.setUserName("bob17");
//        user1.setPassword("password");
//        user1.setRoleName("ROLE_CONTRACTOR");
//        user1.setEnabled(1);
//        al = userDao.create(user1);
//
//        User user2 = new User();
//        user2.setUserName("martinnine");
//        user2.setPassword("better_password");
//        user2.setRoleName("ROLE_CONTRACTOR");
//        user2.setEnabled(1);
//        francisco = userDao.create(user2);
//
//        User user3 = new User();
//        user3.setUserName("john");
//        user3.setPassword("john!");
//        user3.setRoleName("ROLE_CONTRACTOR");
//        user3.setEnabled(1);
//        john = userDao.create(user3);
//
//        // Pages
//        StaticPage page1 = new StaticPage();
//        page1.setTitle("The Algebra Page");
//        page1.setHtmlContent("<body><p>Today, we solve equations</p></body>");
//        page1.setDateToPost(null);
//        page1.setExpirationDate(null);
//        page1.setUser(al);
//        page1.setDeleted(false);
//        page1.setHasBeenReviewed(true);
//        algebraPage = pageDao.create(page1);
//
//        StaticPage page2 = new StaticPage();
//        page2.setTitle("The Baseball Page");
//        page2.setHtmlContent("<body>Go Indians!</body>");
//        page2.setDateToPost(new Date(116, 9, 14));
//        page2.setExpirationDate(new Date(117, 9, 14));
//        page2.setUser(francisco);
//        page2.setDeleted(false);
//        page2.setHasBeenReviewed(true);
//        baseballPage = pageDao.create(page2);
//
//        StaticPage page3 = new StaticPage();
//        page3.setTitle("The Videogames Page");
//        page3.setHtmlContent("<html><body><p>Videogames</p><p>TIPO</p></body></html>");
//        page3.setDateToPost(null);
//        page3.setExpirationDate(null);
//        page3.setUser(john);
//        page3.setDeleted(true);
//        page3.setHasBeenReviewed(true);
//        videogamesPage = pageDao.create(page3);
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
//        Assert.assertNotNull(baseballPage);
//        Assert.assertNotNull(baseballPage.getId());
//
//        Date expectedPostDate = new Date(116, 9, 14);
//        Date expectedExpirationDate = new Date(117, 9, 14);
//
//        Assert.assertEquals("The Baseball Page", baseballPage.getTitle());
//        Assert.assertEquals("<body>Go Indians!</body>", baseballPage.getHtmlContent());
//        Assert.assertEquals(expectedPostDate, baseballPage.getDateToPost());
//        Assert.assertEquals(expectedExpirationDate, baseballPage.getExpirationDate());
//        Assert.assertEquals(francisco.getId(), baseballPage.getUser().getId());
//
//    }
//
//    @Test
//    public void testRead() {
//
//        StaticPage readPage = pageDao.read(baseballPage.getId());
//
//        Assert.assertNotNull(readPage);
//        Assert.assertNotNull(readPage.getId());
//
//        Date expectedPostDate = new Date(116, 9, 14);
//        Date expectedExpirationDate = new Date(117, 9, 14);
//
//        Assert.assertEquals("The Baseball Page", readPage.getTitle());
//        Assert.assertEquals("<body>Go Indians!</body>", readPage.getHtmlContent());
//        Assert.assertEquals(expectedPostDate, readPage.getDateToPost());
//        Assert.assertEquals(expectedExpirationDate, readPage.getExpirationDate());
//        Assert.assertEquals(francisco.getId(), readPage.getUser().getId());
//        Assert.assertEquals(false, readPage.isDeleted());
//
//    }
//
//    @Test
//    public void testUpdate() {
//
//        StaticPage readPage = pageDao.read(baseballPage.getId());
//
//        readPage.setExpirationDate(null);
//
//        pageDao.update(readPage);
//
//        StaticPage updatedPage = pageDao.read(readPage.getId());
//
//        Assert.assertNotNull(updatedPage);
//        Assert.assertNotNull(updatedPage.getId());
//
//        Date expectedPostDate = new Date(116, 9, 14);
//        Date expectedExpirationDate = null;
//
//        Assert.assertEquals("The Baseball Page", updatedPage.getTitle());
//        Assert.assertEquals("<body>Go Indians!</body>", updatedPage.getHtmlContent());
//        Assert.assertEquals(expectedPostDate, updatedPage.getDateToPost());
//        Assert.assertEquals(expectedExpirationDate, updatedPage.getExpirationDate());
//        Assert.assertEquals(francisco.getId(), updatedPage.getUser().getId());
//
//    }
//
//    @Test
//    public void testDelete() {
//
//        StaticPage readPage = pageDao.read(baseballPage.getId());
//
//        pageDao.delete(readPage);
//
//        StaticPage deletedPage = pageDao.read(readPage.getId());
//
//        Assert.assertNull(deletedPage);
//
//    }
//
//    @Test
//    public void testList() {
//
//        List<StaticPage> fullStaticPageList = pageDao.list();
//
//        Assert.assertEquals(3, fullStaticPageList.size());
//
//        Assert.assertEquals(algebraPage.getId(), fullStaticPageList.get(0).getId());
//        Assert.assertEquals(baseballPage.getId(), fullStaticPageList.get(1).getId());
//        Assert.assertEquals(videogamesPage.getId(), fullStaticPageList.get(2).getId());
//
//
//    }
//
//    @Test
//    public void testActiveList() {
//
//        List<StaticPage> fullStaticPageList = pageDao.listActivePages();
//
//        Assert.assertEquals(2, fullStaticPageList.size());
//
//
//    }
//
//}
