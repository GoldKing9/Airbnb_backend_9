package project.airbnb_backend_9.reservation.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.airbnb_backend_9.domain.Reservation;
import project.airbnb_backend_9.domain.Role;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.repository.reservation.ReservationRepository;
import project.airbnb_backend_9.repository.user.UserRepository;
import project.airbnb_backend_9.reservation.dto.request.ReservationRequestDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReservationServiceTest {
    @Autowired
    ReservationService reservationService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserRepository userRepository;
    @Test
    @DisplayName("숙박 예약")
    public void reserveAccommodation() throws Exception{
        // 숙박 id, ReservationRequestDTO, Users
        // accommodationId - 1의 guest는 2
        ReservationRequestDTO reservation = ReservationRequestDTO.builder()
                .totalPrice(100000L)
                .checkIn(LocalDate.of(2023, 8, 1))
                .checkOut(LocalDate.of(2023, 8, 8))
                .guest(2L)
                .build();

        Users user = Users.builder()
                .email("aaa@naver.com")
                .password(bCryptPasswordEncoder.encode("123"))
                .role(Role.USER)
                .birth("1998-12-03")
                .userDescription("hello")
                .username("박경선")
                .build();
        userRepository.save(user);

        reservationService.reserveAccommodation(1L,reservation,user);
        Reservation checkReservation = reservationRepository.findById(11L).get();

        assertThat(checkReservation.getGuest()).isEqualTo(2);
    }

}