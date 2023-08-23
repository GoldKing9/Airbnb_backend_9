package project.airbnb_backend_9.accommodation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class SingleAcmdResponse {
    private String mainAddress;
    private String detailAddress;
    private Long price;
    private Long userId;
    private String username;
    private String userDescription;
    private Long bed;
    private Long bedroom;
    private Long bathroom;
    private Long guest;
    private double ratingAvg;
    private Long reviewCnt;
    private List<ImageDto> images;
    public SingleAcmdResponse(String mainAddress, String detailAddress, Long price, Long userId, String username, String userDescription, Long bed, Long bedroom, Long bathroom,Long guest, double ratingAvg, Long reviewCnt) {
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.price = price;
        this.userId = userId;
        this.username = username;
        this.userDescription = userDescription;
        this.guest = guest;
        this.bed = bed;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.ratingAvg = ratingAvg;
        this.reviewCnt = reviewCnt;
    }

}
