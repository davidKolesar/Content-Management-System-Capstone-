/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneproject.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author apprentice
 */
public class UserCommand {
    
    @NotEmpty(message = "please supply a valid username")
    @Length(max=20, message="username may contain only 20 characters")
    private String userNameCommand;
    
    @NotEmpty(message="Password field can not be empty")
    @Length(max=20, message="password is out of limits")
    private String passwordCommand;
    
    @NotEmpty(message="Kindly select a valid role")
    private String roleName;

    /**
     * @return the userNameCommand
     */
    public String getUserNameCommand() {
        return userNameCommand;
    }

    /**
     * @param userNameCommand the userNameCommand to set
     */
    public void setUserNameCommand(String userNameCommand) {
        this.userNameCommand = userNameCommand;
    }

    /**
     * @return the passwordCOmmand
     */
    public String getPasswordCommand() {
        return passwordCommand;
    }

    /**
     * @param passwordCOmmand the passwordCOmmand to set
     */
    public void setPasswordCommand(String passwordCOmmand) {
        this.passwordCommand = passwordCOmmand;
    }

    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
            
}
