package project.airbnb_backend_9.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.airbnb_backend_9.reservation.dto.response.TotalHostReservationsResponseDTO;
import project.airbnb_backend_9.reservation.service.ReservationService;
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/api/auth/host")
    public TotalHostReservationsResponseDTO getTotalHostReservations(Pageable pageable, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return reservationService.getTotalHostReservations(pageable, principalDetails);
    }
}
