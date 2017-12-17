/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.aop;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author apprentice
 */
public class ValidationErrorContainer {

    private List<ValidationError> errors = new ArrayList();
    
    

    public List<ValidationError> getErrors() {
        return errors;
    }

    
    public void addError(ValidationError error) {
        errors.add(error);
    }
    
    
    
}

