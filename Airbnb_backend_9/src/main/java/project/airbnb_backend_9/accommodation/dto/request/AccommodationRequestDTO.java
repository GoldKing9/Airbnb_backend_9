package project.airbnb_backend_9.accommodation.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccommodationRequestDTO {
    private String mainAddress;
    private String detailAddress;
    private Long guest;
    private Long bedroom;
    private Long bed;
    private Long bathroom;
    private String acmdName;
    private String acmdDescription;
    private Long price;
}
