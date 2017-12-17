/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.controllers;

import com.mycompany.capstoneproject.dao.UserDao;
import com.mycompany.capstoneproject.dto.User;
import com.mycompany.capstoneproject.dto.UserCommand;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



// Randall told us that the client now wants to be able to 
//create new users,
//update existing users, and delete existing users.
/**
 *
 * @author apprentice
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private UserDao userDao;

    private PasswordEncoder encoder;

    @Inject
    public UserController(UserDao userDao, PasswordEncoder encoder)
    {
        this.userDao = userDao;
        this.encoder = encoder;
    }
 
    
    @RequestMapping(value="", method= RequestMethod.POST)
    @ResponseBody
    public User createNewUser(@Valid @RequestBody User newUser) {

        List<User> fullUserList = userDao.list();

        for (User u : fullUserList) {
            if ( newUser.getUserName().equals(u.getUserName()) ) {
                return null;
            }
        }

        newUser.setEnabled(1);

        String clearPassword = newUser.getPassword();
        String hashedPassword = encoder.encode(clearPassword);

        newUser.setPassword(hashedPassword);
       
        User u = userDao.create(newUser);

        return u;
       
    }
     
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ResponseBody
    public User read(@PathVariable("id") Integer userId ) {

        User viewUser = userDao.read(userId);
        
        return viewUser;
    }
    
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public User updateExistingUsers(@RequestBody User oldUser)  {

        oldUser.setEnabled(1);

        String clearPassword = oldUser.getPassword();
        String hashedPassword = encoder.encode(clearPassword);

        oldUser.setPassword(hashedPassword);
        
        userDao.update(oldUser);
        
        return oldUser;
    }
    
    
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public User deleteExisitingUsers(@PathVariable("id") Integer id)
    {
        User deleteUser = userDao.read(id);
        
        userDao.delete(deleteUser);
        
        return deleteUser;
        
    }        

}
