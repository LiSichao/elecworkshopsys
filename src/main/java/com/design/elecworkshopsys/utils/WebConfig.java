package com.design.elecworkshopsys.utils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
public class WebConfig implements WebMvcConfigurer {
    @Bean(name = "sitemesh3")
    WebSiteMeshFilter webSiteMeshFilter() {
        return new WebSiteMeshFilter();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(@Qualifier("sitemesh3") WebSiteMeshFilter siteMeshFilter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(siteMeshFilter);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/login")
                .excludePathPatterns("/api/login")
                .excludePathPatterns("/api/register")
                .excludePathPatterns("/dist/**")
                .excludePathPatterns("/plugins/**")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/img/**")
                .excludePathPatterns("/file/**")
                .excludePathPatterns("/api/**")
        ;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:index");
        registry.addViewController("/decorators/decorator").setViewName("decorators/decorator");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/userinfo").setViewName("userinfo");
        registry.addViewController("/user").setViewName("user");
        registry.addViewController("/fault").setViewName("fault");
        registry.addViewController("/station").setViewName("station");
        registry.addViewController("/spare").setViewName("spare");
        registry.addViewController("/memo").setViewName("memo");
    }

}
