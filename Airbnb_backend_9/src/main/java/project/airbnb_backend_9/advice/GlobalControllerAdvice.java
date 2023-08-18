package project.airbnb_backend_9.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = Exception.class) //전체적인 예외를 다 잡을것임
    public ResponseEntity<String> exception(Exception e){
        log.info(e.getClass().getName());
        log.info("==================");
        log.info("ERROR : {}", e.getLocalizedMessage());
        log.info("==================");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(IllegalArgumentException e){
        log.info("GlobalControllerAdvice의 IllegalArgumentException 발생");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
