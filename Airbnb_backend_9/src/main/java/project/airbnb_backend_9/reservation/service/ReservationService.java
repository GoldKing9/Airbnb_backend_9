package project.airbnb_backend_9.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.airbnb_backend_9.domain.Accommodation;
import project.airbnb_backend_9.domain.Reservation;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.exception.GlobalException;
import project.airbnb_backend_9.repository.accommodation.AccommodationRepository;
import project.airbnb_backend_9.repository.reservation.ReservationRepository;
import project.airbnb_backend_9.repository.user.UserRepository;
import project.airbnb_backend_9.reservation.dto.ReservationDTO;
import project.airbnb_backend_9.reservation.dto.request.ReservationRequestDTO;
import project.airbnb_backend_9.reservation.dto.response.ReservationGuestResponseDTO;
import project.airbnb_backend_9.review.dto.response.ReviewsResponseDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AccommodationRepository accommodationRepository;
    @Transactional
    public void reserveAccommodation(Long accommodationId, ReservationRequestDTO reservationRequestDTO, Users users) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow();
        log.info("숙소 가져오기 : {}",accommodation.toString());
        Long guest = accommodation.getGuest();

        if(reservationRequestDTO.getGuest() > guest){ // 최대 인원을 초과할 경우 예외 던져!
            throw new GlobalException("숙소 최대 인원 초과");
        }

        Reservation reservation = Reservation.builder()
                .checkIn(reservationRequestDTO.getCheckIn())
                .checkOut(reservationRequestDTO.getCheckOut())
                .accommodation(accommodation)
                .totalPrice(reservationRequestDTO.getTotalPrice())
                .guest(reservationRequestDTO.getGuest())
                .users(users)
                .build();

        reservationRepository.save(reservation);
        log.info("숙소 예약하기 완료");
    }

    public ReservationGuestResponseDTO getAllReservations(Long userId, Pageable pageable) {
        Page<ReservationDTO> reservations = reservationRepository.findReservations(userId, pageable);
        int pageSize = pageable.getPageSize();
        long pageOffset = pageable.getOffset();

        return ReservationGuestResponseDTO.builder()
                .results(reservations.getContent())
                .currentPage(pageOffset/pageSize)
                .totalPages(reservations.getTotalPages())
                .build();

    }
}
