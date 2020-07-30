package me.shenderov.website.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    public static final String ADMIN_API = "/admin";
    public static final String PUBLIC_API = "/public";

    public static final String GET_USER_DETAILS = ADMIN_API + "/getUserDetails";
    public static final String ADD_USER = ADMIN_API + "/addUser";
    public static final String CHANGE_NAME = ADMIN_API + "/changeUserName";
    public static final String CHANGE_PASSWORD = ADMIN_API + "/changeUserPassword";
    public static final String SET_AUTHORITIES = ADMIN_API + "/setUserAuthorities";
    public static final String SET_ENABLED = ADMIN_API + "/setUserEnabled";
    public static final String DELETE_USER = ADMIN_API + "/deleteUser";

    public static final String ADD_BLOCK = ADMIN_API + "/addBlock";
    public static final String GET_BLOCK = PUBLIC_API + "/block";
    public static final String UPDATE_BLOCK = ADMIN_API + "/updateBlock";
    public static final String DELETE_BLOCK = ADMIN_API + "/deleteBlock";
    public static final String GET_BLOCKS = PUBLIC_API + "/blocks";

    public static final String ADD_SEO = ADMIN_API + "/addSeoInfo";
    public static final String GET_SEO_INFO_ADMIN = ADMIN_API + "/getSeoInfo";
    public static final String GET_SEO_INFO_PUBLIC = PUBLIC_API + "/seo";
    public static final String UPDATE_SEO = ADMIN_API + "/updateSeoInfo";
    public static final String DELETE_SEO = ADMIN_API + "/deleteSeoInfo";

    public static final String SEND_MESSAGE = PUBLIC_API + "/sendMessage";
    public static final String GET_MESSAGES = ADMIN_API + "/getAllMessages";
    public static final String GET_MESSAGE = ADMIN_API + "/getMessage";
    public static final String DELETE_MESSAGE = ADMIN_API + "/deleteMessage";
    public static final String DELETE_MESSAGES = ADMIN_API + "/deleteMessages";
    public static final String DELETE_ALL_MESSAGES = ADMIN_API + "/deleteAllMessages";

    @Bean
    public TestTools testTools() {
        return new TestTools();
    }
}
