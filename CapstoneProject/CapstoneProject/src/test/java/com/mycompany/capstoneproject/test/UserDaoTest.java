///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.capstoneproject.test;
//
//import com.mycompany.capstoneproject.dao.UserDao;
//import com.mycompany.capstoneproject.dto.User;
//import java.util.List;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import static org.junit.Assert.*;
//import org.springframework.jdbc.core.JdbcTemplate;
//
///**
// *
// * @author apprentice
// */
//
//public class UserDaoTest {
//
//    private UserDao userDao;
//    
//    private User bob;
//    private User martin;
//    private User steve;
//    
//    public UserDaoTest() {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence-test.xml");
//        this.userDao = ctx.getBean("userDao", UserDao.class);
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
//        bob = new User();
//        bob.setUserName("bob17");
//        bob.setPassword("password");
//        bob.setRoleName("ROLE_CONTRACTOR");
//        bob.setEnabled(1);
//
//        martin = new User();
//        martin.setUserName("martinnine");
//        martin.setPassword("better_password");
//        martin.setRoleName("ROLE_CONTRACTOR");
//        martin.setEnabled(1);
//
//        steve = new User();
//        steve.setUserName("steve");
//        steve.setPassword("steve!");
//        steve.setRoleName("ROLE_CONTRACTOR");
//        steve.setEnabled(1);
//
//
//    }
//    
//    @After
//    public void tearDown() {
//    }
//    
//    @Test
//    public void testCreateBob() {
//        
//        User addedUser = userDao.create(bob);
//        
//        Assert.assertNotNull(addedUser);
//        Assert.assertNotNull(addedUser.getId());
//        
//        Assert.assertEquals("bob17", addedUser.getUserName());
//        Assert.assertEquals("password", addedUser.getPassword());
//        
//    }
//    
//    @Test
//    public void testCreateMartin() {
//        
//        User addedUser = userDao.create(martin);
//        
//        Assert.assertNotNull(addedUser);
//        Assert.assertNotNull(addedUser.getId());
//        
//        Assert.assertEquals("martinnine", addedUser.getUserName());
//        Assert.assertEquals("better_password", addedUser.getPassword());
//        
//    }
//    
//    @Test
//    public void testCreateSteve() {
//        
//        User addedUser = userDao.create(steve);
//        
//        Assert.assertNotNull(addedUser);
//        Assert.assertNotNull(addedUser.getId());
//        
//        Assert.assertEquals("steve", addedUser.getUserName());
//        Assert.assertEquals("steve!", addedUser.getPassword());
//        
//    }
//    
//    @Test
//    public void testRead() {
//        
//        User addedUser = userDao.create(bob);
//        User addedUser2 = userDao.create(martin);
//        
//        User readUser = userDao.read(addedUser2.getId());
//        
//        Assert.assertNotNull(readUser);
//        Assert.assertNotNull(readUser.getId());
//        
//        Assert.assertEquals("martinnine", readUser.getUserName());
//        Assert.assertEquals("better_password", readUser.getPassword());
//        
//    }
//    
//    @Test
//    public void testUpdate() {
//        
//        User addedUser = userDao.create(bob);
//        User addedUser2 = userDao.create(martin);
//        
//        User readUser = userDao.read(addedUser2.getId());
//        
//        readUser.setPassword("even_better_password");
//        
//        userDao.update(readUser);
//        
//        User updatedUser = userDao.read(readUser.getId());
//        
//        Assert.assertEquals("martinnine", readUser.getUserName());
//        Assert.assertEquals("even_better_password", updatedUser.getPassword());
//        
//    }
//    
//    @Test
//    public void testDelete() {
//        
//        User addedSteve = userDao.create(steve);
//        User addedMartin = userDao.create(martin);
//        
//        User readUser = userDao.read(addedMartin.getId());
//        
//        userDao.delete(readUser);
//        
//        User deletedUser = userDao.read(readUser.getId());
//        User notDeletedUser = userDao.read(addedSteve.getId());
//        
//        Assert.assertNull(deletedUser);
//        Assert.assertNotNull(notDeletedUser);
//        
//    }
//    
//    @Test
//    public void testList() {
//        
//        User addedMartin = userDao.create(martin);
//        User addedBob = userDao.create(bob);
//        User addedSteve = userDao.create(steve);
//        
//        List<User> userList = userDao.list();
//        
//        Assert.assertNotNull(userList);
//        Assert.assertEquals(3, userList.size());
//        
//    }
//
//    @Test
//    public void testByUsername() {
//
//        User addedMartin = userDao.create(martin);
//        User addedBob = userDao.create(bob);
//        User addedSteve = userDao.create(steve);
//
//        User readBob = userDao.searchByUserName("bob17");
//
//        Assert.assertNotNull(readBob);
//        Assert.assertNotNull(readBob.getId());
//
//        Assert.assertEquals("bob17", readBob.getUserName());
//        Assert.assertEquals("password", readBob.getPassword());
//
//    }
//
//}
