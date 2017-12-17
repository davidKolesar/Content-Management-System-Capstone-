/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dao;

import com.mycompany.capstoneproject.dto.BlogPost;
import com.mycompany.capstoneproject.dto.BlogPostCategory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apprentice
 */
public class BlogPostCategoryDaoDbImpl implements BlogPostCategoryDao {
    
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_BLOGPOSTCATEGORY = "INSERT INTO categoryblogpost (nametag) VALUES(?);";
    private static final String SQL_SELECT_BLOGPOSTCATEGORY = "SELECT * FROM categoryblogpost WHERE id=?;";
    private static final String SQL_UPDATE_BLOGPOSTCATEGORY = "UPDATE categoryblogpost SET nametag=? WHERE id=?;";
    private static final String SQL_DELETE_BLOGPOSTCATEGORY = "DELETE FROM categoryblogpost WHERE id=?;";
    private static final String SQL_SELECT_ALL_BLOGPOSTCATEGORY = "SELECT * FROM categoryblogpost ORDER BY nametag;";
    private static final String SQL_SELECT_BLOGPOSTS = "SELECT b.id, b.title, b.htmlcontent, b.postdate, b.expirationdate, b.useraccessid FROM categoryblogpost_blogpost cb LEFT JOIN blogpost b ON cb.blogpostid = b.id WHERE cb.categoryblogpostid=?;";
    private static final String SQL_SELECT_ACTIVE_BLOGPOSTS = "SELECT b.id, b.title, b.htmlcontent, b.postdate, b.expirationdate, b.useraccessid FROM categoryblogpost_blogpost cb LEFT JOIN blogpost b ON cb.blogpostid = b.id WHERE cb.categoryblogpostid=? AND (b.expirationdate > CURDATE() OR b.expirationdate IS NULL) AND b.hasBeenReviewed=1 AND b.postdate <= CURDATE();\n";
    private static final String SQL_DELETE_BLOGPOST_RELATIONSHIPS = "DELETE FROM categoryblogpost_blogpost WHERE categoryblogpostid=?;";
    private static final String SQL_SELECT_CATEGORIES_WITH_ACTIVE_POSTS = "SELECT DISTINCT c.id, c.nametag FROM categoryblogpost_blogpost cb INNER JOIN blogpost b ON cb.blogpostid = b.id INNER JOIN categoryblogpost c ON cb.categoryblogpostid = c.id WHERE b.postdate <= CURDATE() AND (b.expirationdate > CURDATE() OR b.expirationdate IS NULL) AND b.hasBeenReviewed=1 AND (b.isDeleted=0 OR b.isDeleted IS NULL) ORDER BY nametag;";


    public BlogPostCategoryDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BlogPostCategory create(BlogPostCategory category) {
        
        jdbcTemplate.update(SQL_INSERT_BLOGPOSTCATEGORY,
                category.getCategoryName());
        
        Integer categoryId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
        
        category.setId(categoryId);
        
        return category;
    }

    @Override
    public BlogPostCategory read(Integer id) {

        try {

            BlogPostCategory c = jdbcTemplate.queryForObject(SQL_SELECT_BLOGPOSTCATEGORY, new BlogPostCategoryMapper(), id);
            return c;

        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    @Override
    public void update(BlogPostCategory category) {

        jdbcTemplate.update(SQL_UPDATE_BLOGPOSTCATEGORY,
                category.getCategoryName(),
                category.getId());

    }

    @Override
    public void delete(BlogPostCategory category) {

        jdbcTemplate.update(SQL_DELETE_BLOGPOST_RELATIONSHIPS, category.getId()); // categoryblogpost is a foreign key

        jdbcTemplate.update(SQL_DELETE_BLOGPOSTCATEGORY, category.getId());

    }

    @Override
    public List<BlogPostCategory> list() {

        List<BlogPostCategory> categoryList = jdbcTemplate.query(SQL_SELECT_ALL_BLOGPOSTCATEGORY, new BlogPostCategoryMapper());

        return categoryList;

    }

    @Override
    public List<BlogPostCategory> listCategoriesWithActivePosts() {

        List<BlogPostCategory> categoriesWithActivePosts = jdbcTemplate.query(SQL_SELECT_CATEGORIES_WITH_ACTIVE_POSTS, new BlogPostCategoryMapper());

        return categoriesWithActivePosts;
    }

    private final class BlogPostCategoryMapper implements RowMapper<BlogPostCategory> {

        @Override
        public BlogPostCategory mapRow(ResultSet resultSet, int i) throws SQLException {

            BlogPostCategory category = new BlogPostCategory();

            category.setId( resultSet.getInt("id") );
            category.setCategoryName( resultSet.getString("nametag") );

            
            List<BlogPost> blogPostList = jdbcTemplate.query(SQL_SELECT_ACTIVE_BLOGPOSTS, new BlogPostMapper(), category.getId());
            
            category.setBlogposts(blogPostList);


            return category;

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

            return blogPost;

        }
    }

}
