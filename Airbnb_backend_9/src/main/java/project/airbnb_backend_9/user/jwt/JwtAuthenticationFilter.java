package project.airbnb_backend_9.user.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.airbnb_backend_9.user.dto.request.LoginUserDTO;
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        setFilterProcessesUrl("/api/user/login");
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter : 로그인 시도중");
        try{
            ObjectMapper om = new ObjectMapper();
            LoginUserDTO loginUserDTO = om.readValue(request.getInputStream(), LoginUserDTO.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(), loginUserDTO.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        }catch (IOException e) {
            log.error("오류발생!!!");
            throw new RuntimeException();
        }

    }

    // attemptAuthentication함수 실행 후 인증이 정상적으로 되었으면, successfulAuthentication 함수가 실행됨 따라서 JWT 생성을 attempt~에서 해줄 필요 없음
    // JWT 토큰을 만들어서 request요청한 사용자에게 JWT토큰을 response해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("successfulAuthentication 실행됨 : 인증 완료 ");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        // 암호화
        SecretKey secretKey = jwtUtils.decode();

        //JWT 생성
        String jwtToken = jwtUtils.createToken(principalDetails, secretKey);

        //사용자에게 응답할 response 헤더에 토큰 삽입
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String errorMessage;
        log.info("unsuccessfulAuthentication 실행 : 인증 실패");

        if(failed instanceof BadCredentialsException){
            errorMessage = "아이디나 비밀번호가 올바르지 않습니다" ;
        }else{
            errorMessage = "로그인에 실패하였습니다";
        }

        Map<String, String > errorResponse = new HashMap<>();
        errorResponse.put("error", errorMessage);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        new ObjectMapper().writeValue(response.getWriter(), errorResponse);
    }
}