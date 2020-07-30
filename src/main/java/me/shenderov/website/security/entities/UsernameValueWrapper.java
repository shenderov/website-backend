package me.shenderov.website.security.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UsernameValueWrapper implements Serializable {

    @NotBlank
    private String username;

    @NotNull
    private Object value;

    public UsernameValueWrapper(String username, Object value) {
        this.username = username;
        this.value = value;
    }

    public UsernameValueWrapper() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
