package com.gmail.maxsvynarchuk.config;

import com.gmail.maxsvynarchuk.presentation.filter.AuthenticationFilter;
import com.gmail.maxsvynarchuk.presentation.filter.AuthorizationFilter;
import com.gmail.maxsvynarchuk.presentation.i18n.SupportedLocale;
import com.gmail.maxsvynarchuk.presentation.i18n.filter.EncodingFilter;
import com.gmail.maxsvynarchuk.presentation.i18n.filter.LocaleFilter;
import com.gmail.maxsvynarchuk.presentation.listener.ContextListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/app/").setViewName("home");
        registry.addViewController("/app/index").setViewName("home");
//        registry.addViewController("/error").setViewName("error");
    }

    @Bean
    public ServletListenerRegistrationBean<ContextListener> listenerRegistrationBean() {
        ServletListenerRegistrationBean<ContextListener> bean =
                new ServletListenerRegistrationBean<>();
        bean.setListener(new ContextListener());
        return bean;
    }

    @Bean
    public FilterRegistrationBean<EncodingFilter> encodingFilter() {
        FilterRegistrationBean<EncodingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new EncodingFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<LocaleFilter> localeFilter() {
        FilterRegistrationBean<LocaleFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LocaleFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(2);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter() {
        FilterRegistrationBean<AuthenticationFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new AuthenticationFilter());
        bean.addUrlPatterns("/app/*");
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilter() {
        FilterRegistrationBean<AuthorizationFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new AuthorizationFilter());
        bean.addUrlPatterns("/app/*");
        bean.setOrder(2);
        return bean;
    }
}
