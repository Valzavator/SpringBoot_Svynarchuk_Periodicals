package com.gmail.maxsvynarchuk.config;

import com.gmail.maxsvynarchuk.presentation.filter.AuthenticationFilter;
import com.gmail.maxsvynarchuk.presentation.filter.AuthorizationFilter;
import com.gmail.maxsvynarchuk.presentation.i18n.filter.EncodingFilter;
import com.gmail.maxsvynarchuk.presentation.i18n.filter.LocaleFilter;
import com.gmail.maxsvynarchuk.presentation.listener.ContextListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class WebConfig {
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
