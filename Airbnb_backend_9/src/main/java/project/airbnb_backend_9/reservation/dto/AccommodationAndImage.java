package project.airbnb_backend_9.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class AccommodationAndImage {
    private Long accommodationId;
    private List<String> imageUrl;

    public AccommodationAndImage(Long accommodationId, List<String> imageUrl) {
        this.accommodationId = accommodationId;
        this.imageUrl = imageUrl;
    }
}
