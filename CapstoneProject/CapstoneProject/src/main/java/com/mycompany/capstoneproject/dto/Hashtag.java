/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dto;

import java.util.List;

/**
 *
 * @author apprentice
 */
public class Hashtag {

    private Integer id;
    private String tag;
    private List<BlogPost> postsContainingTag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<BlogPost> getPostsContainingTag() {
        return postsContainingTag;
    }

    public void setPostsContainingTag(List<BlogPost> postsContainingTag) {
        this.postsContainingTag = postsContainingTag;
    }

}
