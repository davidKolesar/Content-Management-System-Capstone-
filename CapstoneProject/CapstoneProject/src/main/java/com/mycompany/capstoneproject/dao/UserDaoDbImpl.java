/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dao;

import com.mycompany.capstoneproject.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apprentice
 */

public class UserDaoDbImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_USER = "INSERT INTO useraccess (username, password, enabled) VALUES(?, ?, ?);";
    private static final String SQL_INSERT_AUTHORITY = "INSERT INTO authorities (username, authority) VALUES(?, ?);";
    private static final String SQL_SELECT_USER = "SELECT * FROM useraccess WHERE user_id=?;";
    private static final String SQL_UPDATE_USER = "UPDATE useraccess SET username=?, password=?, enabled=? WHERE user_id =?;";
    private static final String SQL_DELETE_USER = "DELETE FROM useraccess WHERE user_id=?;";
    private static final String SQL_DELETE_AUTHORITIES = "DELETE FROM authorities WHERE username=?;";
    private static final String SQL_SELECT_ALL_USER = "SELECT * FROM useraccess;";
    private static final String SQL_SELECT_BY_USERNAME = "SELECT * FROM useraccess WHERE username=?;";
    private static final String SQL_GET_USER_ROLES = "SELECT authority FROM useraccess u INNER JOIN authorities a ON u.username = a.username WHERE u.user_id=?;";

    public UserDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User create(User user) {
        
        jdbcTemplate.update(SQL_INSERT_USER,
                user.getUserName(),
                user.getPassword(),
                user.getEnabled());

        Integer userId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);

        user.setId(userId);

        jdbcTemplate.update(SQL_INSERT_AUTHORITY, user.getUserName(), "ROLE_USER");
        jdbcTemplate.update(SQL_INSERT_AUTHORITY, user.getUserName(), user.getRoleName());
        
        return user;
    }

    @Override
    public User read(Integer id) {

        try {
            User u = jdbcTemplate.queryForObject(SQL_SELECT_USER, new UserMapper(), id);
            return u;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
        
    }

    @Override
    public void update(User user) {
        
        jdbcTemplate.update(SQL_UPDATE_USER,
                user.getUserName(),
                user.getPassword(),
                user.getEnabled(),
                user.getId());

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(User user) {

        jdbcTemplate.update(SQL_DELETE_AUTHORITIES, user.getUserName());
        jdbcTemplate.update(SQL_DELETE_USER, user.getId());

    }

    @Override
    public List<User> list() {

        List<User> userList = jdbcTemplate.query(SQL_SELECT_ALL_USER, new UserMapper());

        return userList;
    }

    @Override
    public User searchByUserName(String username) {

        try {
            User u = jdbcTemplate.queryForObject(SQL_SELECT_BY_USERNAME, new UserMapper(), username);
            return u;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    @Override
    public List<String> listUserRoles(User user) {

        List<String> userRoles = jdbcTemplate.queryForList(SQL_GET_USER_ROLES, String.class, user.getId());

        return userRoles;
    }

    private final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {

            User user = new User();

            user.setId( resultSet.getInt("user_id") );
            user.setUserName( resultSet.getString("username") );
            user.setPassword( resultSet.getString("password") );
            user.setEnabled(resultSet.getInt("enabled"));

            List<String> userRoleList = jdbcTemplate.queryForList(SQL_GET_USER_ROLES, String.class, user.getId());
            userRoleList.remove("ROLE_USER");

            String role = "";
            if (userRoleList.size() > 0) {
                role = userRoleList.get(0);
            }

            user.setRoleName(role);

            return user;

        }
    }

}
