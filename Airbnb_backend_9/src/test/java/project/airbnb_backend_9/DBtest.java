package project.airbnb_backend_9;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.airbnb_backend_9.domain.Role;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.repository.user.UserRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class DBtest {

    @Autowired
    EntityManager em;

    @Autowired
    UserRepository userRepository;

    @Test
    public void test(){
        LocalDateTime birth = LocalDateTime.parse("1998-12-03", DateTimeFormatter.ISO_LOCAL_DATE);
        Users user1 = Users.builder()
                .email("abc@naver.com")
                .password("123")
                .role(Role.USER)
                .birth("1998-12-03")
                .userDescription("hello")
                .username("박경선")
                .build();

        em.persist(user1);

    }

}
