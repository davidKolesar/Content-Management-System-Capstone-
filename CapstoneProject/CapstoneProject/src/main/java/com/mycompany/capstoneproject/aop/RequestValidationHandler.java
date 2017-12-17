/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.aop;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author apprentice
 */
@ControllerAdvice
public class RequestValidationHandler {
    
    @ExceptionHandler (MethodArgumentNotValidException.class)
    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorContainer processValidationErrors(MethodArgumentNotValidException ex){
        
        BindingResult result = ex.getBindingResult();
        
        List<FieldError> fieldErrors = result.getFieldErrors();
        
        ValidationErrorContainer container = new ValidationErrorContainer();
        
        for (FieldError error : fieldErrors){
            
            ValidationError vError = new ValidationError();
            vError.setFieldName(error.getField());
            vError.setErrorMessage(error.getDefaultMessage());
            container.addError(vError);
            
        }
        return container;
        
    }
}
