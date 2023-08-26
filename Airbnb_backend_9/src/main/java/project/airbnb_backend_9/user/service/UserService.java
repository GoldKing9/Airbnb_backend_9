package project.airbnb_backend_9.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.exception.GlobalException;
import project.airbnb_backend_9.repository.user.UserRepository;
import project.airbnb_backend_9.user.dto.AccommodationAndReviewDTO;
import project.airbnb_backend_9.user.dto.request.SignUpDTO;
import project.airbnb_backend_9.user.dto.request.UpdateUserDTO;
import project.airbnb_backend_9.user.dto.response.HostProfileDTO;
import project.airbnb_backend_9.user.dto.response.UserProfileDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void register(SignUpDTO signUpDTO) {

        String email = signUpDTO.getEmail();
        Users signUpEmail = userRepository.findByEmail(email);
        //이메일 중복 검증
        if(signUpEmail != null){
            log.info("이미 존재하는 이메일 입니다");
            throw new GlobalException("이미 존재하는 이메일 입니다");
        }


        String password = bCryptPasswordEncoder.encode(signUpDTO.getPassword());
        Users UserEntity = signUpDTO.toEntity(password);

        Users users = userRepository.save(UserEntity);
        log.info("user signup ok : {}",users.toString());
    }

    @Transactional(readOnly = true)
    public UserProfileDTO getUserProfile(Long userId) {
        return userRepository.findUserProfile(userId);
    }

    @Transactional
    public void updateProfile(UpdateUserDTO updateUserDTO, Users users) {
//test1 -> 꺼내와서 변경하는 방법을 사용해라!
        log.info("findBy로 조회 후 자기소개 등록");
        Users user = userRepository.findById(users.getUserId()).orElseThrow(() -> new GlobalException("사용자 조회 실패"));
        user.updateDescription(updateUserDTO.getUserDescription());
//test2 -> 활용 1편 85쪽 참고
//        log.info("자기소개 등록 후 저장");
//        users.updateDescription(updateUserDTO.getUserDescription());
//        userRepository.save(users);
    }

    @Transactional
    public HostProfileDTO getHostProfile(Long userId, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        long pageOffset = pageable.getOffset();

        Page<AccommodationAndReviewDTO> hostProfile = userRepository.findHostProfile(userId, pageable);
        log.info("전체 리뷰 수 : {}", hostProfile.getTotalElements());

        return HostProfileDTO.builder()
                .reviewCnt(hostProfile.getTotalElements())
                .reviews(hostProfile.getContent())
                .totalPage(hostProfile.getTotalPages())
                .currentPage(pageOffset/pageSize)
                .build();
    }
}
