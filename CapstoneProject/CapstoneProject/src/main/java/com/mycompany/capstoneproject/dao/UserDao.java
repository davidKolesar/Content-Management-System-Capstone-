/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dao;

import com.mycompany.capstoneproject.dto.User;

import java.util.List;

/**
 *
 * @author apprentice
 */
public interface UserDao {

    public User create(User user);

    public User read(Integer id);

    public void update(User user);

    public void delete(User user);

    public List<User> list();

    public User searchByUserName(String username);

    public List<String> listUserRoles(User user);
    
}
