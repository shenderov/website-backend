package me.shenderov.website.security.service;

import me.shenderov.website.security.dao.User;
import me.shenderov.website.security.entities.BasicUser;

public final class BasicUserFactory {

    private BasicUserFactory() {
    }

    public static BasicUser create(User user) {
        return new BasicUser(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getPassword(),
                user.getAuthorities(),
                user.isEnabled(),
                user.getLastPasswordResetDate()
        );
    }

}
