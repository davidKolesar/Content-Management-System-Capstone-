package com.mycompany.capstoneproject.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by paulharding on 10/18/16.
 */
public class PWEnc {

    public static void main(String[] args) {

        String clearTxtPw = "paul";
        // BCrypt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPw = encoder.encode(clearTxtPw);
        System.out.println(hashedPw);

    }

}
