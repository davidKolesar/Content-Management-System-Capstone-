/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dao;

import com.mycompany.capstoneproject.dto.StaticPage;

import java.util.List;

/**
 *
 * @author apprentice
 */
public interface StaticPageDao {

    public StaticPage create(StaticPage page);

    public StaticPage read(Integer id);

    public void update(StaticPage page);

    public void delete(StaticPage page);

    public List<StaticPage> list();

    public List<StaticPage> listActivePages();
    
}
