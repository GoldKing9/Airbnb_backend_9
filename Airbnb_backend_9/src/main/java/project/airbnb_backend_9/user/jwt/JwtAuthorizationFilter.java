package project.airbnb_backend_9.user.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.repository.user.UserRepository;
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 시큐리티가 가진 BasicAuthenticationFilter는 권한이나 인증이 필요한 특정 주소 요청시 무조건 이 필터를 탐
// 만약 권한이나 인증이 필요한 주소가 아닌경우 이 필터를 타지 않음
// 필터는 스프링 애플리케이션 컨텍스트 밖에서 생성됨 -> @Autowired로 의존성 주입받을 수 없음 -> SecurityConfig에서 주입해줘야함
@Slf4j
@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtils jwtUtils) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("JwtAuthorizationFilter 실행 : 인증이나 권한이 필요한 주소 요청이 됨");
        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);

        //header가 있는지 확인
        if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }
        //JWT를 검증 -> 정상적인 사용자인지 확인
        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
        // 암호화
        SecretKey secretKey = jwtUtils.decode();

        // successfulAuthentication에서 지정한 전자서명 .sign()안에 담긴 내용, verify서명함
        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken);

        String email = jwsClaims.getBody().get("email", String.class);

        if(email != null){
            Users userEntity = userRepository.findByEmail(email).orElseThrow(
                    () ->  new UsernameNotFoundException("아이디 없음, 사용자 아님")
            );

            // 정상적으로 조회됨? -> 사용자 맞아
            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
            /**
             * Authentication의 구현체는 로그인했을 때 authenticationManager로 인증하는 과정이 필요하지만
             * 강제로 만들어 줄 수 있다, Authentication을 만들어 줄 수 있는 이유? -> username이 null이 아니기 때문
             * principalDetails.getAuthorities() : 권한을 알려줌
             * 이는 정상적인 로그인 요청 처리가 아님 -> JWT 토큰 서명을 통해 만든 서명임
             *
             * 정상이면 Athentication객체를 만들어준다
             */

            // credentials에 password를 넣어야하지만 강제로 만들기 때문에 넣지 않음 -> Authentication객체를 만들기 위해
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);//session 공간에 jwt저장
            log.info("session에 jwt저장");
            chain.doFilter(request,response);

        }
    }
}