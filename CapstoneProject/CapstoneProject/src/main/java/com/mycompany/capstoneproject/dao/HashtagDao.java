/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dao;

import com.mycompany.capstoneproject.dto.Hashtag;

import java.util.List;

/**
 *
 * @author apprentice
 */

public interface HashtagDao {

    public Hashtag create(Hashtag hashtag);

    public Hashtag read(Integer id);

    public void update(Hashtag hashtag);

    public void delete(Hashtag hashtag);

    public List<Hashtag> list();

    public List<Hashtag> listTagsWithActivePosts();
    
}
