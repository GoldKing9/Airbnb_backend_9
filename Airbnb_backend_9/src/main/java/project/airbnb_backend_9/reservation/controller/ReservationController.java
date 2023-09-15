package project.airbnb_backend_9.reservation.controller;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;
import project.airbnb_backend_9.reservation.service.ReservationService;
import project.airbnb_backend_9.reservation.dto.request.ReservationRequestDTO;
import project.airbnb_backend_9.reservation.dto.response.ReservationGuestResponseDTO;
import project.airbnb_backend_9.reservation.dto.response.TotalHostReservationsResponseDTO;
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


@Slf4j
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
    public ReservationGuestResponseDTO getAllReserved(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            Pageable pageable
    ) {
        return reservationService.getAllReservations(principalDetails.getUsers().getUserId(), pageable);
    }
    @GetMapping("/host")
    public TotalHostReservationsResponseDTO getTotalHostReservations(Pageable pageable, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return reservationService.getTotalHostReservations(pageable, principalDetails);

    }
    @GetMapping("/user/reservations/{reservationId}")
    public SingleResResponse singleResResponse(@PathVariable Long reservationId,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails){
        return reservationService.singleResResponse(reservationId);
    }
    @DeleteMapping("/accommodation/reservations/{reservationId}")
    public String deleteReservation(@PathVariable Long reservationId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        reservationService.deleteReservation(reservationId,principalDetails.getUsers().getUserId());
        return "delete";
    }
}
