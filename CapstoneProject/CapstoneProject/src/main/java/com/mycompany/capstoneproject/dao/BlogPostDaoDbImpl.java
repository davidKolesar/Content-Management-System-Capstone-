/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dao;

import com.mycompany.capstoneproject.dto.BlogPost;
import com.mycompany.capstoneproject.dto.BlogPostCategory;
import com.mycompany.capstoneproject.dto.Hashtag;
import com.mycompany.capstoneproject.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apprentice
 */

public class BlogPostDaoDbImpl implements BlogPostDao {

    private UserDao userDao;
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_BLOGPOST = "INSERT INTO blogpost (title, htmlcontent, postdate, expirationdate, useraccessid, isDeleted, hasBeenReviewed) VALUES(?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_SELECT_BLOGPOST = "SELECT * FROM blogpost WHERE id=?;";
    private static final String SQL_UPDATE_BLOGPOST = "UPDATE blogpost SET title=?, htmlcontent=?, postdate=?, expirationdate=?, useraccessid=?, isDeleted=?, hasBeenReviewed=? WHERE id=?;";
    private static final String SQL_DELETE_BLOGPOST = "DELETE FROM blogpost WHERE id=?;";
    private static final String SQL_SELECT_ALL_BLOGPOST = "SELECT * FROM blogpost;";
    private static final String SQL_SELECT_ACTIVE_POSTS = "SELECT * FROM blogpost WHERE hasBeenReviewed=1 AND (isDeleted=0 OR isDeleted IS NULL) ORDER BY postdate desc;";
    private static final String SQL_INSERT_HASHTAG_RELATIONSHIP = "INSERT INTO blogpost_hashtag (hashtagid, blogpostid) VALUES(?,?);";
    private static final String SQL_INSERT_CATEGORY_RELATIONSHIP = "INSERT INTO categoryblogpost_blogpost (categoryblogpostid, blogpostid) VALUES(?, ?);";
    private static final String SQL_SELECT_CATEGORIES = "SELECT c.id, nametag FROM categoryblogpost_blogpost cbb LEFT JOIN categoryblogpost c ON cbb.categoryblogpostid = c.id WHERE cbb.blogpostid=?;";
    private static final String SQL_DELETE_CATEGORY_RELATIONSHIPS = "DELETE FROM categoryblogpost_blogpost WHERE blogpostid=?;";
    private static final String SQL_DELETE_HASHTAG_RELATIONSHIPS = "DELETE FROM blogpost_hashtag WHERE blogpostid=?;";
    private static final String SQL_SELECT_BLOGPOSTS_TO_REVIEW = "SELECT * FROM blogpost WHERE hasBeenReviewed=0;";

    public BlogPostDaoDbImpl(UserDao userDao, JdbcTemplate jdbcTemplate) {
        this.userDao = userDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BlogPost create(BlogPost post) {

        jdbcTemplate.update(SQL_INSERT_BLOGPOST,
                post.getTitle(),
                post.getHtmlContent(),
                post.getPublishDate(),
                post.getExpirationDate(),
                post.getUser().getId(),
                post.isDeleted(),
                post.getHasBeenReviewed());

        Integer blogPostId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);

        post.setId(blogPostId);

        writeToCategoryRelationshipTable(post);
        writeToHashtagRelationshipTable(post);

        return post;
    }

