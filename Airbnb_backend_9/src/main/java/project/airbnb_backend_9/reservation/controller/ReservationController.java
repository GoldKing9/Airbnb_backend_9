package project.airbnb_backend_9.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.airbnb_backend_9.reservation.dto.response.SingleResResponse;
import project.airbnb_backend_9.reservation.service.ReservationService;
import project.airbnb_backend_9.review.dto.response.ReviewsResponseDTO;
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    @GetMapping("/api/auth/user/reservations/{reservationId}")
    public SingleResResponse singleResResponse(@PathVariable Long reservationId,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails){
        return reservationService.singleResResponse(reservationId);
    }
    @DeleteMapping("api/auth/accommodation/{reservationId}")
    public String deleteReservation(@PathVariable Long reservationId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        reservationService.deleteReservation(reservationId,principalDetails.getUsers().getUserId());
        return "delete";
    }
}
