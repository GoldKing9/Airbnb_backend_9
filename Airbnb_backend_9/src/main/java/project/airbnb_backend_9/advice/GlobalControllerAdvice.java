package project.airbnb_backend_9.advice;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;


@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = Exception.class) //전체적인 예외를 다 잡을것임
    public ResponseEntity<String> exception(Exception e){
        log.info(e.getClass().getName());
        log.info("==================");
        log.info("ERROR : {}", e.getLocalizedMessage());
        log.info("==================");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(IllegalArgumentException e){
        log.info("GlobalControllerAdvice의 IllegalArgumentException 발생");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // 스프링 시큐리티에서 발생할 수 있는 예외
    @ExceptionHandler(value = SignatureVerificationException.class)
    public ResponseEntity<String> SignatureVerificationException(SignatureVerificationException e){
        log.error("error : {}",e.getLocalizedMessage());
        log.info("변조된 토큰 사용");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("SignatureVerificationException : 변조된 토큰 사용");
    }
//    @ExceptionHandler(MalformedJwtException.class)
//    public ResponseEntity<String> handleMalformedJwtException(){
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("MalformedJwtException : 토큰으로 아무값 사용");
//    }
    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<String> handleExpiredJwtException(TokenExpiredException e){
        log.error("error : {}",e.getLocalizedMessage());
        log.info("토큰 만료");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("TokenExpiredException : 토큰 만료");
    }

}
