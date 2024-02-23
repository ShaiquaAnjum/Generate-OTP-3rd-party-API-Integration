package com.otpexample.service;

import com.otpexample.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailVerificationService {

    @Autowired
    private UserService userService;


    @Autowired
    private EmailService emailService;

    static final Map<String,String> emailOtpMapping = new HashMap<>();

    public  Map<String, String> verifyOtp(String email,String otp) {
        String storedOtp = emailOtpMapping.get(email);

        Map<String,String> response = new HashMap<>();
        if(storedOtp!=null&& storedOtp.equals(otp)){
            User user = userService.getUserByEmail(email);
            if(user!=null){

                emailOtpMapping.remove(email);
                userService.verifyEmail(user);
                response.put("status","success");
                response.put("message","email verified successfully");
            }else{
                response.put("status","error");
                response.put("message","user not found");
            }
        }else{
            response.put("status","error");
            response.put("message","invalid otp");
        }
        return response;
    }

    public Map<String,String> sendOtpforlogin(String email) {
        if (userService.isEmailVerified(email)) {

            String otp = emailService.generateOtp();
            emailOtpMapping.put(email, otp);

            emailService.sendOtpEmail(email);

            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("msg", "otp sent successfully");
            return response;

        } else {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("msg", "email is not verified");
            return response;


        }
    }

    public Map<String,String> verifyOtpLogin(String email,String otp) {
        String storedotp = emailOtpMapping.get(email);

        Map<String, String> response = new HashMap<>();
        if (storedotp != null && storedotp.equals(otp)) {
            response.put("status", "success");
            response.put("message", "otp verified");
        } else {
            response.put("status", "error");
            response.put("message", "invalid otp");
        }


        return response;
    }
}
