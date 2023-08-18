package project.airbnb_backend_9.user.jwt.auth;


import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.airbnb_backend_9.domain.Role;
import project.airbnb_backend_9.domain.Users;

import java.util.ArrayList;
import java.util.Collection;
//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다
//로그인 진행이 완료가 되면 시큐리티가 가진 session을 만들어 준다. (Security ContextHolder)라는 키값을 저장
// 이 시큐리티가 가진 세션에 들어갈 수 있는 정보는 오브젝트가 정해져 있다(오브젝트 타입 => Authentication타입 객체)
// Authentication 안에 뭐가 있냐면 User정보가 있어야 됨 -> 이것도 클래스가 정해져 있다
// User 오브젝트 타입 => UserDetails 타입 객체
//Security가 가지고 있는 Session영역이 있다. 여기에다 세션정보를 저장해주는데, 여기 들어갈 수 있는 객체가 Authentication => 이 객체안에 유저정보를 저장할 때 UserDetails타입 이어야함
// Security Session => Authentication => UserDetails(PrincipalDetails)
@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {

    private final Users users;//콤포지션

    //해당 Users의 권한을 리턴하는 곳!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return String.valueOf(users.getRole());
            }
        }); //add()안에 들어가는 타입 GrantedAuthority
        return collect;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { //계정이 만료되었는가?
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정이 잠겼는가?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //계정의 비밀번호가 일정 기간이 지났는가?, 너무 오래 사용한건 아닌지
        return true;
    }

    @Override
    public boolean isEnabled() { //계정이 활성화 되있니?
        //예시 -> 1년이 지난 회원은 휴면계정으로 바꾸고싶을때 : 현재시간 - 로그인시간 = 1년을 초과했다 ? => return false로
        return true;
    }
}
