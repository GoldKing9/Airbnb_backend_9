package project.airbnb_backend_9.user.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.repository.UserRepository;
import project.airbnb_backend_9.user.dto.request.SignUpDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원가입 테스트")
    public void signup() throws Exception{

//        LocalDate birth = LocalDate.parse("1998-12-03", DateTimeFormatter.ISO_LOCAL_DATE);

        SignUpDTO signup = SignUpDTO.builder()
                .username("박경선")
                .password("123")
                .email("aaa@gmail.com")
                .birth("2022-12-03")
                .build();

        userService.signup(signup);

        Users users = userRepository.findByEmail("aaa@gmail.com");
        assertThat(users.getEmail()).isEqualTo(signup.getEmail());


    }
}