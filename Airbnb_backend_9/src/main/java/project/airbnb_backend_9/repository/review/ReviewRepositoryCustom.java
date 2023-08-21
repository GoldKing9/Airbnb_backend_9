package project.airbnb_backend_9.repository.review;

import project.airbnb_backend_9.user.dto.ReviewDTO;

import java.util.List;

public interface ReviewRepositoryCustom {
    List<ReviewDTO> getReviews(Long userId);
}
