package com.otpexample.controller;


import com.otpexample.service.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private EmailVerificationService emailVerificationService;

    @PostMapping("/send-otp-for-login")
    public Map<String,String> sendOtpforlogin(@RequestParam String email){
        return emailVerificationService.sendOtpforlogin(email);
    }

    @PostMapping("/verify-login-email-otp")
    public Map<String,String>verifyOtpLogin(@RequestParam String email,@RequestParam String otp){
        return emailVerificationService.verifyOtpLogin(email,otp);
    }
}
