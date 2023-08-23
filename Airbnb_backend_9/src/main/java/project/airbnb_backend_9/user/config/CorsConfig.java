package project.airbnb_backend_9.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

    @Configuration
    public class CorsConfig {
        @Bean
        public CorsFilter corsFilter(){ // 이것을 SecurityConfig에서 필터를 걸어줘야함
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true); // 내 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정 하는 것(예를들어 ajax, fetch, axios같은 라이브러리로 데이터를 요청하면 응답을 자바스크립트가 받을수 있도록 할것인지 여부)
            config.addAllowedOrigin("*"); //출처가 어디든 허용, 모든 ip에 응답을 허용
            config.addAllowedHeader("*"); // 모든 header 허용, 모든 header에 응답을 허용
            config.addAllowedMethod("*"); //get, put 등등 모두 허용, 모든 post, get, put, delete, patch 요청을 허용
            source.registerCorsConfiguration("/api/**", config); // /api/* 의 모든 요청의 이 config를 따라라
            return new CorsFilter(source);
        }
    }
