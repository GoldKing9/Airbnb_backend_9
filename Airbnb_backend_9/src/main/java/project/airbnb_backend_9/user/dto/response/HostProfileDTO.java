package project.airbnb_backend_9.user.dto.response;

import lombok.*;
import project.airbnb_backend_9.user.dto.AccommodationAndReviewDTO;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
@NoArgsConstructor
public class HostProfileDTO {
    private Long reviewCnt;
    private List<AccommodationAndReviewDTO> review = new ArrayList<>();
    private long totalPage;
    private long currentPage;

    @Builder
    public HostProfileDTO(Long reviewCnt, List<AccommodationAndReviewDTO> reviews, long totalPage, long currentPage) {
        this.reviewCnt = reviewCnt;
        this.review = reviews;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }
}
