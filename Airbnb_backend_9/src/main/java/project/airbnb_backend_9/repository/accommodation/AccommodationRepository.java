package project.airbnb_backend_9.repository.accommodation;

import org.springframework.data.jpa.repository.JpaRepository;
import project.airbnb_backend_9.domain.Accommodation;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long>, AccommodationRepositoryCustom {
}
