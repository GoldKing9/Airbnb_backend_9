package project.airbnb_backend_9.user.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.repository.user.UserRepository;
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 시큐리티가 가진 BasicAuthenticationFilter는 권한이나 인증이 필요한 특정 주소 요청시 무조건 이 필터를 탐
// 만약 권한이나 인증이 필요한 주소가 아닌경우 이 필터를 타지 않음
// 필터는 스프링 애플리케이션 컨텍스트 밖에서 생성됨 -> @Autowired로 의존성 주입받을 수 없음 -> SecurityConfig에서 주입해줘야함
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final UserRepository userRepository;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("인증이나 권한이 필요한 주소 요청이 됨");
        log.info("JwtAuthorizationFilter 실행");
        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);


        //header가 있는지 확인
        if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }
        //JWT를 검증 -> 정상적인 사용자인지 확인
        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        // successfulAuthentication에서 지정한 전자서명 .sign()안에 담긴 내용, verify서명함
        String email = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("email").asString();
        log.info("email 존재 : {}", email);

        if(email != null){
            Users userEntity = userRepository.findByEmail(email);
            log.info("userEntity 조회 성공 : {}", userEntity);

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
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            log.info("jwt 서명 완료");

            SecurityContextHolder.getContext().setAuthentication(authentication);//session 공간에 jwt저장
            log.info("session에 jwt저장");
            chain.doFilter(request,response);



        }

    }
}