package project.airbnb_backend_9.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AccommodationDTO {
    private Long accommodationId;
    private String acmdImageUrl;
    private double acmdRatingAvg;
    private String mainAddress;

    public AccommodationDTO(Long accommodationId, String acmdImageUrl, double acmdRatingAvg, String mainAddress) {
        this.accommodationId = accommodationId;
        this.acmdImageUrl = acmdImageUrl;
        this.acmdRatingAvg = (double)Math.round(acmdRatingAvg * 100)/100;
        this.mainAddress = mainAddress;
    }
}
