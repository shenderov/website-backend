package me.shenderov.website.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
public class MailerConfig {

    private final static Logger LOGGER = Logger.getLogger(MailerConfig.class.getName());

    @Bean
    @DependsOn({"setEmailSettings", "setApplicationSettings"})
    public JavaMailSender javaMailSender() {
        LOGGER.info("Setting Java Mail Sender...");
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        if(Boolean.parseBoolean(System.getProperty("application.mailer.enable-mailer"))){
            LOGGER.info("Mail Sender enabled, settings up parameters...");
            mailSender.setHost(System.getProperty("spring.mail.host"));
            mailSender.setPort(Integer.parseInt(System.getProperty("spring.mail.port")));
            mailSender.setUsername(System.getProperty("spring.mail.username"));
            mailSender.setPassword(System.getProperty("spring.mail.password"));

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", System.getProperty("spring.mail.properties.mail.transport.protocol"));
            props.put("mail.smtp.auth", System.getProperty("spring.mail.properties.mail.smtp.auth"));
            props.put("mail.smtp.starttls.enable", System.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
            props.put("mail.debug", System.getProperty("spring.mail.properties.mail.smtp.debug"));
            try {
                LOGGER.info("Testing connection to mail server...");
                mailSender.testConnection();
            } catch (MessagingException e) {
                LOGGER.warning(String.format("Mailer test connection failed: %s. Disabling mailer...", e.getMessage()));
                System.setProperty("application.mailer.enable-mailer", "false");
            }
        }else {
            LOGGER.info("Mail Sender disabled, doing nothing...");
        }
        return mailSender;
    }
}
