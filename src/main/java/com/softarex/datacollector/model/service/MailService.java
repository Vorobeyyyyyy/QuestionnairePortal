package com.softarex.datacollector.model.service;

import com.softarex.datacollector.model.entity.user.User;
import com.softarex.datacollector.model.property.MailProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class MailService {
    private JavaMailSender javaMailSender;
    private MailProperty mailProperty;

    public void sendMessage(User user, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperty.getFrom());
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
    }

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    public void setMailProperty(MailProperty mailProperty) {
        this.mailProperty = mailProperty;
    }
}
