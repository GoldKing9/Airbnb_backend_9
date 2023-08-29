package project.airbnb_backend_9.user.jwt.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.repository.user.UserRepository;

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
        log.info(" loadUserByUsername 실행 ");
        Users userEntity = userRepository.findByEmail(email).orElseThrow(
                () ->  new UsernameNotFoundException("아이디 없음, 사용자 아님") // 해줄 필요가 있을까? 여기서 에러 발생하면 unsuccessfulAuthentication이거 실행될텐데? 근데 에러가 콘솔에 터지냐 안터지냐 차이발생, 글고 메시지가 달라짐, 이거 적용하면 아이디 비벌 불일치 찍히고 안하면 로그인 실패찍힘
        );

        return new PrincipalDetails(userEntity); //PrincipalDetails이 리턴될 때 Authentication에 쏙~ 들어감, 그러면서 이 Authentication을 시큐리티 세션에 넣어줌
    }
}
