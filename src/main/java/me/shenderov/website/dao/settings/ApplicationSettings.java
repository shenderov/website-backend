package me.shenderov.website.dao.settings;

import java.util.Objects;

public class ApplicationSettings extends AbstractApplicationSettings{

    private Boolean runDataInitializerOnStartup;
    private Boolean updateFromJsonOnOnStartup;
    private String adminEmail;
    private Boolean enableRecaptcha;
    private String recaptchaSecretKey;
    private Boolean enableMailerService;
    private Boolean enableYandexMetrica;
    private String yandexMetricaId;
    private String yandexMetricaToken;

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

    public Boolean getEnableYandexMetrica() {
        return enableYandexMetrica;
    }

    public void setEnableYandexMetrica(Boolean enableYandexMetrica) {
        this.enableYandexMetrica = enableYandexMetrica;
    }

    public String getYandexMetricaToken() {
        return yandexMetricaToken;
    }

    public void setYandexMetricaToken(String yandexMetricaToken) {
        this.yandexMetricaToken = yandexMetricaToken;
    }

    public String getYandexMetricaId() {
        return yandexMetricaId;
    }

    public void setYandexMetricaId(String yandexMetricaId) {
        this.yandexMetricaId = yandexMetricaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationSettings)) return false;
        if (!super.equals(o)) return false;
        ApplicationSettings that = (ApplicationSettings) o;
        return Objects.equals(getRunDataInitializerOnStartup(), that.getRunDataInitializerOnStartup()) &&
                Objects.equals(getUpdateFromJsonOnOnStartup(), that.getUpdateFromJsonOnOnStartup()) &&
                Objects.equals(getAdminEmail(), that.getAdminEmail()) &&
                Objects.equals(getEnableRecaptcha(), that.getEnableRecaptcha()) &&
                Objects.equals(getRecaptchaSecretKey(), that.getRecaptchaSecretKey()) &&
                Objects.equals(getEnableMailerService(), that.getEnableMailerService()) &&
                Objects.equals(getEnableYandexMetrica(), that.getEnableYandexMetrica()) &&
                Objects.equals(getYandexMetricaId(), that.getYandexMetricaId()) &&
                Objects.equals(getYandexMetricaToken(), that.getYandexMetricaToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRunDataInitializerOnStartup(), getUpdateFromJsonOnOnStartup(), getAdminEmail(), getEnableRecaptcha(), getRecaptchaSecretKey(), getEnableMailerService(), getEnableYandexMetrica(), getYandexMetricaId(), getYandexMetricaToken());
    }

    @Override
    public String toString() {
        return "ApplicationSettings{" +
                "runDataInitializerOnStartup=" + runDataInitializerOnStartup +
                ", updateFromJsonOnOnStartup=" + updateFromJsonOnOnStartup +
                ", adminEmail='" + adminEmail + '\'' +
                ", enableRecaptcha=" + enableRecaptcha +
                ", recaptchaSecretKey='" + recaptchaSecretKey + '\'' +
                ", enableMailerService=" + enableMailerService +
                ", enableYandexMetrica=" + enableYandexMetrica +
                ", yandexMetricaId='" + yandexMetricaId + '\'' +
                ", yandexMetricaToken='" + yandexMetricaToken + '\'' +
                '}';
    }
}
