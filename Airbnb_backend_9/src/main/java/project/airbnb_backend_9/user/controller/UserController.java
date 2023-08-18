package project.airbnb_backend_9.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.airbnb_backend_9.user.dto.request.SignUpDTO;
import project.airbnb_backend_9.user.service.UserService;

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
    public String signup(@RequestBody SignUpDTO signUpDTO){
        userService.signup(signUpDTO);

        return "join";
    }


}
