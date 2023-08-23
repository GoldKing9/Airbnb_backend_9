package project.airbnb_backend_9.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.airbnb_backend_9.user.dto.request.SignUpDTO;
import project.airbnb_backend_9.user.dto.request.UpdateUserDTO;
import project.airbnb_backend_9.user.dto.response.UserProfileDTO;
import project.airbnb_backend_9.user.dto.response.ValidationErrorDTO;
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;
import project.airbnb_backend_9.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/api/user/signup")
    public ResponseEntity<List<ValidationErrorDTO>> signup(@Validated @RequestBody SignUpDTO signUpDTO, BindingResult bindingResult){
        //회원가입시 검증
        if(bindingResult.hasErrors()){
        List<ValidationErrorDTO> list = new ArrayList<>();
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError field = (FieldError) objectError;
                String message = objectError.getDefaultMessage();

                list.add(new ValidationErrorDTO(field.getField(), message));

                log.info("field : {}", field.getField());
                log.info("message : {}", message);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(list);
        }
        userService.register(signUpDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
    }

    @PostMapping("/api/auth/user/logout")
    public PrincipalDetails logout(HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal PrincipalDetails principalDetails){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails p = (PrincipalDetails)authentication.getPrincipal();

        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return principalDetails;
    }


    @GetMapping("/api/user/{userId}")
    public UserProfileDTO profile(@PathVariable Long userId){

        return userService.getUserProfile(userId);
    }
    @PutMapping("/api/auth/user")
    public void updateUser(
            @RequestBody UpdateUserDTO updateUserDTO,
            @AuthenticationPrincipal PrincipalDetails principalDetails){
        userService.updateProfile(updateUserDTO, principalDetails.getUsers());
    }

}
