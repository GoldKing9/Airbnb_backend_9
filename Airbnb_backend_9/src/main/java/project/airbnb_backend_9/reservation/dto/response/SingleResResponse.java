package project.airbnb_backend_9.reservation.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import project.airbnb_backend_9.accommodation.dto.response.ImageDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@RequiredArgsConstructor
public class SingleResResponse {
    private Long accommodationId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Long userId;
    private String username;
    private String userDescription;
    private String status;
    private Long totalPrice;
    private List<ImageDto> images;

    public SingleResResponse(Long accommodationId, LocalDate checkIn, LocalDate checkOut, Long userId, String username, String userDescription,String status, Long totalPrice) {
        this.accommodationId = accommodationId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.userId = userId;
        this.username = username;
        this.userDescription = userDescription;
        this.status = status;
        this.totalPrice = totalPrice;
    }
}


