package me.shenderov.website.security.entities;

import me.shenderov.website.security.dao.Authority;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

public class UsernameAuthorityWrapper implements Serializable {

    @NotBlank
    private String username;

    @NotNull
    private Set<Authority> authorities;

    public UsernameAuthorityWrapper() {
    }

    public UsernameAuthorityWrapper(String username, Set<Authority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
