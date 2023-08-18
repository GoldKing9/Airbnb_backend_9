package project.airbnb_backend_9.user.jwt.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.repository.UserRepository;

//시큐리티 설정에서 .loginProcessingUrl("/login")을 걸어놨으므로
// /login 요청이 오면, 자동으로 UserDetailsService타입으로 IoC되어있는 loadUserByUsername 함수가 실행됨 -> 규칙임
@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {
    /**
     * /login호출 -> 스프링은 IoC컨테이너에서 UserDetailsService로 등록되어있는 서비스를 찾는다
     * PrincipalDetailsService를 찾음, 이걸 찾으면 바로 loadUserByUsername를 실행
     * 이때 넘어온 파라미터 email을 가져옴
     */

    private final UserRepository userRepository;
    // 시큐리티 session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { //request로 오는 인자값이 파라미터명과 일치해야함
        log.info("email : {}", email );
        Users userEntity = userRepository.findByEmail(email);
        log.info("loadUserByUsername : {}, {}, {}", userEntity.getUsername(), userEntity.getEmail(), userEntity.getPassword());

        //todo null일경우 처리해줘야함
        if(userEntity == null){
            log.info("아이디가 없음, 사용자 아님");
        }
        log.info("userEntity 존재 : {}", userEntity);
        return new PrincipalDetails(userEntity); //PrincipalDetails이 리턴될 때 Authentication에 쏙~ 들어감, 그러면서 이 Authentication을 시큐리티 세션에 넣어줌
    }
}
