/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dto;

import java.util.List;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;


/**
 *
 * @author apprentice
 */
public class BlogPostCategory {

    private Integer id;
    
    @NotEmpty(message="Please enter category name")
    @Length(max=45, message="Category name can only contain 45 characters")
    private String categoryName;

    private List<BlogPost> blogposts;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<BlogPost> getBlogposts() {
        return blogposts;
    }

    public void setBlogposts(List<BlogPost> blogposts) {
        this.blogposts = blogposts;
    }
    
}
