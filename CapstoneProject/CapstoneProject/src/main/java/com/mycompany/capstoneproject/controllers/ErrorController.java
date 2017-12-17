package com.mycompany.capstoneproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by paulharding on 10/17/16.
 */
@Controller
public class ErrorController {

    @RequestMapping(value="/error", method= RequestMethod.GET)
    public String error() {
        return "error";
    }

}
