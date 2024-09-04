package com.sparta.newsfeed.user.config;

import com.sparta.newsfeed.user.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    // jwtfilter를 등록하기 위해서 bean 생성
    public FilterRegistrationBean<JwtFilter> jwtAuthenticationFilter(JwtFilter jwtFilter) {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        //등록할 필터로 jwtfilter 설정
        registrationBean.setFilter(jwtFilter);
        //필터가 적용될 URL 설정
        registrationBean.addUrlPatterns("/api/user/*");
        return registrationBean;
    }
}
