package project.airbnb_backend_9.repsoitory.accommodation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import project.airbnb_backend_9.accommodation.dto.request.SearchRequest;
import project.airbnb_backend_9.accommodation.dto.response.AccommodationDataDto;
import project.airbnb_backend_9.repository.accommodation.AccommodationRepository;
import project.airbnb_backend_9.repository.reservation.ReservationRepository;
import project.airbnb_backend_9.repository.review.ReviewRepository;

import java.time.LocalDate;

@SpringBootTest
public class AccommodationRepositoryTest {
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
//    @BeforeEach
//    void setup() {
//        for (int i = 1; i <= 10; i++) {
//            Accommodation acmd = Accommodation.builder()
////                    .guest((int) (Math.random() * 4) + 1)
////                    .bedroom((int) (Math.random() * 4) + 1)
////                    .bed((int) (Math.random() * 4) + 1)
////                    .bathroom((int) (Math.random() * 4) + 1)
////                    .price(((int) (Math.random() * 4) + 1) * 1000)
//                    .guest(i)
//                    .bedroom(i)
//                    .bed(i)
//                    .bathroom(i)
//                    .price(i)
//                    .build();
//            accommodationRepository.save(acmd);
//
//            if (i == 2) {
//                Reservation res = Reservation.builder()
//                        .accommodation(acmd)
//                        .checkIn(LocalDateTime.of(2023, 5, 3))
//                        .checkOut(LocalDate.of(2023, 5, 23))
//                        .build();
//                reservationRepository.save(res);
//
//                Review review = Review.builder()
//                        .accommodation(acmd)
//                        .rating(3L)
//                        .build();
//                Review review2 = Review.builder()
//                        .accommodation(acmd)
//                        .rating(2L)
//                        .build();
//                Review review3 = Review.builder()
//                        .accommodation(acmd)
//                        .rating(1L)
//                        .build();
//                reviewRepository.save(review);
//                reviewRepository.save(review2);
//                reviewRepository.save(review3);
//
//            }
//        }
//    }
    @Test
    void test() {
        Pageable pageable = PageRequest.of(0, 100);
        SearchRequest request = new SearchRequest("90698 Gulseth Trail", LocalDate.of(2022, 8, 16), LocalDate.of(2022, 8, 26), 0L, 6000000L, 1L, 1L, 1L, 1L);
        PageImpl<AccommodationDataDto> search = accommodationRepository.search(pageable, request);
        for (AccommodationDataDto accommodationDataDto : search) {
            System.out.println("accommodationDataDto = " + accommodationDataDto);
        }
        System.out.println(search.getContent().size());
        System.out.println(search.getTotalPages());

    }
}
