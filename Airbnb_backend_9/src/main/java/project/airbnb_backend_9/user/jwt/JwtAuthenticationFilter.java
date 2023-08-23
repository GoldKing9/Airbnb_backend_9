package project.airbnb_backend_9.user.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.airbnb_backend_9.user.dto.request.LoginUserDTO;
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter : 로그인 시도중");
        try{
            //1. username, password 받음
            ObjectMapper om = new ObjectMapper();
            LoginUserDTO loginUserDTO = om.readValue(request.getInputStream(), LoginUserDTO.class);
            log.info("user : {}", loginUserDTO.toString());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(), loginUserDTO.getPassword());
            log.info("authenticationToken 생성");
            //2. 정상인지 로그인 시도 authenticationManager로 로그인 시도 : 이때 UserDetailsService 사용됨 -> loadUserByUsername 함수 실행, 정상이면 authentication리턴
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            log.info("authentication 생성");

            //3. PrincipalDetailsService를 세션영억에 저장
            // 그리고 세션 영역에 있는 authentication에 있는 principal이라는 객체를 가져옴 -> 출력 된다?-> 로그인 성공
            PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
            log.info(" 로그인 완료 : id : {} , pw : {}", principalDetails.getUsername(), principalDetails.getPassword());
            log.info("=============로그인 성공!===============");
            return authentication;
        }catch (Exception e){
            log.info("오류발생!!!");
            e.printStackTrace();
        }
        log.info("=============로그인 안됨===============");
        return null;

    }

    // attemptAuthentication함수 실행 후 인증이 정상적으로 되었으면, successfulAuthentication 함수가 실행됨 따라서 JWT 생성을 attempt~에서 해줄 필요 없음
    // JWT 토큰을 만들어서 request요청한 사용자에게 JWT토큰을 response해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("successfulAuthentication 실행됨 : 인증 완료 ");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        //JWT 생성
        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME)) //토큰 유효시간 설정
                .withClaim("id", principalDetails.getUsers().getUserId()) //비공개 클레임 내가 넣고 싶은 키, 값을 넣어줌
                .withClaim("email", principalDetails.getUsers().getEmail())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));// 내 서버의 고유한 값

        //사용자에게 응답할 response 헤더에
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);


    }
}
