package project.airbnb_backend_9.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.repository.UserRepository;
import project.airbnb_backend_9.user.dto.request.SignUpDTO;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void signup(SignUpDTO signUpDTO) {
        String password = bCryptPasswordEncoder.encode(signUpDTO.getPassword());
        Users UserEntity = signUpDTO.toEntity(password);

        Users users = userRepository.save(UserEntity);
        log.info("user signup ok : {}",users.toString());
    }
}
