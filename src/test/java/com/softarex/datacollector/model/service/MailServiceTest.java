package com.softarex.datacollector.model.service;

import com.softarex.datacollector.model.entity.user.User;
import com.softarex.datacollector.model.property.MailProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {
    private final static String FROM = "noreply@test.com";
    @Mock
    JavaMailSender javaMailSender;
    @Mock
    MailProperty mailProperty;
    private MailService mailService = new MailService(javaMailSender, mailProperty);

    @Test
    void sendMessage() {

        User user = new User();
        String email = "test@mail.com";
        String subject = "Lorem ipsum";
        String text = "dolor sit amet";
        user.setEmail(email);


        SimpleMailMessage expectedMailMessage = new SimpleMailMessage();
        expectedMailMessage.setFrom(FROM);
        expectedMailMessage.setText(text);
        expectedMailMessage.setSubject(subject);
        expectedMailMessage.setTo(email);

        mailService.sendMessage(user, subject, text);
        Mockito.verify(javaMailSender, Mockito.times(1)).send(expectedMailMessage);
    }
}