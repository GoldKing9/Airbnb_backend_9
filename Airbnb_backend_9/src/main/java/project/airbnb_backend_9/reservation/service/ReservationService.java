package project.airbnb_backend_9.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.airbnb_backend_9.domain.Accommodation;
import project.airbnb_backend_9.domain.Reservation;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.exception.GlobalException;
import project.airbnb_backend_9.repository.accommodation.AccommodationRepository;
import project.airbnb_backend_9.repository.reservation.ReservationRepository;
import project.airbnb_backend_9.repository.user.UserRepository;
import project.airbnb_backend_9.reservation.dto.request.ReservationRequestDTO;
@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;
    @Transactional
    public void reserveAccommodation(Long accommodationId, ReservationRequestDTO reservationRequestDTO, Users users) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow();
        log.info("숙소 가져오기 : {}",accommodation.toString());
        Long guest = accommodation.getGuest();

//        Users reservationUser = userRepository.findById(users.getUserId()).orElseThrow();
        log.info("사용자 정보 : {}", users.toString());

        if(reservationRequestDTO.getGuest() > guest){ // 최대 인원을 초과할 경우 예외 던져!
            throw new GlobalException("최대 인원 초과");
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
}
