/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dao;

import com.mycompany.capstoneproject.dto.BlogPost;
import com.mycompany.capstoneproject.dto.Hashtag;
import com.mycompany.capstoneproject.dto.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author apprentice
 */

public class HashtagDaoDbImpl implements HashtagDao {
        
    private JdbcTemplate jdbcTemplate;
    
    private UserDao userDao;

    private static final String SQL_INSERT_HASHTAG = "INSERT INTO hashtag (tagname) VALUES(?);";
    private static final String SQL_SELECT_HASHTAG = "SELECT * FROM hashtag WHERE id=?;";
    private static final String SQL_UPDATE_HASHTAG = "UPDATE hashtag SET tagname=? WHERE id=?;";
    private static final String SQL_DELETE_HASHTAG = "DELETE FROM hashtag WHERE id=?;";
    private static final String SQL_SELECT_ALL_HASHTAG = "SELECT * FROM hashtag ORDER BY tagname;";
    private static final String SQL_SELECT_BLOGPOSTS = "SELECT b.id, b.title, b.htmlcontent, b.postdate, b.expirationdate, b.useraccessid FROM blogpost_hashtag bh LEFT JOIN blogpost b ON bh.blogpostid = b.id WHERE bh.hashtagid=?;";
    private static final String SQL_SELECT_ACTIVE_BLOGPOSTS = "SELECT b.id, b.title, b.htmlcontent, b.postdate, b.expirationdate, b.useraccessid FROM blogpost_hashtag bh LEFT JOIN blogpost b ON bh.blogpostid = b.id WHERE bh.hashtagid=? AND (b.expirationdate > CURDATE() OR b.expirationdate IS NULL) AND b.hasBeenReviewed=1 AND b.postdate <= CURDATE();\n";
    private static final String SQL_SELECT_TAGS_WITH_ACTIVE_POSTS = "SELECT DISTINCT h.id, tagname, h.hasPosts FROM blogpost_hashtag bh LEFT JOIN blogpost b ON bh.blogpostid = b.id INNER JOIN hashtag h ON bh.hashtagid=h.id WHERE b.postdate <= CURDATE() AND (b.expirationdate > CURDATE() OR b.expirationdate IS NULL) AND b.hasBeenReviewed=1 AND (b.isDeleted=0 OR b.isDeleted IS NULL) ORDER BY tagname;";

    public HashtagDaoDbImpl(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public Hashtag create(Hashtag hashtag) {
        
        jdbcTemplate.update(SQL_INSERT_HASHTAG,
                hashtag.getTag());
        
        Integer hashtagId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
        
        hashtag.setId(hashtagId);
        
        return hashtag;
    }

    @Override
    public Hashtag read(Integer id) {
        
        try {
            Hashtag h = jdbcTemplate.queryForObject(SQL_SELECT_HASHTAG, new HashtagMapper(), id);
            return h;
        } catch(EmptyResultDataAccessException ex) {
            return null;
        }
        
    }

    @Override
    public void update(Hashtag hashtag) {
        
        jdbcTemplate.update(SQL_UPDATE_HASHTAG,
                hashtag.getTag(),
                hashtag.getId());

    }

    @Override
    public void delete(Hashtag hashtag) {
        
        jdbcTemplate.update(SQL_DELETE_HASHTAG, hashtag.getId());

    }

    @Override
    public List<Hashtag> list() {
        
        List<Hashtag> hashtagList = jdbcTemplate.query(SQL_SELECT_ALL_HASHTAG, new HashtagMapper());
        
        return hashtagList;
    }

    @Override
    public List<Hashtag> listTagsWithActivePosts() {

        List<Hashtag> tagsWithActivePosts = jdbcTemplate.query(SQL_SELECT_TAGS_WITH_ACTIVE_POSTS, new HashtagMapper());

        return tagsWithActivePosts;

    }

    private final class HashtagMapper implements RowMapper<Hashtag> {

        @Override
        public Hashtag mapRow(ResultSet resultSet, int i) throws SQLException {

            Hashtag hashtag = new Hashtag();

            hashtag.setId( resultSet.getInt("id") );
            hashtag.setTag( resultSet.getString("tagname") );
            
            List<BlogPost> blogPostList = jdbcTemplate.query(SQL_SELECT_ACTIVE_BLOGPOSTS, new BlogPostMapper(), hashtag.getId());
            
            hashtag.setPostsContainingTag( blogPostList );

            return hashtag;

        }
    }
    
    private final class BlogPostMapper implements RowMapper<BlogPost> {

        @Override
        public BlogPost mapRow(ResultSet resultSet, int i) throws SQLException {

            BlogPost blogPost = new BlogPost();

            blogPost.setId(resultSet.getInt("id"));
            blogPost.setHtmlContent(resultSet.getString("htmlcontent"));
            blogPost.setTitle(resultSet.getString("title"));
            blogPost.setPublishDate(resultSet.getDate("postdate"));
            blogPost.setExpirationDate(resultSet.getDate("expirationdate"));
            
            User user = userDao.read( resultSet.getInt("useraccessid") );
            
            blogPost.setUser(user);

            return blogPost;

        }
    }

}
