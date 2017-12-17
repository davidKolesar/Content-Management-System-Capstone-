/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.controllers;

import com.mycompany.capstoneproject.dao.BlogPostCategoryDao;
import com.mycompany.capstoneproject.dao.BlogPostDao;
import com.mycompany.capstoneproject.dao.HashtagDao;
import com.mycompany.capstoneproject.dao.UserDao;
import com.mycompany.capstoneproject.dto.*;

import java.security.Principal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author apprentice
 */
@Controller
@RequestMapping(value = "/blogpost")
public class BlogPostController {

    private UserDao userDao;
    private BlogPostDao blogPostDao;
    private HashtagDao hashtagDao;
    private BlogPostCategoryDao categoryDao;

    @Inject
    public BlogPostController(UserDao userDao, BlogPostDao blogPostDao, HashtagDao hashtagDao, BlogPostCategoryDao categoryDao) {
        this.userDao = userDao;
        this.blogPostDao = blogPostDao;
        this.hashtagDao = hashtagDao;
        this.categoryDao = categoryDao;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String addPost(@Valid @ModelAttribute AddBlogPostCommand blogPostCommand, BindingResult result, Map model) {

        if (result.hasErrors()) {

            List<BlogPostCategory> categoryList = categoryDao.list();

            model.put("addBlogPostCommand", blogPostCommand);
            model.put("categoryList", categoryList);

            return "createBlogPost";

        }

        if (!(blogPostCommand.getExpirationDate() == null)) {

            if (blogPostCommand.getExpirationDate().compareTo(blogPostCommand.getPublishDate()) < 0) {
                return "redirect:/admin/createblog";
            }

        }

        BlogPost post = convertAddCommandToBlogPost(blogPostCommand);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User postAuthor = userDao.searchByUserName(username);
        post.setUser(postAuthor);

        List<String> authorRoles = userDao.listUserRoles(postAuthor);

        if (authorRoles.contains("ROLE_ADMIN")) {
            post.setHasBeenReviewed(true);
        } else {
            post.setHasBeenReviewed(false);
        }

        post = blogPostDao.create(post);

        return "redirect:/blogpost/read/" + post.getId();

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BlogPost viewPost(@PathVariable("id") Integer id) {

        BlogPost post = blogPostDao.read(id);

        return post;

    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public String viewPost(@PathVariable Integer id, Map model) {

        BlogPost post = blogPostDao.read(id);

        model.put("blogPost", post);

        return "viewBlogPost";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public BlogPost updatePost(@Valid @RequestBody EditBlogPostCommand commandObject) {

        BlogPost post = convertEditCommandToBlogPost(commandObject);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User postAuthor = userDao.searchByUserName(username);
        post.setUser(postAuthor);

        List<String> authorRoles = userDao.listUserRoles(postAuthor);

        if (authorRoles.contains("ROLE_ADMIN")) {
            post.setHasBeenReviewed(true);
        } else {
            post.setHasBeenReviewed(false);
        }

        if (post.getExpirationDate() != null) {

            if (post.getExpirationDate().compareTo(post.getPublishDate()) < 0) {
                return post;
            }

        }

        blogPostDao.update(post);

        return post;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public BlogPost delete(@PathVariable("id") Integer id) {

        BlogPost post = blogPostDao.read(id);

        blogPostDao.delete(post);

        return post;

    }

    @RequestMapping(value="/approve/{id}", method=RequestMethod.POST)
    @ResponseBody
    public BlogPost approvePost(@PathVariable("id") Integer id) {

        BlogPost post = blogPostDao.read(id);

        post.setHasBeenReviewed(true);

        blogPostDao.update(post);

        return post;

    }

    @RequestMapping(value="/reject/{id}", method=RequestMethod.POST)
    @ResponseBody
    public BlogPost rejectPost(@PathVariable("id") Integer id) {

        BlogPost post = blogPostDao.read(id);

        blogPostDao.delete(post);

        return post;

    }

    private BlogPost convertAddCommandToBlogPost(@RequestBody AddBlogPostCommand blogPostCommand) {

        BlogPost newBlogPost = new BlogPost();

        List<Hashtag> tagList = getHashtagsFromHTML(blogPostCommand.getHtmlContent());
        List<BlogPostCategory> categoryList = getCategoryListFromCommand(blogPostCommand.getBlogPostCategory());

        newBlogPost.setTitle(blogPostCommand.getTitle());
        newBlogPost.setHtmlContent(blogPostCommand.getHtmlContent());

        if (blogPostCommand.getPublishDate() == null) {
            newBlogPost.setPublishDate(new Date());
        } else {
            newBlogPost.setPublishDate(blogPostCommand.getPublishDate());
        }
        newBlogPost.setExpirationDate(blogPostCommand.getExpirationDate());

        newBlogPost.setBlogPostCategories(categoryList);
        newBlogPost.setTagsWithinPost(tagList);

        return newBlogPost;

    }

    private BlogPost convertEditCommandToBlogPost(EditBlogPostCommand commandObject) {

        BlogPost newBlogPost = new BlogPost();

        List<Hashtag> tagList = getHashtagsFromHTML(commandObject.getHtmlContent());
        List<Integer> categoryIdList = commandObject.getCategoryIds();

        List<BlogPostCategory> categoryList = new ArrayList();

        for (Integer i : categoryIdList) {
            BlogPostCategory category = categoryDao.read(i);
            categoryList.add(category);
        }

        newBlogPost.setId(commandObject.getId());

        newBlogPost.setBlogPostCategories(categoryList);

        newBlogPost.setTitle(commandObject.getTitle());
        newBlogPost.setHtmlContent(commandObject.getHtmlContent());

        if (commandObject.getPublishDate() == null) {
            newBlogPost.setPublishDate(new Date());
        } else {
            newBlogPost.setPublishDate(commandObject.getPublishDate());
        }
        newBlogPost.setExpirationDate(commandObject.getExpirationDate());

        newBlogPost.setBlogPostCategories(categoryList);
        newBlogPost.setTagsWithinPost(tagList);

        return newBlogPost;

    }

    public List<Hashtag> getHashtagsFromHTML(String htmlContent) {

        List<Hashtag> hashtagsInPostList = new ArrayList();

        Pattern regEx = Pattern.compile("#(\\w+)");

        Matcher matcher = regEx.matcher(htmlContent);

        List<String> tags = new ArrayList();

        while (matcher.find()) {
            tags.add(matcher.group(1));
        }

        for (String s : tags) {

            boolean sIsAlreadyInDatabase = false;
            Integer existingHashtagId = 0;

            List<Hashtag> fullHashtagList = hashtagDao.list();
            for (Hashtag h : fullHashtagList) {
                if (("#" + s).equals(h.getTag())) {
                    sIsAlreadyInDatabase = true;
                    existingHashtagId = h.getId();
                }
            }

            if (sIsAlreadyInDatabase) {

                boolean sIsAlreadyInPostList = false;
                for (Hashtag h : hashtagsInPostList) {
                    if (("#" + s).equals(h.getTag())) {
                        sIsAlreadyInPostList = true;
                    }
                }

                if (!sIsAlreadyInPostList) {
                    Hashtag existingHashtag = hashtagDao.read(existingHashtagId);
                    hashtagsInPostList.add(existingHashtag);
                }

            } else {

                Hashtag hashtag = new Hashtag();
                hashtag.setTag("#" + s);
                Hashtag newHashtag = hashtagDao.create(hashtag);
                hashtagsInPostList.add(newHashtag);

            }

        }

        return hashtagsInPostList;

    }

    public List<BlogPostCategory> getCategoryListFromCommand(String categoryListString) {

        List<BlogPostCategory> categoryList = new ArrayList();

        try {

            String[] categoryStrings = categoryListString.split(",");

            for (String s : categoryStrings) {
                Integer categoryId = Integer.parseInt(s);
                BlogPostCategory category = categoryDao.read(categoryId);
                categoryList.add(category);
            }

        } catch (NullPointerException ex) {

        }

        return categoryList;

    }

    public boolean getActiveStatus(BlogPost post) {

        /* Returns true if active, false if not */

        boolean postIsActive = true;

        Date today = new Date();

        // Check the publish date
        if(post.getPublishDate() == null) {
            // still true
        } else if (post.getPublishDate().compareTo(today) >= 0) {
            postIsActive = false;
        }

        // Check the expiration date
        if (post.getExpirationDate() == null) {
            // still true
        } else if (post.getExpirationDate().compareTo(today) <= 0) {
            postIsActive = false;
        }

        // Check if the post has been reviewed by admin
        if ( !post.getHasBeenReviewed() ) {
            postIsActive = false;
        }

        // Check if the post has been deleted
        if ( post.isDeleted() ) {
            postIsActive = false;
        }

        return postIsActive;

    }

}
