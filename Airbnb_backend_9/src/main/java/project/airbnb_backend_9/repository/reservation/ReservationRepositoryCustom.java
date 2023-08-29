package project.airbnb_backend_9.repository.reservation;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.airbnb_backend_9.reservation.dto.response.HostReservationResponseDTO;
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;

public interface ReservationRepositoryCustom {
    PageImpl<HostReservationResponseDTO> findHostReservation(Pageable pageable, PrincipalDetails principalDetails);
}
