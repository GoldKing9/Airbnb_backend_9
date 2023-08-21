package project.airbnb_backend_9.repository.review;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.airbnb_backend_9.user.dto.ReviewDTO;

import java.util.List;

@SpringBootTest
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;


    @Test
    @DisplayName("숙소별 리뷰조회")
    public void getReviews() throws Exception{
        List<ReviewDTO> reviews = reviewRepository.getReviews(1L);

        for (ReviewDTO review : reviews) {
            System.out.println(review.toString());
        }

    }

}