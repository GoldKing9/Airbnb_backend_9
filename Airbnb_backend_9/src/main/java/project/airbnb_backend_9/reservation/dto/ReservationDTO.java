package project.airbnb_backend_9.reservation.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import project.airbnb_backend_9.domain.Image;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReservationDTO {
    private Long accommodationId;
    private Long userId;
    private String username;
    private String city;
    private String checkInDate;
    private String checkOutDate;
    private String status;
    private List<ImageDTO> images = new ArrayList<>();

    public ReservationDTO(Long accommodationId, Long userId, String username, String city, LocalDateTime checkIn, LocalDateTime checkout, String status, List<ImageDTO> images) {
        this.accommodationId = accommodationId;
        this.userId = userId;
        this.username = username;
        this.city = city;
        this.checkInDate = String.format("%d-%02d-%02d",checkIn.getYear(), checkIn.getMonthValue(),checkIn.getDayOfMonth());
        this.checkOutDate = String.format("%d-%02d-%02d",checkout.getYear(), checkout.getMonthValue(),checkout.getDayOfMonth());
        this.status = status;
        this.images = images;
    }


}
