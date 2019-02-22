package me.shenderov.website.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendTextMessage(String to, String subject, String text)  throws MailException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text);
        };
        javaMailSender.send(messagePreparator);
    }

    public void sendHtmlMessage(String to, String subject, String html)  throws MailException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(html, true);
        };
        javaMailSender.send(messagePreparator);
    }

    public void sendTextWithAttachment(String to, String subject, String text, File file)  throws MailException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text);
            messageHelper.addAttachment("Invoice", file);
        };
        javaMailSender.send(messagePreparator);
    }

    public void sendHtmlWithAttachment(String to, String subject, String html, File file) throws MailException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(html, true);
            messageHelper.addAttachment("Invoice", file);
        };
        javaMailSender.send(messagePreparator);
    }

}
