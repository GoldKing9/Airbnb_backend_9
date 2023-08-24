package project.airbnb_backend_9.repository.review;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.airbnb_backend_9.domain.Review;
import project.airbnb_backend_9.review.dto.response.ReviewsResponseDTO;
import project.airbnb_backend_9.user.dto.ReviewInfoDTO;

import java.util.List;

import static project.airbnb_backend_9.domain.QAccommodation.accommodation;
import static project.airbnb_backend_9.domain.QReview.review;


@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public List<ReviewInfoDTO> getReviews(Long userId) {
        return queryFactory
                .select(Projections.constructor(ReviewInfoDTO.class,
                        review.users.userId.as("userId"),
                        review.users.username,
                        Expressions.stringTemplate("DATE_FORMAT({0},'%Y년 %c월')", review.createdAt),
                        review.comment
                ))
                .from(review)
                .join(accommodation).on(review.accommodation.eq(accommodation))
                .where(accommodation.users.userId.eq(userId))
                .fetch();
    }

    @Override
    public Page<ReviewInfoDTO> getReviewsFromAccommodation(Long accommodationId, Pageable pageable) {
        List<ReviewInfoDTO> reviews = queryFactory
                .select(Projections.constructor(ReviewInfoDTO.class,
                        review.users.userId.as("userId"),
                        review.users.username,
                        Expressions.stringTemplate("DATE_FORMAT({0},'%Y년 %c월')", review.createdAt),
                        review.comment
                ))
                .from(review)
                .join(accommodation).on(review.accommodation.eq(accommodation))
                .where(accommodation.accommodationId.eq(accommodationId))
                .orderBy(review.reviewId.desc())
                .offset(pageable.getOffset()) // 몇 번째 부터 시작할거야
                .limit(pageable.getPageSize()) // 한번 조회할때 몇개까지 조회할거야
                .fetch();

        int size = queryFactory
                .select(review)
                .from(review)
                .where(review.accommodation.accommodationId.eq(accommodationId))
                .fetch().size();


        return new PageImpl<>(reviews, pageable, size);

    }

    @Override
    public int getReviewCnt(Long accommodationId) {
        return queryFactory
                .select(review)
                .from(review)
                .where(review.accommodation.accommodationId.eq(accommodationId))
                .fetch().size();
    }
}
