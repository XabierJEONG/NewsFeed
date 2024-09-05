package com.sparta.newsfeed.user.config;

import com.sparta.newsfeed.user.filter.JwtFilter;
import com.sparta.newsfeed.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {
    private final JwtUtil jwtTokenUtil;

    @Bean
    // jwtfilter를 등록하기 위해서 bean 생성
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        //등록할 필터로 jwtfilter 설정
        registrationBean.setFilter(new JwtFilter(jwtTokenUtil));
        //필터가 적용될 URL 설정
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
