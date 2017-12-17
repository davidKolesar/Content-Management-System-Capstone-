

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.controllers;

import com.mycompany.capstoneproject.dao.BlogPostCategoryDao;
import com.mycompany.capstoneproject.dao.BlogPostDao;
import com.mycompany.capstoneproject.dao.HashtagDao;
import com.mycompany.capstoneproject.dao.StaticPageDao;
import com.mycompany.capstoneproject.dao.UserDao;
import com.mycompany.capstoneproject.dto.AddBlogPostCommand;
import com.mycompany.capstoneproject.dto.BlogPost;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

import com.mycompany.capstoneproject.dto.BlogPostCategory;
import com.mycompany.capstoneproject.dto.Hashtag;
import com.mycompany.capstoneproject.dto.StaticPage;

import java.util.Iterator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author apprentice
 */
@Controller
public class HomeController {

    private BlogPostCategoryDao blogpostcategorydao;
    private BlogPostDao blogpostdao;
    private HashtagDao hashtagdao;
    private StaticPageDao staticpagedao;
    private UserDao userDao;

    @Inject
    public HomeController(BlogPostCategoryDao blogpostcategorydao, BlogPostDao blogpostdao, HashtagDao hashtagdao, StaticPageDao staticpagedao, UserDao userDao) {
        this.blogpostcategorydao = blogpostcategorydao;
        this.blogpostdao = blogpostdao;
        this.hashtagdao = hashtagdao;
        this.staticpagedao = staticpagedao;
        this.userDao = userDao;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Map model) {

        List<BlogPost> BlogPostList = blogpostdao.listActivePosts();
        List<Hashtag> hashtagList = hashtagdao.listTagsWithActivePosts();
        List<BlogPostCategory> categoryList = blogpostcategorydao.listCategoriesWithActivePosts();
        List<StaticPage> staticPageList = staticpagedao.listActivePages();

        model.put("BlogPostList", BlogPostList);
        model.put("HashtagList", hashtagList);
        model.put("CategoryList", categoryList);
        model.put("staticPageList", staticPageList);

        return "home";

    }

}
