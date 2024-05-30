package com.thanksang.HentoriManager.services;

import com.thanksang.HentoriManager.config.JavaMailConfigs;
import com.thanksang.HentoriManager.services.Imp.EmailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailServiceImp {

    private JavaMailSender javaMailSender;
    private JavaMailConfigs javaMailConfigs;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, JavaMailConfigs javaMailConfigs) {
        this.javaMailSender = javaMailSender;
        this.javaMailConfigs = javaMailConfigs;
    }

    public void sendSimpleEmail(String text, String username){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Confirm register account " + username);
        message.setTo(javaMailConfigs.getReceiver());
        message.setText(text);
        javaMailSender.send(message);
    };


}
