package me.shenderov.website.config;

import me.shenderov.website.handlers.AdminRequestHandler;
import me.shenderov.website.handlers.PublicRequestHandler;
import me.shenderov.website.handlers.SystemRequestHandler;
import me.shenderov.website.interfaces.IAdminRequestHandler;
import me.shenderov.website.interfaces.IPublicRequestHandler;
import me.shenderov.website.interfaces.ISystemRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.CacheControl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync
@EnableScheduling
@PropertySource({"classpath:db.properties", "classpath:build.properties", "classpath:email.properties"})
public class ApplicationConfig {

    @Bean
    //@DependsOn("setApplicationSettings")
    public DataInitializer dataInitializer() {
        return new DataInitializer();
    }

    @Bean
    public IPublicRequestHandler publicRequestHandler() {
        return new PublicRequestHandler();
    }

    @Bean
    public IAdminRequestHandler adminRequestHandler() {
        return new AdminRequestHandler();
    }

    @Bean
    public ISystemRequestHandler systemRequestHandler() {
        return new SystemRequestHandler();
    }

    @Bean
    public WebMvcConfigurer mvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("forward:/index.html");
                registry.addViewController("/panel").setViewName("forward:/panel/login.html");
                registry.addViewController("/panel/").setViewName("forward:/panel/login.html");
                registry.addViewController("/panel?error=true").setViewName("forward:/panel/login.html?error=true");
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/**").addResourceLocations("classpath:/static/front/");
                registry.addResourceHandler("/panel/**").addResourceLocations("classpath:/static/panel/");
                //registry.addResourceHandler("/auth/**").addResourceLocations("classpath:/static/auth/");
            }
        };
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*");
            }
        };
    }
}
