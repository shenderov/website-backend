package me.shenderov.website.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
public class MailerConfig {

    private final static Logger LOGGER = Logger.getLogger(MailerConfig.class.getName());

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.properties.mail.smtp.auth:true}")
    private boolean auth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable:true}")
    private boolean starttls;
    @Value("${spring.mail.properties.mail.transport.protocol:smtp}")
    private String protocol;
    @Value("${spring.mail.properties.mail.smtp.debug:false}")
    private boolean debug;
    @Value("${application.mailer.enable-mailer:false}")
    private boolean enableMailer;

    @Bean
    public JavaMailSender javaMailSender() {
        System.setProperty("application.mailer.enable-mailer", Boolean.toString(enableMailer));
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        if(enableMailer){
            mailSender.setHost(host);
            mailSender.setPort(port);
            mailSender.setUsername(username);
            mailSender.setPassword(password);

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", protocol);
            props.put("mail.smtp.auth", auth);
            props.put("mail.smtp.starttls.enable", starttls);
            props.put("mail.debug", debug);
            try {
                mailSender.testConnection();
            } catch (MessagingException e) {
                LOGGER.warning(String.format("Mailer test connection failed: %s. Disabling mailer...", e.getMessage()));
                System.setProperty("application.mailer.enable-mailer", "false");
            }
        }
        return mailSender;
    }
}
