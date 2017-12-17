/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.controllers;

import com.mycompany.capstoneproject.dao.ImageDao;
import com.mycompany.capstoneproject.dto.Image;
import java.util.Map;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author apprentice
 */
@Controller
@RequestMapping(value = "/image")
public class ImageController {

    private ImageDao imagedao;
    
    
    @Inject
    ImageController(ImageDao imagedao) {
        
        this.imagedao = imagedao;
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Image addCategory(@RequestBody Image image, Map model) {

      
     image = imagedao.create(image);
            
        return image;
    }

}
