package project.airbnb_backend_9.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.airbnb_backend_9.user.dto.request.SignUpDTO;
import project.airbnb_backend_9.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping({"","/"})
    public String index(){
        return "index";
    }

    @GetMapping("/user")
    public String user(){
        return "user";
    }

    @GetMapping("/api/auth")
    public String test(){
        return "/api/auth";
    }
//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }
    @GetMapping("/join")
    public String join(){
        return "join";
    }

    @GetMapping("/api/user/login")
    public String login(){
        return "login";
    }

    @PostMapping("/api/user/signup")
    public ResponseEntity signup(@Validated @RequestBody SignUpDTO signUpDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError field = (FieldError) objectError;
                String message = objectError.getDefaultMessage();
                sb.append("field : ");
                sb.append(field.getField());
                sb.append("\n message : ");
                sb.append(message);

                log.info("field : {}", field.getField());
                log.info("message : {}", message);

            });
                log.info("원인 : {}", sb.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
        }
        userService.signup(signUpDTO);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @PostMapping("/api/auth/user/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }


        return "로그아웃 성공";
    }


}
