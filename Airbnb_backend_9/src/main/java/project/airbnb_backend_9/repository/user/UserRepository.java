package project.airbnb_backend_9.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import project.airbnb_backend_9.domain.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
}
