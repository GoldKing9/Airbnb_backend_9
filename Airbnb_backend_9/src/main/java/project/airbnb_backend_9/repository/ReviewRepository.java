package project.airbnb_backend_9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.airbnb_backend_9.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
