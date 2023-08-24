package project.airbnb_backend_9.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReservationDTO {
    private Long accommodationId;
    private Long userId;
    private String city;
    private String username;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime checkIn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime checkout;
    private List<ImageDTO> images = new ArrayList<>();
}
