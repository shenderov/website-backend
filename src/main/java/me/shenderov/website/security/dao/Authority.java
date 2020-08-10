package me.shenderov.website.security.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Document(collection = "authority")
public class Authority implements GrantedAuthority {

    @Id
    private Long id;

    @NotNull
    @Indexed(unique=true)
    private AuthorityName name;

    @DBRef
    private Set<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorityName getName() {
        return name;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }

    public Set <User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getAuthority() {
        return name.name();
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", name=" + name +
                ", users=" + users +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authority)) return false;
        Authority authority = (Authority) o;
        return getId().equals(authority.getId()) &&
                getName() == authority.getName() &&
                Objects.equals(getUsers(), authority.getUsers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getUsers());
    }
}
