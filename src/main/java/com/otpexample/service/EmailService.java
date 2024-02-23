package com.otpexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.otpexample.service.EmailVerificationService.emailOtpMapping;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public String generateOtp() {
        return String.format("%06d", new java.util.Random().nextInt(1000000));
    }

    public void sendOtpEmail(String email) {

        String otp = generateOtp();
        emailOtpMapping.put(email, otp);
        sendEmail(email, "your otp is" + otp);

    }


    private void sendEmail(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shaiquak76@gmail.com");
        message.setTo(to);
        message.setSubject("otp for email verification");
        message.setText(text);
        javaMailSender.send(message);
    }
}

