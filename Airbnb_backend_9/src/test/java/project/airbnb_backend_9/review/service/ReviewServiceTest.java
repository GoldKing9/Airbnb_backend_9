package project.airbnb_backend_9.review.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import project.airbnb_backend_9.review.dto.ReviewDTO;
import project.airbnb_backend_9.review.dto.response.ReviewsResponseDTO;

@SpringBootTest
class ReviewServiceTest {
    @Autowired
    ReviewService reviewService;

    @Test
    @DisplayName("리뷰 등록")
    public void  register() throws Exception{
        //given
        ReviewDTO reviewDTO = new ReviewDTO("good~",5);

        //when
        reviewService.createReview(1L, reviewDTO,1L);

        //then

    }
    @Test
    @DisplayName("리뷰 조회")
    public void getReviews() throws Exception{
        //given
        PageRequest pageRequest = PageRequest.of(0, 3);
        ReviewsResponseDTO reviews = reviewService.getReviews(2L, pageRequest);
        //when
        System.out.println("current page : "+reviews.getCurrentPage());
        System.out.println("total : "+reviews.getTotalPages());
        //then

    }

}