package project.airbnb_backend_9.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.airbnb_backend_9.user.dto.ReviewInfoDTO;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ReviewsResponseDTO {
    private List<ReviewInfoDTO> result = new ArrayList<>();
    private long totalPages;
    private long currentPage;

    @Builder
    public ReviewsResponseDTO(List<ReviewInfoDTO> reviewInfos, long totalPages, long currentPage) {
        this.result = reviewInfos;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }
}
