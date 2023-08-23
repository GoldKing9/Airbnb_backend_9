package project.airbnb_backend_9.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import project.airbnb_backend_9.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
