package project.airbnb_backend_9.service.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.exception.GlobalException;
import project.airbnb_backend_9.repository.user.UserRepository;
import project.airbnb_backend_9.user.dto.request.SignUpDTO;
import project.airbnb_backend_9.user.dto.response.HostProfileDTO;
import project.airbnb_backend_9.user.dto.response.UserProfileDTO;
import project.airbnb_backend_9.user.service.UserService;

import static org.assertj.core.api.Assertions.*;

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

        userService.register(signup);

        Users users = userRepository.findByEmail("aaa@gmail.com").get();
        assertThat(users.getEmail()).isEqualTo(signup.getEmail());


    }

    @Test
    @DisplayName("프로필 조회")
    @Transactional
    @Rollback(value = false)
    public void create() throws Exception{

        SignUpDTO signup = SignUpDTO.builder()
                .username("박경선")
                .password("123")
                .email("aaa@gmail.com")
                .birth("2022-12-03")
                .build();

        userService.register(signup);



        //given
        UserProfileDTO userProfile = userService.getUserProfile(11L);
        //when

        System.out.println("결과 : "+userProfile.toString());
        //then

    }

    @Test
    @DisplayName("호스트 프로필 조회(리뷰정보와 숙소정보)")
    public void  getHostProfile() throws Exception{
        Pageable pageRequest = PageRequest.of(0,4);
        HostProfileDTO hostProfile = userService.getHostProfile(10L, pageRequest);
        System.out.println("결과 : "+hostProfile.toString());
    }
}