package me.shenderov.website.security.entities;

import me.shenderov.website.security.dao.Authority;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class NewUserWrapper {

//    @NotNull(message = "ID cannot be null")
//    private Long id;

    @NotBlank(message = "Username cannot be null")
    private String username;

    @NotBlank(message = "Name cannot be null")
    private String name;

    @NotBlank(message = "Password cannot be null")
    private String password;

    @NotNull(message = "Authorities cannot be null")
    private Set<Authority> authorities;

    public NewUserWrapper(Long id, String username, String name, String password, Set<Authority> authorities) {
//        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    public NewUserWrapper() {
    }

    //    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "NewUserWrapper{" +
               // "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
