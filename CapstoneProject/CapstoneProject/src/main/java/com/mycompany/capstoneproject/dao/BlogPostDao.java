/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dao;

import com.mycompany.capstoneproject.dto.BlogPost;

import java.util.List;

/**
 *
 * @author apprentice
 */
public interface BlogPostDao {

    public BlogPost create(BlogPost post);

    public BlogPost read(Integer id);

    public void update(BlogPost post);

    public void delete(BlogPost post);

    public List<BlogPost> list();

    public List<BlogPost> listActivePosts();

    public List<BlogPost> listPostsNeedingReview();
    
}
