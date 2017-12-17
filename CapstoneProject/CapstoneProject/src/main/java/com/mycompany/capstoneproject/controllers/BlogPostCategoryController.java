/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.controllers;

import com.mycompany.capstoneproject.dao.BlogPostCategoryDao;
import com.mycompany.capstoneproject.dto.BlogPost;
import com.mycompany.capstoneproject.dto.BlogPostCategory;
import javax.inject.Inject;
import javax.validation.Valid;

import com.mycompany.capstoneproject.viewModels.AddBlogPostCategoryViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author apprentice
 */
@Controller
@RequestMapping(value = "/blogpostcategory")
public class BlogPostCategoryController {

    private BlogPostCategoryDao blogPostCategoryDao;

    @Inject
    public BlogPostCategoryController(BlogPostCategoryDao blogPostCategoryDao) {
        this.blogPostCategoryDao = blogPostCategoryDao;
    }

    
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public AddBlogPostCategoryViewModel addCategory(@Valid @RequestBody BlogPostCategory category) {

        category = blogPostCategoryDao.create(category);

        List<BlogPostCategory> fullCategoryList = blogPostCategoryDao.list();

        AddBlogPostCategoryViewModel viewModel = new AddBlogPostCategoryViewModel();
        viewModel.setCategory(category);
        viewModel.setFullCategoryList(fullCategoryList);

        return viewModel;
    }
    
    

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public BlogPostCategory removeCategory(@RequestBody BlogPostCategory category) {

       blogPostCategoryDao.delete(category);
       
       return category;

    }
    
    

    @RequestMapping(value = "/viewposts/{id}", method = RequestMethod.GET)
    public String viewCategory(@PathVariable("id") Integer id, Map model) {

        BlogPostCategory category = blogPostCategoryDao.read(id);

        List<BlogPost> blogPostList;
        try {
            blogPostList = category.getBlogposts();
        } catch (NullPointerException ex) {
            blogPostList = new ArrayList(); // I WANT SOMETHING ELSE HERE (ex. JSP "Category Not Found!")
        }

        model.put("blogPostList", blogPostList);
        model.put("category", category);

        return "showPostsByCategory";

    }
    
    

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public BlogPostCategory updateCategory(@RequestBody BlogPostCategory category) {

        blogPostCategoryDao.update(category);

        return category;
    }

}
