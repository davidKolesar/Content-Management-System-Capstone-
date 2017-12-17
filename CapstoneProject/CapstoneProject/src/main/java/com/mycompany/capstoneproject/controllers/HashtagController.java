/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.capstoneproject.controllers;

import com.mycompany.capstoneproject.dao.HashtagDao;
import javax.inject.Inject;

import com.mycompany.capstoneproject.dto.BlogPost;
import com.mycompany.capstoneproject.dto.Hashtag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author apprentice
 */

@Controller
@RequestMapping(value="/hashtag")
public class HashtagController {

    private HashtagDao hashtagDao;

    @Inject
    public HashtagController(HashtagDao hashtagDao){
        this.hashtagDao=hashtagDao;
    }

    @RequestMapping(value="/viewposts/{id}", method= RequestMethod.GET)
    public String viewPostsByTag(@PathVariable("id") Integer id, Map model) {

        Hashtag hashtag = hashtagDao.read(id);

        List<BlogPost> blogPostList;
        try {
            blogPostList = hashtag.getPostsContainingTag();
        } catch (NullPointerException ex) {
            blogPostList = new ArrayList(); // I WANT THIS TO DO SOMETHNING ELSE (like show a JSP that says 'Tag not Found!')

        }

        model.put("blogPostList", blogPostList);
        model.put("hashtag", hashtag);

        return "showPostsByTag";

    }

}
