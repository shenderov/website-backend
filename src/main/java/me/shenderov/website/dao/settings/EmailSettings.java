package me.shenderov.website.dao.settings;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailSettings)) return false;
        if (!super.equals(o)) return false;
        EmailSettings that = (EmailSettings) o;
        return Objects.equals(getMailHost(), that.getMailHost()) &&
                Objects.equals(getPort(), that.getPort()) &&
                Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getSmtpAuth(), that.getSmtpAuth()) &&
                Objects.equals(getSmtpStartTlsEnable(), that.getSmtpStartTlsEnable()) &&
                Objects.equals(getTransportProtocol(), that.getTransportProtocol()) &&
                Objects.equals(getSmtpDebug(), that.getSmtpDebug());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMailHost(), getPort(), getUsername(), getPassword(), getSmtpAuth(), getSmtpStartTlsEnable(), getTransportProtocol(), getSmtpDebug());
    }

    @Override
    public String toString() {
        return "EmailSettings{" +
                "mailHost='" + mailHost + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", smtpAuth=" + smtpAuth +
                ", smtpStartTlsEnable=" + smtpStartTlsEnable +
                ", transportProtocol='" + transportProtocol + '\'' +
                ", smtpDebug=" + smtpDebug +
                '}';
    }
}
