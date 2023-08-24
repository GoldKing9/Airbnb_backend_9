package project.airbnb_backend_9.repository.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.airbnb_backend_9.reservation.dto.ReservationDTO;

public interface ReservationRepositoryCustom {
    public Page<ReservationDTO> findReservations(Long userId, Pageable pageable);
}
