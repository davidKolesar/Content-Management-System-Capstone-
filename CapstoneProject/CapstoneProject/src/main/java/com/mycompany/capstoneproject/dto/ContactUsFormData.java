/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author apprentice
 */
public class ContactUsFormData {
    
    private String InputName;
    private String InputEmail;
    private String InputNote;
//    private MultipartFile InputFile;        

    /**
     * @return the InputName
     */
    public String getInputName() {
        return InputName;
    }

    /**
     * @param InputName the InputName to set
     */
    public void setInputName(String InputName) {
        this.InputName = InputName;
    }

    /**
     * @return the InputEmail
     */
    public String getInputEmail() {
        return InputEmail;
    }

    /**
     * @param InputEmail the InputEmail to set
     */
    public void setInputEmail(String InputEmail) {
        this.InputEmail = InputEmail;
    }

    /**
     * @return the InputNote
     */
    public String getInputNote() {
        return InputNote;
    }

    /**
     * @param InputNote the InputNote to set
     */
    public void setInputNote(String InputNote) {
        this.InputNote = InputNote;
    }

    
}
