package me.shenderov.website.dao.settings;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "settings")
public class ApplicationSettings extends AbstractApplicationSettings{

    private Boolean runDataInitializerOnStartup;
    private Boolean updateFromJsonOnOnStartup;
    private String adminEmail;
    private Boolean enableRecaptcha;
    private String recaptchaSecretKey;
    private Boolean enableMailerService;

    public ApplicationSettings() {
    }

    public Boolean getRunDataInitializerOnStartup() {
        return runDataInitializerOnStartup;
    }

    public void setRunDataInitializerOnStartup(Boolean runDataInitializerOnStartup) {
        this.runDataInitializerOnStartup = runDataInitializerOnStartup;
    }

    public Boolean getUpdateFromJsonOnOnStartup() {
        return updateFromJsonOnOnStartup;
    }

    public void setUpdateFromJsonOnOnStartup(Boolean updateFromJsonOnOnStartup) {
        this.updateFromJsonOnOnStartup = updateFromJsonOnOnStartup;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public Boolean getEnableRecaptcha() {
        return enableRecaptcha;
    }

    public void setEnableRecaptcha(Boolean enableRecaptcha) {
        this.enableRecaptcha = enableRecaptcha;
    }

    public String getRecaptchaSecretKey() {
        return recaptchaSecretKey;
    }

    public void setRecaptchaSecretKey(String recaptchaSecretKey) {
        this.recaptchaSecretKey = recaptchaSecretKey;
    }

    public Boolean getEnableMailerService() {
        return enableMailerService;
    }

    public void setEnableMailerService(Boolean enableMailerService) {
        this.enableMailerService = enableMailerService;
    }

}
