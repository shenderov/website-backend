package me.shenderov.website.dao.settings;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "settings")
public class EmailSettings extends AbstractApplicationSettings{

    private String mailHost;
    private Integer port;
    private String username;
    private String password;
    private Boolean smtpAuth;
    private Boolean smtpStartTlsEnable;
    private String transportProtocol;
    private Boolean smtpDebug;

    public EmailSettings() {
    }

    public String getMailHost() {
        return mailHost;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSmtpAuth() {
        return smtpAuth;
    }

    public void setSmtpAuth(Boolean smtpAuth) {
        this.smtpAuth = smtpAuth;
    }

    public Boolean getSmtpStartTlsEnable() {
        return smtpStartTlsEnable;
    }

    public void setSmtpStartTlsEnable(Boolean smtpStartTlsEnable) {
        this.smtpStartTlsEnable = smtpStartTlsEnable;
    }

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    public Boolean getSmtpDebug() {
        return smtpDebug;
    }

    public void setSmtpDebug(Boolean smtpDebug) {
        this.smtpDebug = smtpDebug;
    }
}
