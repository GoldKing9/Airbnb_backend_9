package project.airbnb_backend_9.repository.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.airbnb_backend_9.reservation.dto.ReservationDTO;
import project.airbnb_backend_9.reservation.dto.response.SingleResResponse;
import org.springframework.data.domain.PageImpl;
import project.airbnb_backend_9.reservation.dto.response.HostReservationResponseDTO;
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;

public interface ReservationRepositoryCustom {
    Page<ReservationDTO> findReservations(Long userId, Pageable pageable);

    PageImpl<HostReservationResponseDTO> findHostReservation(Pageable pageable, PrincipalDetails principalDetails);

    SingleResResponse findSingleReservation(Long reservationId);
}
