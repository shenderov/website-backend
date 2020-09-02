package me.shenderov.website.config;

import me.shenderov.website.dao.settings.ApplicationSettings;
import me.shenderov.website.dao.settings.EmailSettings;
import me.shenderov.website.repositories.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class SettingsConfig {

    private final static Logger LOGGER = Logger.getLogger(SettingsConfig.class.getName());

    private MailerConfig mailerConfig = new MailerConfig();

    @Autowired
    private SettingsRepository settingsRepository;

    @Bean(name = "setEmailSettings")
    public void setEmailSettingsBean() {
        setEmailSettings(settingsRepository);
    }

    @Bean(name = "setApplicationSettings")
    public void setApplicationSettingsBean() {
        setApplicationSettings(settingsRepository);
    }

    public void setEmailSettings(SettingsRepository settingsRepository) {
        LOGGER.info("Setting email settings...");
        if(!settingsRepository.existsById("email")){
            LOGGER.info("Email settings: config does not exits in the database, initiate from the property file...");
            saveEmailSettingsFromProperties(settingsRepository);
        }
        try {
            LOGGER.info("Email settings: getting config from the database and setting up...");
            EmailSettings emailSettings = (EmailSettings) settingsRepository.findById("email").orElseThrow(() -> new Exception("Settings entry with id: 'email' is not found"));
            setEmailProperties(emailSettings);
            LOGGER.info("Email settings: compare DB version to properties...");
            if(!getEmailSettingsFromProperties().equals(emailSettings)){
                LOGGER.warning("Email settings: DB version is not equals to properties properties...");
            }
        } catch (Exception e) {
            LOGGER.severe("Email settings: cannot find email settings in the DB. Following steps may be ignored");
            e.printStackTrace();
        }
    }

    public void setApplicationSettings(SettingsRepository settingsRepository) {
        LOGGER.info("Setting application settings...");
        if(!settingsRepository.existsById("application")){
            LOGGER.info("Application settings: config does not exits in the database, initiate from the property file...");
            saveApplicationSettingsFromProperties(settingsRepository);
        }
        try {
            LOGGER.info("Application settings: getting config from the database and setting up...");
            ApplicationSettings applicationSettings = (ApplicationSettings) settingsRepository.findById("application").orElseThrow(() -> new Exception("Settings entry with id: 'application' is not found"));
            setApplicationProperties(applicationSettings);
            LOGGER.info("Application settings: compare DB version to properties...");
            if(!getApplicationSettingsFromProperties().equals(applicationSettings)){
                LOGGER.warning("Application settings: DB version is not equals to properties properties...");
            }
        } catch (Exception e) {
            LOGGER.severe("Application settings: cannot find application settings in the DB. Following steps may be ignored");
            e.printStackTrace();
        }
    }

    private void saveEmailSettingsFromProperties(SettingsRepository settingsRepository){
        settingsRepository.insert(getEmailSettingsFromProperties());
    }

    private void setEmailProperties(EmailSettings emailSettings){
        System.setProperty("spring.mail.host", emailSettings.getMailHost());
        System.setProperty("spring.mail.port", Integer.toString(emailSettings.getPort()));
        System.setProperty("spring.mail.username", emailSettings.getUsername());
        System.setProperty("spring.mail.password", emailSettings.getPassword());
        System.setProperty("spring.mail.properties.mail.smtp.auth", Boolean.toString(emailSettings.getSmtpAuth()));
        System.setProperty("spring.mail.properties.mail.smtp.starttls.enable", Boolean.toString(emailSettings.getSmtpStartTlsEnable()));
        System.setProperty("spring.mail.properties.mail.transport.protocol", emailSettings.getTransportProtocol());
        System.setProperty("spring.mail.properties.mail.smtp.debug", Boolean.toString(emailSettings.getSmtpDebug()));
    }

    public EmailSettings getEmailSettingsFromProperties(){
        EmailSettings emailSettings = new EmailSettings();
        emailSettings.setId("email");
        emailSettings.setMailHost(System.getProperty("spring.mail.host"));
        emailSettings.setPort(Integer.parseInt(System.getProperty("spring.mail.port")));
        emailSettings.setUsername(System.getProperty("spring.mail.username"));
        emailSettings.setPassword(System.getProperty("spring.mail.password"));
        emailSettings.setSmtpStartTlsEnable(Boolean.parseBoolean(System.getProperty("spring.mail.properties.mail.smtp.starttls.enable")));
        emailSettings.setTransportProtocol(System.getProperty("spring.mail.properties.mail.transport.protocol"));
        emailSettings.setSmtpDebug(Boolean.parseBoolean(System.getProperty("spring.mail.properties.mail.smtp.debug")));
        emailSettings.setSmtpAuth(Boolean.parseBoolean(System.getProperty("spring.mail.properties.mail.smtp.auth")));
        return emailSettings;
    }

    public ApplicationSettings getApplicationSettingsFromProperties(){
        ApplicationSettings applicationSettings = new ApplicationSettings();
        applicationSettings.setId("application");
        applicationSettings.setRunDataInitializerOnStartup(Boolean.parseBoolean(System.getProperty("application.data-initializer.run-on-startup")));
        applicationSettings.setUpdateFromJsonOnOnStartup(Boolean.parseBoolean(System.getProperty("application.data-initializer.update-from-json-on-startup")));
        applicationSettings.setAdminEmail(System.getProperty("application.admin.email"));
        applicationSettings.setEnableMailerService(Boolean.parseBoolean(System.getProperty("application.mailer.enable-mailer")));
        applicationSettings.setEnableRecaptcha(Boolean.parseBoolean(System.getProperty("application.security.enable-recaptcha")));
        applicationSettings.setRecaptchaSecretKey(System.getProperty("application.security.recaptcha.secretkey"));
        applicationSettings.setEnableYandexMetrica(Boolean.parseBoolean(System.getProperty("application.statistics.ym.enable")));
        applicationSettings.setYandexMetricaId(System.getProperty("application.statistics.ym.id"));
        applicationSettings.setYandexMetricaToken(System.getProperty("application.statistics.ym.token"));
        return applicationSettings;
    }

    private void saveApplicationSettingsFromProperties(SettingsRepository settingsRepository){
        settingsRepository.insert(getApplicationSettingsFromProperties());
    }

    private void setApplicationProperties(ApplicationSettings applicationSettings) {
        System.setProperty("application.data-initializer.run-on-startup", applicationSettings.getRunDataInitializerOnStartup().toString());
        System.setProperty("application.data-initializer.update-from-json-on-startup", applicationSettings.getUpdateFromJsonOnOnStartup().toString());
        System.setProperty("application.admin.email", applicationSettings.getAdminEmail());
        System.setProperty("application.mailer.enable-mailer", applicationSettings.getEnableMailerService().toString());
        System.setProperty("application.security.enable-recaptcha", applicationSettings.getEnableRecaptcha().toString());
        System.setProperty("application.security.recaptcha.secretkey", applicationSettings.getRecaptchaSecretKey());
        System.setProperty("application.statistics.ym.enable", applicationSettings.getEnableYandexMetrica().toString());
        System.setProperty("application.statistics.ym.id", applicationSettings.getYandexMetricaId());
        System.setProperty("application.statistics.ym.token", applicationSettings.getYandexMetricaToken());
    }

}
