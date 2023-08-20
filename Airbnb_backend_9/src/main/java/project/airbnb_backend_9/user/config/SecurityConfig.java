package project.airbnb_backend_9.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.airbnb_backend_9.repository.UserRepository;
import project.airbnb_backend_9.user.jwt.JwtAuthenticationFilter;
import project.airbnb_backend_9.user.jwt.JwtAuthorizationFilter;
import project.airbnb_backend_9.user.jwt.JwtExceptionFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new MyCustomDsl()) // 커스텀 필터 등록
                .and()
                .authorizeRequests( //다음 요청에 대한 사용 권한 체크
                        authorize -> authorize
                                .antMatchers("/api/user/**", "/api/reviews/**","/api/accommodation/**").permitAll()//가입, 리뷰조회, 숙소조회는 누구나 접근 가능
                                .antMatchers("/api/auth/**")
                                .access("hasRole('USER')")
                                .anyRequest().permitAll())
                .build();
    }



    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
                    .addFilterBefore(new JwtExceptionFilter(), JwtAuthorizationFilter.class)
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
        }
    }

}
