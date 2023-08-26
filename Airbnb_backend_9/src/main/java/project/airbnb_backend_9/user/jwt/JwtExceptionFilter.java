//package project.airbnb_backend_9.user.jwt;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.filter.OncePerRequestFilter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//@Slf4j
//public class JwtExceptionFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        try{
//            String jwtToken = request.getHeader(JwtProperties.HEADER_STRING);
//            log.info("token : {}",jwtToken);
//
//            if(jwtToken != null && checkValidationJwt(jwtToken, response)){
//                log.info("token 유효! : {}",jwtToken);
//                chain.doFilter(request, response);
//            }
//
//        }catch (ExpiredJwtException e){
//            log.error("만료된 토큰");
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 만료");
//        }
//    }
//
//    private boolean checkValidationJwt(String jwtToken, HttpServletResponse response) throws IOException {
//        try{
//            Jwts.parserBuilder().setSigningKey(JwtProperties.SECRET).build().parseClaimsJwt(jwtToken); //토큰 검증 로직
//            return true;
//        }catch (ExpiredJwtException e){
//            CustomAuthenticationEntryPoint.setResponse(response,"토큰 시간이 만료되었습니다");
//        }
//        catch (MalformedJwtException e){
//            CustomAuthenticationEntryPoint.setResponse(response,"JWT 토큰이 유효하지 않습니다");
//        }catch (UnsupportedJwtException e){
//            CustomAuthenticationEntryPoint.setResponse(response,"지원하지 않는 토큰입니다");
//        }
//        return false;
//
//    }
//
//
//}
