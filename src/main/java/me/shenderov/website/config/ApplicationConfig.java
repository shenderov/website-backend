package me.shenderov.website.config;

import me.shenderov.website.handlers.AdminRequestHandler;
import me.shenderov.website.handlers.PublicRequestHandler;
import me.shenderov.website.interfaces.IAdminRequestHandler;
import me.shenderov.website.interfaces.IPublicRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
@PropertySource({"classpath:db.properties", "classpath:build.properties"})
public class ApplicationConfig {

    @Bean
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
    public WebMvcConfigurer mvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        //.allowedOrigins("localhost")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/")
                        .addResourceLocations("/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/*.jpg",
                        "/**/*.jpg",
                        "/*.eot",
                        "/**/*.eot",
                        "/*.woff",
                        "/**/*.woff",
                        "/*.woff2",
                        "/**/*.woff2",
                        "/*.ttf",
                        "/**/*.ttf",
                        "/*.svg",
                        "/**/*.svg")
                        .setCacheControl(CacheControl.maxAge(1, TimeUnit.DAYS));
            }
        };
    }
}
