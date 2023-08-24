package project.airbnb_backend_9.reservation.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.airbnb_backend_9.reservation.dto.ReservationDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReservationGuestResponseDTO {
    private List<ReservationDTO> results;
    private long totalPages;
    private long currentPage;
}
