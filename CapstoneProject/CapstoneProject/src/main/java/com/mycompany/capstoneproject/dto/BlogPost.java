/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author apprentice
 */

public class BlogPost {

    private Integer id;

    @NotEmpty(message= "Please supply Blog Title")
    @Length(max = 200, message = "Blog Title limit is 200 characters")
    private String title;
    
    @NotEmpty(message= "Please supply HTML Contents")
    @Length(max = 20000, message = "HTML content is limited to 20,000 characters")
    private String htmlContent;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="America/Phoenix")
    private Date publishDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="America/Phoenix")
    private Date expirationDate;

    private User user;

    private List<BlogPostCategory> blogPostCategories;

    private List<Hashtag> tagsWithinPost;

    private boolean isDeleted;

    private boolean hasBeenReviewed;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BlogPostCategory> getBlogPostCategories() {
        return blogPostCategories;
    }

    public void setBlogPostCategories(List<BlogPostCategory> blogPostCategories) {
        this.blogPostCategories = blogPostCategories;
    }

    public List<Hashtag> getTagsWithinPost() {
        return tagsWithinPost;
    }

    public void setTagsWithinPost(List<Hashtag> tagsWithinPost) {
        this.tagsWithinPost = tagsWithinPost;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean getHasBeenReviewed() {
        return hasBeenReviewed;
    }

    public void setHasBeenReviewed(boolean hasBeenReviewed) {
        this.hasBeenReviewed = hasBeenReviewed;
    }

}
