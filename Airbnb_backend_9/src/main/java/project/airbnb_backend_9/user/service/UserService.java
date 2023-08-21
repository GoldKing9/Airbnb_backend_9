package project.airbnb_backend_9.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.repository.user.UserRepository;
import project.airbnb_backend_9.user.dto.request.SignUpDTO;
import project.airbnb_backend_9.user.dto.response.UserProfileDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void register(SignUpDTO signUpDTO) {

        String email = signUpDTO.getEmail();
        Users signUpEmail = userRepository.findByEmail(email);
        //이메일 중복 검증
        if(signUpEmail != null){
            log.info("이미 존재하는 이메일 입니다");
            throw new IllegalArgumentException();
        }


        String password = bCryptPasswordEncoder.encode(signUpDTO.getPassword());
        Users UserEntity = signUpDTO.toEntity(password);

        Users users = userRepository.save(UserEntity);
        log.info("user signup ok : {}",users.toString());
    }

    public UserProfileDTO getUserProfile(Long userId) {

        return userRepository.findUserProfile(userId);
    }
}
