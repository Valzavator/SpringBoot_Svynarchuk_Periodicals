package com.gmail.maxsvynarchuk;

import com.gmail.maxsvynarchuk.presentation.filter.AuthenticationFilter;
import com.gmail.maxsvynarchuk.presentation.filter.AuthorizationFilter;
import com.gmail.maxsvynarchuk.presentation.i18n.SupportedLocale;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

//TODO
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Bean
//    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter() {
//        FilterRegistrationBean<AuthenticationFilter> registration =
//                new FilterRegistrationBean<>();
//        registration.setFilter(new AuthenticationFilter());
//        registration.addUrlPatterns("/app/*");
//        registration.setOrder(1);
//
//        return registration;
//    }
//
//    @Bean
//    public FilterRegistrationBean<AuthorizationFilter> authorizationFilter() {
//        FilterRegistrationBean<AuthorizationFilter> registration =
//                new FilterRegistrationBean<>();
//        registration.setFilter(new AuthorizationFilter());
//        registration.addUrlPatterns("/app/*");
//        registration.setOrder(2);
//
//        return registration;
//    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("index");
//    }
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(SupportedLocale.getDefault());
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
