/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.controllers;

import com.mycompany.capstoneproject.dao.BlogPostCategoryDao;
import com.mycompany.capstoneproject.dao.BlogPostDao;
import com.mycompany.capstoneproject.dao.UserDao;
import com.mycompany.capstoneproject.dao.StaticPageDao;
import com.mycompany.capstoneproject.dto.AddBlogPostCommand;
import com.mycompany.capstoneproject.dto.BlogPost;
import com.mycompany.capstoneproject.dto.BlogPostCategory;
import com.mycompany.capstoneproject.dto.StaticPage;
import com.mycompany.capstoneproject.dto.User;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * @author apprentice
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private BlogPostDao blogpostdao;
    private BlogPostCategoryDao categoryDao;
    private UserDao userDao;
    private StaticPageDao staticPageDao;
    
    @Inject
    public AdminController(BlogPostDao blogpostdao, BlogPostCategoryDao categoryDao, StaticPageDao staticPageDao, UserDao userDao) {

        this.blogpostdao = blogpostdao;
        this.categoryDao = categoryDao;
        this.staticPageDao = staticPageDao;
        this.userDao = userDao;

    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String validateLogin(Map model) {

        List<StaticPage> staticPageList = staticPageDao.listActivePages();

        model.put("staticPageList", staticPageList);


        return "adminControlPanel";

    }

    @RequestMapping(value = "/createblog", method = RequestMethod.GET)
    public String addPost(Map model) {

        List<BlogPostCategory> categoryList = categoryDao.list();
        List<StaticPage> staticPageList = staticPageDao.listActivePages();

        model.put("staticPageList", staticPageList);
        model.put("addBlogPostCommand", new AddBlogPostCommand());
        model.put("categoryList", categoryList);

        return "createBlogPost";

    }

    @RequestMapping(value = "/editblog", method = RequestMethod.GET)
    public String editPost(Map model) {

        List<BlogPost> blogPostList = blogpostdao.list();
        List<BlogPostCategory> fullCategoryList = categoryDao.list();
        List<StaticPage> staticPageList = staticPageDao.listActivePages();

        model.put("staticPageList", staticPageList);
        model.put("blogPostList", blogPostList);
        model.put("categoryList", fullCategoryList);

        return "editBlogPost";

    }

    @RequestMapping(value = "/createpage", method = RequestMethod.GET)
    public String addPage(Map model) {

        List<StaticPage> staticPageList = staticPageDao.listActivePages();
        
        model.put("staticPageList", staticPageList);
        model.put("StaticPage", new StaticPage());

        return "createStaticPage";

    }

    @RequestMapping(value = "/editpage", method = RequestMethod.GET)
    public String editPage(Map model) {

        List<StaticPage> staticPageList = staticPageDao.listActivePages();

        model.put("staticPageList", staticPageList);

        return "editStaticPage";

    }
    
    @RequestMapping (value="/editusers", method = RequestMethod.GET)
    public String newUser(Map model){
        
        List<User> userList = userDao.list();
        
        model.put("userList", userList);
        
        return "usersList";
    }

    @RequestMapping(value="/reviewposts", method=RequestMethod.GET)
    public String reviewPosts(Map model) {

        List<BlogPost> blogPostsNeedingReview = blogpostdao.listPostsNeedingReview();

        model.put("blogPostsToReview", blogPostsNeedingReview);

        return "reviewPosts";

    }

}
