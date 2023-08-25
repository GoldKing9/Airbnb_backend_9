package project.airbnb_backend_9.reservation.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class HostReservationResponseDTO {
    private Long reservationId;
    private Long userId;
    private String reservationUsername;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;
    private Long accommodationId;
    private String acmdName;
    private String acmdImageUrl;
    private String status;
    private Long totalPrice;

    public HostReservationResponseDTO(Long reservationId, Long userId, String reservationUsername, LocalDate checkIn, LocalDate checkOut, Long accommodationId, String acmdName, String acmdImageUrl, String status, Long totalPrice) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.reservationUsername = reservationUsername;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.accommodationId = accommodationId;
        this.acmdName = acmdName;
        this.acmdImageUrl = acmdImageUrl;
        this.status = status;
        this.totalPrice = totalPrice;
    }
}
