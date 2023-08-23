package project.airbnb_backend_9.repository.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.airbnb_backend_9.review.dto.response.ReviewsResponseDTO;
import project.airbnb_backend_9.user.dto.ReviewInfoDTO;

import java.util.List;

public interface ReviewRepositoryCustom {
    List<ReviewInfoDTO> getReviews(Long userId);
    Page<ReviewInfoDTO> getReviewsFromAccommodation(Long accommodationId, Pageable pageable);

    int getReviewCnt(Long accommodationId);
}
