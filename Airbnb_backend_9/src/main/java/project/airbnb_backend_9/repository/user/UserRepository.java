package project.airbnb_backend_9.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import project.airbnb_backend_9.domain.Users;

//@Repository라는 어노테이션이 없어도 IoC됨, 왜? JpaRepository를 상속했기 때문
public interface UserRepository extends JpaRepository<Users, Long> , UserRepositoryCustom{

    //findBy 규칙 => email문법
    // select * from user where email = ?
    public Users findByEmail(String email); // jpa 쿼리 메서드


}
