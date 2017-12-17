package com.mycompany.capstoneproject.controllers;

import com.mycompany.capstoneproject.dao.StaticPageDao;
import com.mycompany.capstoneproject.dto.StaticPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by paulharding on 10/15/16.
 */

@Controller
public class LoginController {

    private StaticPageDao staticPageDao;

    @Inject
    public LoginController(StaticPageDao staticPageDao) {
        this.staticPageDao = staticPageDao;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String adminLogin(Map model) {

        List<StaticPage> staticPageList = staticPageDao.listActivePages();

        model.put("staticPageList", staticPageList);

        return "login";

    }

}