    @Override
    public BlogPost read(Integer id) {

        try {
            BlogPost blogPost = jdbcTemplate.queryForObject(SQL_SELECT_BLOGPOST, new BlogPostMapper(), id);
            return blogPost;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(BlogPost post) {

        jdbcTemplate.update(SQL_UPDATE_BLOGPOST,
                post.getTitle(),
                post.getHtmlContent(),
                post.getPublishDate(),
                post.getExpirationDate(),
                post.getUser().getId(),
                post.isDeleted(),
                post.getHasBeenReviewed(),
                post.getId());

        deleteFromCategoryRelationshipTable(post);
        deleteFromHashtagRelationshipTable(post);

        writeToCategoryRelationshipTable(post);
        writeToHashtagRelationshipTable(post);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(BlogPost post) {

        // Delete entries where blogpostid is a foreign key
        jdbcTemplate.update(SQL_DELETE_HASHTAG_RELATIONSHIPS, post.getId());
        jdbcTemplate.update(SQL_DELETE_CATEGORY_RELATIONSHIPS, post.getId());

        jdbcTemplate.update(SQL_DELETE_BLOGPOST, post.getId());

    }

    @Override
    public List<BlogPost> list() {

        List<BlogPost> blogPostList = jdbcTemplate.query(SQL_SELECT_ALL_BLOGPOST, new BlogPostMapper());

        return blogPostList;
    }

    @Override
    public List<BlogPost> listActivePosts() {

        List<BlogPost> activePostList = jdbcTemplate.query(SQL_SELECT_ACTIVE_POSTS, new BlogPostMapper());

        Date today = new Date();

        Iterator<BlogPost> i = activePostList.iterator();
        while (i.hasNext()) {
            BlogPost b = i.next();

            if (b.getExpirationDate() == null) {
            } else if (b.getExpirationDate().compareTo(today) > 0) {
            } else if (b.getExpirationDate().compareTo(today) < 0) {
                i.remove();
            } else if (b.getExpirationDate().compareTo(today) == 0) {
                i.remove();
            }

            if (b.getPublishDate() == null) {
            } else if (b.getPublishDate().compareTo(today) > 0) {
                i.remove();
            } else if (b.getPublishDate().compareTo(today) < 0) {
            } else if (b.getPublishDate().compareTo(today) == 0) {
            }

        }

        return activePostList;
    }

    @Override
    public List<BlogPost> listPostsNeedingReview() {

        List<BlogPost> blogPostsToReview = jdbcTemplate.query(SQL_SELECT_BLOGPOSTS_TO_REVIEW, new BlogPostMapper());

        return blogPostsToReview;
    }

    private void writeToHashtagRelationshipTable(BlogPost post) {
        if ( !(post.getTagsWithinPost() == null) ) {
            for (Hashtag h : post.getTagsWithinPost()) {
                jdbcTemplate.update(SQL_INSERT_HASHTAG_RELATIONSHIP, h.getId(), post.getId());
            }
        }
    }

    private void writeToCategoryRelationshipTable(BlogPost post) {
        if ( !(post.getBlogPostCategories() == null) ) {
            for (BlogPostCategory c : post.getBlogPostCategories()) {
                jdbcTemplate.update(SQL_INSERT_CATEGORY_RELATIONSHIP, c.getId(), post.getId());
            }
        }
    }

    private void deleteFromHashtagRelationshipTable(BlogPost post) {
        if ( !(post.getTagsWithinPost() == null) )
            for (Hashtag h : post.getTagsWithinPost()) {
                jdbcTemplate.update("DELETE FROM blogpost_hashtag WHERE blogpostid=?;", post.getId());
            }
    }

    private void deleteFromCategoryRelationshipTable(BlogPost post) {
        jdbcTemplate.update("DELETE FROM categoryblogpost_blogpost WHERE blogpostid=?;",post.getId());
    }

    private final class BlogPostMapper implements RowMapper<BlogPost> {

        @Override
        public BlogPost mapRow(ResultSet resultSet, int i) throws SQLException {

            BlogPost blogPost = new BlogPost();

            Integer userId = resultSet.getInt("useraccessid");
            User user = userDao.read(userId);

            blogPost.setId(resultSet.getInt("id"));
            blogPost.setHtmlContent(resultSet.getString("htmlcontent"));
            blogPost.setTitle(resultSet.getString("title"));
            blogPost.setPublishDate(resultSet.getDate("postdate"));
            blogPost.setExpirationDate(resultSet.getDate("expirationdate"));
            blogPost.setUser(user);
            blogPost.setDeleted(resultSet.getBoolean("isDeleted"));
            blogPost.setHasBeenReviewed(resultSet.getBoolean("hasBeenReviewed"));

            List<BlogPostCategory> categoryList = jdbcTemplate.query(SQL_SELECT_CATEGORIES, new BlogPostCategoryMapper(), blogPost.getId());
            blogPost.setBlogPostCategories(categoryList);

            List<Hashtag> tagList = jdbcTemplate.query("SELECT h.id, tagname FROM blogpost_hashtag r INNER JOIN hashtag h ON r.hashtagid = h.id WHERE r.blogpostid=?;", new HashtagMapper(), blogPost.getId());
            blogPost.setTagsWithinPost(tagList);

            return blogPost;

        }
    }

    private final class BlogPostCategoryMapper implements RowMapper<BlogPostCategory> {

        @Override
        public BlogPostCategory mapRow(ResultSet resultSet, int i) throws SQLException {

            BlogPostCategory category = new BlogPostCategory();

            category.setId(resultSet.getInt("id"));
            category.setCategoryName(resultSet.getString("nametag"));

            return category;

        }
    }

    private final class HashtagMapper implements RowMapper<Hashtag> {

        @Override
        public Hashtag mapRow(ResultSet resultSet, int i) throws SQLException {

            Hashtag hashtag = new Hashtag();

            hashtag.setId(resultSet.getInt("id"));
            hashtag.setTag(resultSet.getString("tagname"));

            return hashtag;

        }

    }

}
