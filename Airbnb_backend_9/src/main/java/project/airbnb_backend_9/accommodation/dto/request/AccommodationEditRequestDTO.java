package project.airbnb_backend_9.accommodation.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccommodationEditRequestDTO {
    private Long guest;
    private Long bedroom;
    private Long bed;
    private Long bathroom;
//    private List<String> deleteImageKey;
    private String acmdName;
    private String acmdDescription;
    private Long price;
}
