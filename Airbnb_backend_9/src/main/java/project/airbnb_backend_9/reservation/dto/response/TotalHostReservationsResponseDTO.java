package project.airbnb_backend_9.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Getter
@NoArgsConstructor
public class TotalHostReservationsResponseDTO {
    private List<HostReservationResponseDTO> hostReservations;
    private int totalPage;
    private int currentPage;

    @Builder
    public TotalHostReservationsResponseDTO(List<HostReservationResponseDTO> hostReservations, int totalPage, int currentPage) {
        this.hostReservations = hostReservations;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }
}
