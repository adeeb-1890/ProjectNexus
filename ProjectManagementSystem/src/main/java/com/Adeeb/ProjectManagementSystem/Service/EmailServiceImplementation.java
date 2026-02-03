package com.Adeeb.ProjectManagementSystem.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImplementation implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void SendEmailWithToken(String userEmail, String link) {

        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage , true , "utf-8");

            String subject = "Join Project Team Invitation";
            String text = "Click the link to join the team:\n" + link;

            messageHelper.setFrom("adeebforspring@gmail.com");
            messageHelper.setTo(userEmail);
            messageHelper.setSubject(subject);
            messageHelper.setText(text);

            javaMailSender.send(mimeMessage);
        }catch (Exception e) {
            throw new MailSendException("Failed to send email", e);
        }

    }
}
