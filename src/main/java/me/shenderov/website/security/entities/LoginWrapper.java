package me.shenderov.website.security.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginWrapper {
    @NotNull
    @NotBlank
    @Email
    @Size(min = 4, max = 254)
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 6, max = 128)
    private String password;

    public LoginWrapper(@NotNull @NotBlank @Email @Size(min = 4, max = 254) String username, @NotNull @NotBlank @Size(min = 6, max = 128) String password) {
        this.username = username;
        this.password = password;
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

    @Override
    public String toString() {
        return "LoginWraper{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
