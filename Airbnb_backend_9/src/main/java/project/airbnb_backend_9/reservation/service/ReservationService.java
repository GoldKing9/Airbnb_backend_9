package project.airbnb_backend_9.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.airbnb_backend_9.repository.reservation.ReservationRepository;
import project.airbnb_backend_9.reservation.dto.response.HostReservationResponseDTO;
import project.airbnb_backend_9.reservation.dto.response.TotalHostReservationsResponseDTO;
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public TotalHostReservationsResponseDTO getTotalHostReservations(Pageable pageable, PrincipalDetails principalDetails) {
        PageImpl<HostReservationResponseDTO> hostReservation = reservationRepository.findHostReservation(pageable, principalDetails);
        return TotalHostReservationsResponseDTO.builder()
                .hostReservations(hostReservation.getContent())
                .totalPage(hostReservation.getTotalPages())
                .currentPage(hostReservation.getNumber())
                .build();
    }
}
