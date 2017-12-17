/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.test;

import com.mycompany.capstoneproject.dao.*;
import com.mycompany.capstoneproject.dto.BlogPost;
import com.mycompany.capstoneproject.dto.BlogPostCategory;
import com.mycompany.capstoneproject.dto.Hashtag;
import com.mycompany.capstoneproject.dto.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author apprentice
 */

public class BlogPostDaoTest {

    private JdbcTemplate jdbcTemplate;

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

    public BlogPostDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence-test.xml");
        this.jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);
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
    public void testCreateSportsPost() {

        Assert.assertNotNull(baseballPost);
        Assert.assertNotNull(baseballPost.getId());

        Date expectedPostDate = new Date(116, 11, 6);
        Date expectedExpirationDate = new Date(116, 11, 7);

        Assert.assertEquals("Playoffs Begin", baseballPost.getTitle());
        Assert.assertEquals("Go Indians!", baseballPost.getHtmlContent());
        Assert.assertEquals(expectedPostDate, baseballPost.getPublishDate());
        Assert.assertEquals(expectedExpirationDate, baseballPost.getExpirationDate());
        Assert.assertEquals(francisco.getId(), baseballPost.getUser().getId());

        List<BlogPostCategory> categoryList = jdbcTemplate.query("SELECT c.id, nametag FROM categoryblogpost_blogpost r LEFT JOIN categoryblogpost c ON r.categoryblogpostid = c.id WHERE r.blogpostid=?;", new BlogPostCategoryMapper(), baseballPost.getId());
        Assert.assertEquals(2, categoryList.size());

        List<Hashtag> tagList = jdbcTemplate.query("SELECT h.id, tagname FROM blogpost_hashtag r INNER JOIN hashtag h ON r.hashtagid = h.id WHERE r.blogpostid=?;", new HashtagMapper(), baseballPost.getId());
        Assert.assertEquals(3, tagList.size());

    }

    @Test
    public void testCreateAlgebraPost() {

        Assert.assertNotNull(algebraPost);
        Assert.assertNotNull(algebraPost.getId());

        Assert.assertEquals("Solving Equations", algebraPost.getTitle());
        Assert.assertEquals("The key to solving equations is ... ", algebraPost.getHtmlContent());
        Assert.assertNull(algebraPost.getPublishDate());
        Assert.assertNull(algebraPost.getExpirationDate());
        Assert.assertEquals(al.getId(), algebraPost.getUser().getId());

        List<BlogPostCategory> categoryList = jdbcTemplate.query("SELECT c.id, nametag FROM categoryblogpost_blogpost r LEFT JOIN categoryblogpost c ON r.categoryblogpostid = c.id WHERE r.blogpostid=?;", new BlogPostCategoryMapper(), algebraPost.getId());
        Assert.assertEquals(3, categoryList.size());

        List<Hashtag> tagList = jdbcTemplate.query("SELECT h.id, tagname FROM blogpost_hashtag r INNER JOIN hashtag h ON r.hashtagid = h.id WHERE r.blogpostid=?;", new HashtagMapper(), algebraPost.getId());
        Assert.assertEquals(0, tagList.size());

    }

    @Test
    public void testCreateXboxPost() {

        Assert.assertNotNull(videoGamesPost);
        Assert.assertNotNull(videoGamesPost.getId());

        Assert.assertEquals("New Halo Doesn't Have Split Screen", videoGamesPost.getTitle());
        Assert.assertEquals("Why?", videoGamesPost.getHtmlContent());
        Assert.assertNull(videoGamesPost.getPublishDate());
        Assert.assertNull(videoGamesPost.getExpirationDate());
        Assert.assertEquals(john.getId(), videoGamesPost.getUser().getId());
        Assert.assertNull(videoGamesPost.getBlogPostCategories());

        List<BlogPostCategory> categoryList = jdbcTemplate.query("SELECT c.id, nametag FROM categoryblogpost_blogpost r LEFT JOIN categoryblogpost c ON r.categoryblogpostid = c.id WHERE r.blogpostid=?;", new BlogPostCategoryMapper(), videoGamesPost.getId());
        Assert.assertEquals(0, categoryList.size());

        List<Hashtag> tagList = jdbcTemplate.query("SELECT h.id, tagname FROM blogpost_hashtag r INNER JOIN hashtag h ON r.hashtagid = h.id WHERE r.blogpostid=?;", new HashtagMapper(), videoGamesPost.getId());
        Assert.assertEquals(3, tagList.size());

    }

    @Test
    public void testReadSportsPost() {

        BlogPost readPost = blogPostDao.read(baseballPost.getId());

        Assert.assertNotNull(readPost);
        Assert.assertNotNull(readPost.getId());

        Date expectedPostDate = new Date(116, 11, 6);
        Date expectedExpirationDate = new Date(116, 11, 7);

        Assert.assertEquals("Playoffs Begin", readPost.getTitle());
        Assert.assertEquals("Go Indians!", readPost.getHtmlContent());
        Assert.assertEquals(expectedPostDate, readPost.getPublishDate());
        Assert.assertEquals(expectedExpirationDate, readPost.getExpirationDate());
        Assert.assertEquals(francisco.getId(), readPost.getUser().getId());

        Assert.assertEquals(2, readPost.getBlogPostCategories().size());
        Assert.assertEquals(3, readPost.getTagsWithinPost().size());

    }

    @Test
    public void testReadAlgebraPost() {

        BlogPost readPost = blogPostDao.read(algebraPost.getId());

        Assert.assertNotNull(readPost);
        Assert.assertNotNull(readPost.getId());

        Assert.assertEquals("Solving Equations", readPost.getTitle());
        Assert.assertEquals("The key to solving equations is ... ", readPost.getHtmlContent());
        Assert.assertNull(readPost.getPublishDate());
        Assert.assertNull(readPost.getExpirationDate());
        Assert.assertEquals(al.getId(), readPost.getUser().getId());

        Assert.assertEquals(3, readPost.getBlogPostCategories().size());
        Assert.assertEquals(0, readPost.getTagsWithinPost().size());

    }

    @Test
    public void testReadXboxPost() {

        BlogPost readPost = blogPostDao.read(videoGamesPost.getId());

        Assert.assertNotNull(readPost);
        Assert.assertNotNull(readPost.getId());

        Assert.assertEquals("New Halo Doesn't Have Split Screen", readPost.getTitle());
        Assert.assertEquals("Why?", readPost.getHtmlContent());
        Assert.assertNull(readPost.getPublishDate());
        Assert.assertNull(readPost.getExpirationDate());
        Assert.assertEquals(john.getId(), readPost.getUser().getId());

        Assert.assertEquals(0, readPost.getBlogPostCategories().size());
        Assert.assertEquals(3, readPost.getTagsWithinPost().size());

    }

    @Test
    public void testUpdateSportsPost() {

        BlogPost readPost = blogPostDao.read(baseballPost.getId());

        readPost.setHtmlContent("Let's Go Tribe!!!");

        List<BlogPostCategory> categoryList = new ArrayList();
        categoryList.add(algebraCategory);

        readPost.setBlogPostCategories(categoryList);

        List<Hashtag> hashtagList = new ArrayList();
        hashtagList.add(algebraTag);
        hashtagList.add(baseballTag);

        readPost.setTagsWithinPost(hashtagList);

        blogPostDao.update(readPost);

        BlogPost updatedPost = blogPostDao.read(readPost.getId());

        Assert.assertNotNull(updatedPost);

        Assert.assertEquals("Let's Go Tribe!!!", updatedPost.getHtmlContent());

        Assert.assertEquals(1, readPost.getBlogPostCategories().size());
        Assert.assertEquals(2, readPost.getTagsWithinPost().size());

    }

    @Test
    public void testDeleteSportsPost() {

        BlogPost addedSportsPost = blogPostDao.create(baseballPost);

        BlogPost readPost = blogPostDao.read(addedSportsPost.getId());

        blogPostDao.delete(readPost);

        BlogPost deletedPost = blogPostDao.read(readPost.getId());

        Assert.assertNull(deletedPost);

        List<Hashtag> tagList = jdbcTemplate.query("SELECT h.id, tagname FROM blogpost_hashtag r INNER JOIN hashtag h ON r.hashtagid = h.id WHERE r.blogpostid=?;", new HashtagMapper(), baseballPost.getId());
        Assert.assertEquals(0, tagList.size());

        List<BlogPostCategory> categoryList = jdbcTemplate.query("SELECT c.id, nametag FROM categoryblogpost_blogpost r LEFT JOIN categoryblogpost c ON r.categoryblogpostid = c.id WHERE r.blogpostid=?;", new BlogPostCategoryMapper(), baseballPost.getId());
        Assert.assertEquals(0, categoryList.size());

    }

    @Test
    public void testList() {

        List<BlogPost> blogPostList = blogPostDao.list();

        Assert.assertEquals(3, blogPostList.size());

    }

    private final class BlogPostCategoryMapper implements RowMapper<BlogPostCategory> {

        @Override
        public BlogPostCategory mapRow(ResultSet resultSet, int i) throws SQLException {

            BlogPostCategory category = new BlogPostCategory();

            category.setId( resultSet.getInt("id") );
            category.setCategoryName( resultSet.getString("nametag") );

            return category;

        }
    }

    private final class HashtagMapper implements RowMapper<Hashtag> {

            @Override
            public Hashtag mapRow(ResultSet resultSet, int i) throws SQLException {

                Hashtag hashtag = new Hashtag();

                hashtag.setId( resultSet.getInt("id") );
                hashtag.setTag( resultSet.getString("tagname") );

                return hashtag;

            }
    }

}
