package project.airbnb_backend_9.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.airbnb_backend_9.accommodation.dto.response.SingleAcmdResponse;
import project.airbnb_backend_9.domain.QReservation;
import project.airbnb_backend_9.domain.Reservation;
import project.airbnb_backend_9.exception.GlobalException;
import project.airbnb_backend_9.repository.reservation.ReservationRepository;
import project.airbnb_backend_9.reservation.dto.response.SingleResResponse;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    public SingleResResponse singleResResponse(Long reservationId){
        return reservationRepository.findSingleReservation(reservationId);
    }
    @Transactional
    public void deleteReservation(Long reservationId, Long AuthUserId){
        Reservation findReservation = reservationRepository.findById(reservationId).orElseThrow();
        Long reservationUserId = findReservation.getUsers().getUserId();
        if(reservationUserId==AuthUserId){
        reservationRepository.delete(findReservation);
        }
        else{
            throw new GlobalException("예약한 당사자가 아닙니다.");
        }

    }
}
