package me.shenderov.website.config;

import me.shenderov.website.dao.settings.EmailSettings;
import me.shenderov.website.repositories.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class SettingsConfig {

    private final static Logger LOGGER = Logger.getLogger(SettingsConfig.class.getName());

    @Autowired
    private SettingsRepository settingsRepository;

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

    @Bean(name = "setEmailSettings")
    public void setEmailSettings() throws Exception {
        LOGGER.info("Setting email settings...");
        if(settingsRepository.existsById("email")){
            LOGGER.info("Email configuration found in the database. Setting up...");
            EmailSettings emailSettings = (EmailSettings) settingsRepository.findById("email").orElseThrow(() -> new Exception("Settings entry with id: 'email' is not found"));
            setEmailConfig(emailSettings);
        }else{
            LOGGER.info("Email settings does not exits in the database, initiate from the property file...");
            initialEmailSettings();
        }
    }

    private void initialEmailSettings(){
        EmailSettings emailSettings = new EmailSettings();
        emailSettings.setMailHost(host);
        emailSettings.setPort(port);
        emailSettings.setUsername(username);
        emailSettings.setPassword(password);
        emailSettings.setSmtpStartTlsEnable(auth);
        emailSettings.setTransportProtocol(protocol);
        emailSettings.setSmtpDebug(debug);
        settingsRepository.insert(emailSettings);
    }

    private void setEmailConfig(EmailSettings emailSettings){
        System.setProperty("spring.mail.host", emailSettings.getMailHost());
        System.setProperty("spring.mail.port", Integer.toString(emailSettings.getPort()));
        System.setProperty("spring.mail.username", emailSettings.getUsername());
        System.setProperty("spring.mail.password", emailSettings.getPassword());
        System.setProperty("spring.mail.properties.mail.smtp.auth", Boolean.toString(emailSettings.getSmtpAuth()));
        System.setProperty("spring.mail.properties.mail.smtp.starttls.enable", Boolean.toString(emailSettings.getSmtpStartTlsEnable()));
        System.setProperty("spring.mail.properties.mail.transport.protocol", emailSettings.getTransportProtocol());
        System.setProperty("spring.mail.properties.mail.smtp.debug", Boolean.toString(emailSettings.getSmtpDebug()));
    }

//
//    @Bean(name = "setApplicationSettings")
//    public void SetApplicationSettings() throws Exception {
//        ApplicationSettings applicationSettings = (ApplicationSettings) settingsRepository.findById("application").orElseThrow(() -> new Exception("Settings entry with id: 'email' is not found"));
//    }
}
