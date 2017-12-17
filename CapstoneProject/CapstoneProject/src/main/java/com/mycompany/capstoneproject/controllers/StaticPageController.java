/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.controllers;

import com.mycompany.capstoneproject.dao.StaticPageDao;
import com.mycompany.capstoneproject.dao.UserDao;
import com.mycompany.capstoneproject.dto.StaticPage;
import com.mycompany.capstoneproject.dto.User;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author apprentice
 */
@Controller
@RequestMapping(value="/staticpage")
public class StaticPageController {
    
    private StaticPageDao staticPageDao;
    private UserDao userDao;
    
    @Inject
    public StaticPageController(StaticPageDao staticPageDao, UserDao userDao){
        this.staticPageDao = staticPageDao;
        this.userDao = userDao;
    }
    

    @RequestMapping(value="", method=RequestMethod.POST)
    public String addPage(@Valid @ModelAttribute StaticPage staticPage, BindingResult result, Map model){

        if (staticPage.getExpirationDate() != null) {

            if (staticPage.getExpirationDate().compareTo(staticPage.getDateToPost()) < 0) {
                return "redirect:/admin/createpage/";
            }

        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User postAuthor = userDao.searchByUserName(username);
        staticPage.setUser(postAuthor);

        List<String> authorRoles = userDao.listUserRoles(postAuthor);

        if (authorRoles.contains("ROLE_ADMIN")) {
            staticPage.setHasBeenReviewed(true);
        } else {
            staticPage.setHasBeenReviewed(false);
        }

        if (staticPage.getDateToPost() == null) {
            staticPage.setDateToPost(new Date());
        }

        StaticPage addedPage = staticPageDao.create(staticPage);

        return "redirect:/staticpage/viewStaticPage/" + addedPage.getId();
        
    }
    
    
    
    @RequestMapping(value="/viewStaticPage/{id}", method=RequestMethod.GET) 
    public String showPage (@PathVariable ("id") Integer id, Map model) {

        List<StaticPage> activePages = staticPageDao.listActivePages();
        
        StaticPage sp = staticPageDao.read(id);
        
        model.put("StaticPage", sp);
        model.put("staticPageList", activePages);
        
        return "genericStaticPage" ;
        
    }
            
            
            
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ResponseBody
    public StaticPage viewPost(@PathVariable ("id") Integer id){
        
        StaticPage sp = staticPageDao.read(id);
        
        return sp;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public StaticPage updatePage(@RequestBody StaticPage page){

        if (page.getExpirationDate() != null) {

            if (page.getExpirationDate().compareTo(page.getDateToPost()) < 0) {
                return page;
            }

        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User postAuthor = userDao.searchByUserName(username);
        page.setUser(postAuthor);

        List<String> authorRoles = userDao.listUserRoles(postAuthor);

        if (authorRoles.contains("ROLE_ADMIN")) {
            page.setHasBeenReviewed(true);
        } else {
            page.setHasBeenReviewed(false);
        }

        if (page.getDateToPost() == null) {
            page.setDateToPost(new Date());
        }

        staticPageDao.update(page);
        
        return page;
    }
    
    
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public StaticPage delete(@RequestBody Integer Id){
        
        StaticPage page = staticPageDao.read(Id);
        
        staticPageDao.delete(page);
        
        return page;
    }
    
    
    
    
    
}
