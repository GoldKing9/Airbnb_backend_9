package project.airbnb_backend_9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.airbnb_backend_9.domain.Accommodation;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}
