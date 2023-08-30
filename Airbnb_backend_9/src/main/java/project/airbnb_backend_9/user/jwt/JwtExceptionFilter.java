package project.airbnb_backend_9.user.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try{
            chain.doFilter(request, response);
        }catch (ExpiredJwtException e){
            CustomAuthenticationEntryPoint.setResponse(response,"토큰 시간이 만료되었습니다");
        }
        catch (MalformedJwtException e){
            CustomAuthenticationEntryPoint.setResponse(response,"JWT 토큰이 유효하지 않습니다");
        }
        catch (UnsupportedJwtException e){
            CustomAuthenticationEntryPoint.setResponse(response,"지원하지 않는 토큰입니다");
        }

    }

}
