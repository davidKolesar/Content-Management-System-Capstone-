/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dao;

import com.mycompany.capstoneproject.dto.StaticPage;
import com.mycompany.capstoneproject.dto.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apprentice
 */
public class StaticPageDaoDbImpl implements StaticPageDao {

    private JdbcTemplate jdbcTemplate;

    private UserDao userDao;

    private static final String SQL_INSERT_STATICPAGE = "INSERT INTO staticpage (title, htmlcontent, postdate, expirationdate, useraccessid, deleted, hasBeenReviewed) VALUES(?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_SELECT_STATICPAGE = "SELECT * FROM staticpage WHERE id=?;";
    private static final String SQL_UPDATE_STATICPAGE = "UPDATE staticpage SET title=?, htmlcontent=?, postdate=?, expirationdate=?, useraccessid=?, deleted=?, hasBeenReviewed=? WHERE id=?;";
    private static final String SQL_DELETE_STATICPAGE = "DELETE FROM staticpage WHERE id=?;";
    private static final String SQL_SELECT_ALL_STATICPAGE = "SELECT * FROM staticpage;";
    private static final String SQL_SELECT_ALL_ACTIVE_STATICPAGE = "SELECT * FROM staticpage WHERE (deleted=0 OR deleted IS NULL) AND hasBeenReviewed=1;";

    public StaticPageDaoDbImpl(UserDao userDao, JdbcTemplate jdbcTemplate) {
        this.userDao = userDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public StaticPage create(StaticPage page) {

        jdbcTemplate.update(SQL_INSERT_STATICPAGE,
                page.getTitle(),
                page.getHtmlContent(),
                page.getDateToPost(),
                page.getExpirationDate(),
                page.getUser().getId(),
                page.isDeleted(),
                page.getHasBeenReviewed());

        Integer StaticPageId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);

        page.setId(StaticPageId);

        return page;
    }

    @Override
    public StaticPage read(Integer id) {
        try {
            StaticPage staticPage = jdbcTemplate.queryForObject(SQL_SELECT_STATICPAGE, new StaticPageMapper(), id);
            return staticPage;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public void update(StaticPage page) {

        jdbcTemplate.update(SQL_UPDATE_STATICPAGE,
                page.getTitle(),
                page.getHtmlContent(),
                page.getDateToPost(),
                page.getExpirationDate(),
                page.getUser().getId(),
                page.isDeleted(),
                page.getHasBeenReviewed(),
                page.getId());

    }

    @Override
    public void delete(StaticPage page) {

        jdbcTemplate.update(SQL_DELETE_STATICPAGE, page.getId());

    }

    @Override
    public List<StaticPage> list() {

        List<StaticPage> staticPageList = jdbcTemplate.query(SQL_SELECT_ALL_STATICPAGE, new StaticPageMapper());

        return staticPageList;

    }


        @Override
        public List<StaticPage> listActivePages() {

        List<StaticPage> staticPageList = jdbcTemplate.query(SQL_SELECT_ALL_ACTIVE_STATICPAGE, new StaticPageMapper());

            Date today = new Date();

            Iterator<StaticPage> s = staticPageList.iterator();
            while (s.hasNext()) {
                StaticPage p = s.next();

                if (p.getExpirationDate() == null) {
                } else if (p.getExpirationDate().compareTo(today) > 0) {
                } else if (p.getExpirationDate().compareTo(today) < 0) {
                    s.remove();
                } else if (p.getExpirationDate().compareTo(today) == 0) {
                    s.remove();
                }

                if (p.getDateToPost() == null) {
                } else if (p.getDateToPost().compareTo(today) > 0) {
                    s.remove();
                } else if (p.getDateToPost().compareTo(today) < 0) {
                } else if (p.getDateToPost().compareTo(today) == 0) {
                }

            }

            return staticPageList;
        }

    

    private final class StaticPageMapper implements RowMapper<StaticPage> {

        @Override
        public StaticPage mapRow(ResultSet resultSet, int i) throws SQLException {

            StaticPage staticPage = new StaticPage();

            Integer userId = resultSet.getInt("useraccessid");
            User user = userDao.read(userId);

            staticPage.setId(resultSet.getInt("id"));
            staticPage.setHtmlContent(resultSet.getString("htmlcontent"));
            staticPage.setTitle(resultSet.getString("title"));
            staticPage.setDateToPost(resultSet.getDate("postdate"));
            staticPage.setExpirationDate(resultSet.getDate("expirationdate"));
            staticPage.setUser(user);
            staticPage.setDeleted(resultSet.getBoolean("deleted"));
            staticPage.setHasBeenReviewed(resultSet.getBoolean("hasBeenReviewed"));

            return staticPage;

        }

    }
}
