/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dao;

import com.mycompany.capstoneproject.dto.Image;
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

public class ImageDaoDbImpl implements ImageDao {

    private JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_USER = "INSERT INTO image (id, image) VALUES(?, ?);";
    
    public ImageDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Image create(Image image) {
        
        jdbcTemplate.update(SQL_INSERT_USER,
                image.getImage());
        
        Integer imageId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);

        image.setId(imageId);

        return image;
    }
}