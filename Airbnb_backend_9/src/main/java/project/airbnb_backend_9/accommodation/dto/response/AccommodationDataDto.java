package project.airbnb_backend_9.accommodation.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AccommodationDataDto {
    private Long accommodationId;
    private String mainAddress;
    private List<ImageDto> Images;
    private Long price;
    private double ratingAvg;


    public AccommodationDataDto(Long accommodationId, String mainAddress, Long price, double ratingAvg) {
        this.accommodationId = accommodationId;
        this.mainAddress = mainAddress;
        this.price = price;
        this.ratingAvg = ratingAvg;
    }
}
