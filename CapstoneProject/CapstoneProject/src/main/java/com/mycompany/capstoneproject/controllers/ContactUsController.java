/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.controllers;


import javax.mail.internet.MimeMessage;

import com.mycompany.capstoneproject.dto.ContactUsFormData;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
/**
 *
 * @author apprentice
 */
@Controller
@RequestMapping(value = "/contactusform")
public class ContactUsController {
    
   @Autowired
   private JavaMailSender mailSender;
   
   @Autowired
   private SimpleMailMessage templateMessage;
    
   
   public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
    
    @RequestMapping(value="", method= RequestMethod.GET)
    public String ContactUsPage(Map model){
  
        model.put("ContactUsPageList", new ContactUsFormData() );
        return "contactusform";
       
   }
    
    
    @RequestMapping(value="/", method= RequestMethod.POST)
    public String holdFormDataAndFile(HttpServletRequest request, @RequestParam MultipartFile InputFile){
        
        String name = request.getParameter("InputName");
        String email = request.getParameter("InputEmail");
        String note = request.getParameter("InputNote");
        String attachName = InputFile.getOriginalFilename();
        
        
        SimpleMailMessage templateMessage = new SimpleMailMessage();
        templateMessage.setTo(name);
        templateMessage.setTo(email);
        templateMessage.setTo(note);
        
            mailSender.send(new MimeMessagePreparator() {
 
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                
                MimeMessageHelper messageHelper = new MimeMessageHelper(
                        mimeMessage, true, "UTF-8");
                    messageHelper.setText(name);
                    messageHelper.setText(email);
                    messageHelper.setText(note);
                
                    messageHelper.addAttachment(attachName, new InputStreamSource() {
                         
                        @Override
                        public InputStream getInputStream() throws IOException {
                            return InputFile.getInputStream();
                        }
                    });
                }
                 

            });
            
            return "ContactUsSuccessPage";
                    }
             
            
 
        
    }
   
        
        
        
    
    
    
//      @RequestMapping(value="", method= RequestMethod.POST)
//    public String holdFormDataAndFile(@ModelAttribute ContactUsFormData formData){
//        
//        formData.getInputName();
//      
//        
////        formData.getInputName();
////        formData.get
//                
//        
//        
//        return null;
//    }
//  
    

