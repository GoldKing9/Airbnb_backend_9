package project.airbnb_backend_9.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.airbnb_backend_9.reservation.dto.request.ReservationRequestDTO;
import project.airbnb_backend_9.reservation.service.ReservationService;
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class ReservationController {
    private final ReservationService reservationService;


    @PostMapping("/accommodation/{accommodationId}/book")
    public void reserveAccommodation(
            @PathVariable Long accommodationId,
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody ReservationRequestDTO reservationRequestDTO
            ){
            reservationService.reserveAccommodation(accommodationId, reservationRequestDTO, principalDetails.getUsers());
    }

    @GetMapping("/user/reservations")
    public void getAllReserved(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ){

    }
}
