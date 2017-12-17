/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dao;


import com.mycompany.capstoneproject.dto.BlogPostCategory;

import java.util.List;

/**
 *
 * @author apprentice
 */

public interface BlogPostCategoryDao {

    public BlogPostCategory create(BlogPostCategory category);

    public BlogPostCategory read(Integer id);

    public void update(BlogPostCategory category);

    public void delete(BlogPostCategory category);

    public List<BlogPostCategory> list();

    public List<BlogPostCategory> listCategoriesWithActivePosts();
    
}
