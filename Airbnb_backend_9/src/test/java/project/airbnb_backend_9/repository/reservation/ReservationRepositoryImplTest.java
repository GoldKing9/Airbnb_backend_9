package project.airbnb_backend_9.repository.reservation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import project.airbnb_backend_9.reservation.dto.ReservationDTO;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;


import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReservationRepositoryImplTest {
    @Autowired
    ReservationRepository reservationRepository;


    @Test
    @DisplayName("게스트 예약 조회")
    public void getAllReservations() throws Exception{
        Pageable pageRequest = PageRequest.of(0,1);
        Page<ReservationDTO> reservations = reservationRepository.findReservations(8L, pageRequest);

//        for (ReservationDTO reservation : reservations) {
//            System.out.println(reservation.toString());
//        }

    }
}