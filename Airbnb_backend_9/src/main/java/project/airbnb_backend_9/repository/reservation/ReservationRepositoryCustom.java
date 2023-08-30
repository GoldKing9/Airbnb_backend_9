package project.airbnb_backend_9.repository.reservation;

import project.airbnb_backend_9.reservation.dto.response.SingleResResponse;

public interface ReservationRepositoryCustom {
    SingleResResponse findSingleReservation(Long reservationId);

}
