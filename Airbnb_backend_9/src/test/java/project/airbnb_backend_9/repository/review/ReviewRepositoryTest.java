package project.airbnb_backend_9.repository.review;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import project.airbnb_backend_9.user.dto.ReviewInfoDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;


    @Test
    @DisplayName("숙소별 리뷰조회")
    public void getReviews() throws Exception{
        List<ReviewInfoDTO> reviews = reviewRepository.getReviews(1L);

        for (ReviewInfoDTO review : reviews) {
            System.out.println(review.toString());
        }

    }

    @Test
    @DisplayName("리뷰 조회 페이징처리")
    public void getReviewsFromAccommodation() throws Exception{
        PageRequest pageRequest = PageRequest.of(0, 3);

        Page<ReviewInfoDTO> reviews = reviewRepository.getReviewsFromAccommodation(1L, pageRequest);
//        assertThat(reviews.getSize()).isEqualTo(10);
        System.out.println("total : "+reviews.getTotalPages()); // 전체 페이지

        System.out.println(reviews.getContent());
//        for (ReviewInfoDTO review : reviews) {
//            System.out.println(review.toString());
//        }
//        assertThat(reviews.getContent()).extracting("username").containsExactly("ibayford8");

    }

    @Test
    @DisplayName("숙소의 리뷰 전체 수 구하기")
    public void  getReviewCnt() throws Exception{
        int test = reviewRepository.getReviewCnt(4L);
        System.out.println("cnt : "+ test);


    }
}