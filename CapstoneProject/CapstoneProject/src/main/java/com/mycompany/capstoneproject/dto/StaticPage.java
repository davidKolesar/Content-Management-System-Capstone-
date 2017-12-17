/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author apprentice
 */
public class StaticPage {
    
    private Integer id;
    
    @NotEmpty(message="Please enter page title")
    @Length(max=200, message="title can only contain 200 characters")
    private String title;
    
    @NotEmpty(message="Html Content can not be empty")
    @Length(max=20000, message="You are only limited to 20000 characters")
    private String htmlContent;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="America/Phoenix")
    private Date dateToPost;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="America/Phoenix")
    private Date expirationDate;

    private User user;

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

    public Date getDateToPost() {
        return dateToPost;
    }

    public void setDateToPost(Date dateToPost) {
        this.dateToPost = dateToPost;
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
