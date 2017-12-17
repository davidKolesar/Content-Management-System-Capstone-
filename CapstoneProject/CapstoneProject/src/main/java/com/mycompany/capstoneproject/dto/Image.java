/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dto;

/**
 *
 * @author apprentice
 */
public class Image {

    Integer id;
    Image pic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Image getImage() {
        return pic;
    }

    public void setImage(Image image) {
        this.pic = image;
    }
    
}
