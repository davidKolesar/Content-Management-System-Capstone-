/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *
 * @author TPD
 */

public class AddBlogPostCommand {


    @NotEmpty(message= "Please supply HTML Contents")
    @Length(max = 20000, message = "HTML content is limited to 20,000 characters")
    private String htmlContent;

    @NotEmpty(message= "Please supply Blog Title")
    @Length(max = 200, message = "Blog Title limit is 200 characters")
    private String title;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="America/Phoenix")
    private Date publishDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="America/Phoenix")
    private Date expirationDate;

    @Length(max=45, message="Category name can only contain 45 characters")
    private String blogPostCategory;



    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getBlogPostCategory() {
        return blogPostCategory;
    }

    public void setBlogPostCategory(String blogPostCategory) {
        this.blogPostCategory = blogPostCategory;
    }
}
