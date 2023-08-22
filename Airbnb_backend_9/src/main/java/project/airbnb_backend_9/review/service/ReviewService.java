package project.airbnb_backend_9.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.airbnb_backend_9.domain.Accommodation;
import project.airbnb_backend_9.domain.Review;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.repository.accommodation.AccommodationRepository;
import project.airbnb_backend_9.repository.review.ReviewRepository;
import project.airbnb_backend_9.repository.user.UserRepository;
import project.airbnb_backend_9.review.dto.ReviewDTO;
import project.airbnb_backend_9.review.dto.response.ReviewsResponseDTO;
import project.airbnb_backend_9.user.dto.ReviewInfoDTO;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;
    @Transactional
    public void createReview(Long accommodationId, ReviewDTO reviewDTO, Long userId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow();
        log.info("숙박 정보 : {}", accommodation.toString());

        Users users = userRepository.findById(userId).orElseThrow();
        log.info("리뷰 작성자 정보 : {}", users.toString());

        Review reviewEntity = reviewDTO.toEntity(users, accommodation);

        reviewRepository.save(reviewEntity);
    }
    @Transactional
    public ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO, Long userId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow();

        String comment = reviewDTO.getComment();

        if(! review.getUsers().getUserId().equals(userId)){
            log.info("댓글 작성자 불일치 댓글 수정 실패");
            return new ReviewDTO();
        }
            review.update(comment);
            log.info("댓글 수정 성공");
            return review.toReviewDTO();
    }
    @Transactional
    public HttpStatus deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        if(review.getUsers().getUserId().equals(userId)){
            reviewRepository.deleteById(reviewId);
            log.info("리뷰 삭제 성공");
            return HttpStatus.OK;
        }
            log.info("리뷰 작성자 불일치");
        return HttpStatus.BAD_REQUEST;
    }
    @Transactional(readOnly = true)
    public ReviewsResponseDTO getReviews(Long accommodationId, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        long pageOffset = pageable.getOffset();
        log.info("size : {}", pageSize);
        log.info("page : {}", pageOffset);

        Page<ReviewInfoDTO> reviews = reviewRepository.getReviewsFromAccommodation(accommodationId, pageable);

        return ReviewsResponseDTO.builder()
                .reviewInfos(reviews.getContent())
                .currentPage(pageOffset/pageSize)
                .totalPages(reviews.getTotalPages())
                .build();
    }
}
