package project.airbnb_backend_9.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.airbnb_backend_9.domain.Role;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.repository.user.UserRepository;
import project.airbnb_backend_9.user.dto.response.UserProfileDTO;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

//    @BeforeEach
//    public void before(){
////        LocalDateTime birth = LocalDateTime.parse("1998-12-03", DateTimeFormatter.ISO_LOCAL_DATE);
//        Users user = Users.builder()
//                .email("abc@naver.com")
//                .password(bCryptPasswordEncoder.encode("123"))
//                .role(Role.USER)
//                .birth("1998-12-03")
//                .userDescription("hello")
//                .username("박경선")
//                .build();
//        userRepository.save(user);
//    }
    @Test
    @DisplayName("비밀번호 암호화")
    public void BcryptTest(){
//        LocalDateTime birth = LocalDateTime.parse("1998-12-03", DateTimeFormatter.ISO_LOCAL_DATE);
        Users user = Users.builder()
                .email("aaa@naver.com")
                .password(bCryptPasswordEncoder.encode("123"))
                .role(Role.USER)
                .birth("1998-12-03")
                .userDescription("hello")
                .username("박경선")
                .build();
        userRepository.save(user);
        assertThat(user.getUsername()).isEqualTo("박경선");

    }
    @Test
    @DisplayName("email로 회원정보 가져오기")
    public void  getUserInfo() throws Exception{

        Users user = userRepository.findByEmail("bbroadbere0@gmail.com");
        System.out.println(user.toString());
        assertThat(user.getEmail()).isEqualTo("bbroadbere0@gmail.com");
    }

    @Test
    @DisplayName("호스트 정보 조회")
    public void getHostInfo(){
        UserProfileDTO userProfile = userRepository.findUserProfile(1L);

            System.out.println(userProfile.toString());
    }

}