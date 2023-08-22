package project.airbnb_backend_9.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.review.dto.ReviewDTO;
;
import project.airbnb_backend_9.review.dto.response.ReviewsResponseDTO;
import project.airbnb_backend_9.review.dto.response.ValidationErrorDTO;
import project.airbnb_backend_9.review.service.ReviewService;
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    @PostMapping("/api/auth/user/accommodation/review/{accommodationId}")
    public String createReview(
                            @PathVariable Long accommodationId,
                            @Validated @RequestBody ReviewDTO reviewDTO,
                            @AuthenticationPrincipal PrincipalDetails principalDetails,
                            BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError field = (FieldError) objectError;
                String message = objectError.getDefaultMessage();

                log.info("field : {}", field.getField());
                log.info("message : {}", message);

                new ValidationErrorDTO(field.getField(),message);
            });
            return "ERROR";
        }
        Users users = principalDetails.getUsers();
        log.info("리뷰 작성자 정보 : {}", users.getUsername());
        reviewService.createReview(accommodationId, reviewDTO, users.getUserId());
        return "ok";
    }


    @PutMapping("/api/auth/user/accommodation/review/{reviewId}")
    public ReviewDTO updateReview(@PathVariable Long reviewId,
                                   @RequestBody ReviewDTO reviewDTO,
                                   @AuthenticationPrincipal PrincipalDetails principalDetails){
        return reviewService.updateReview(reviewId, reviewDTO, principalDetails.getUsers().getUserId());
    }

    @DeleteMapping("/api/auth/user/accommodation/review/{reviewId}")
    public HttpStatus deleteReview(@PathVariable Long reviewId,
                                   @AuthenticationPrincipal PrincipalDetails principalDetails){
        return reviewService.deleteReview(reviewId, principalDetails.getUsers().getUserId());
    }

    @GetMapping("/api/reviews/{accommodationId}")
    public ReviewsResponseDTO getReviews(@PathVariable Long accommodationId,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails,
                                         Pageable pageable){
        return reviewService.getReviews(accommodationId, pageable);
    }



}
