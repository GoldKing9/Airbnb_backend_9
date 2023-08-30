package project.airbnb_backend_9.repository.user;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.airbnb_backend_9.domain.QImage;
import project.airbnb_backend_9.domain.Review;
import project.airbnb_backend_9.user.dto.AccommodationAndReviewDTO;
import project.airbnb_backend_9.user.dto.AccommodationInfoDTO;
import project.airbnb_backend_9.user.dto.ReviewInfoDTO;
import project.airbnb_backend_9.user.dto.response.HostProfileDTO;
import project.airbnb_backend_9.user.dto.response.UserProfileDTO;

import java.util.List;

import static project.airbnb_backend_9.domain.QAccommodation.*;
import static project.airbnb_backend_9.domain.QImage.image;
import static project.airbnb_backend_9.domain.QReview.review;
import static project.airbnb_backend_9.domain.QUsers.*;
@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public UserProfileDTO findUserProfile(Long userId) {
        UserProfileDTO response = queryFactory
                .select(Projections.constructor(UserProfileDTO.class,
                        users.username,
                        review.count().as("commentCnt"),
                        review.rating.avg().as("hostRating"),
                        users.userDescription))
                .from(users)
                .leftJoin(accommodation).on(accommodation.users.eq(users))
                .leftJoin(review).on(review.accommodation.eq(accommodation))
                .where(
                        users.userId.eq(userId)
                )
                .fetchOne();

        JPQLQuery<String> url = queryFactory
                .select(image.acmdImageUrl)
                .from(image)
                .where(image.imageId.eq(
                        JPAExpressions
                                .select(image.imageId.min())
                                .from(image)
                                .where(image.accommodation.eq(accommodation))
                ));

        JPQLQuery<Double> avg = queryFactory
                .select(review.rating.avg())
                .from(review)
                .where(review.accommodation.eq(accommodation));


        List<ReviewInfoDTO> reviews = queryFactory
                .select(Projections.constructor(ReviewInfoDTO.class,
                        review.users.userId.as("userId"),
                        review.users.username,
//                        Expressions.stringTemplate("DATE_FORMAT({0},'%Y년 %c월')", review.createdAt), Mysql에 의존적인 쿼리임
                        review.createdAt,
                        review.comment
                ))
                .from(review)
                .join(accommodation).on(review.accommodation.eq(accommodation))
                .where(accommodation.users.userId.eq(userId))
                .orderBy(review.reviewId.desc())
                .limit(6)
                .fetch();

        List<AccommodationInfoDTO> accommodations = queryFactory
                .select(Projections.constructor(AccommodationInfoDTO.class,
                        accommodation.accommodationId,
                        url,
                        avg,
                        accommodation.mainAddress
                ))
                .from(accommodation)
                .where(accommodation.users.userId.eq(userId))
                .limit(10)
                .fetch();


        if (response != null) {
            response.setReviews(reviews);
        }
        if (response != null) {
            response.setAccommodations(accommodations);
        }

        return response;

    }

    @Override
    public Page<AccommodationAndReviewDTO> findHostProfile(Long userId, Pageable pageable) {
//널포인트 에러 나는 이유 ? join을 할때 innerJoin, leftJoin 등 때문
        Long size = queryFactory
                .select(review.reviewId.count())
                .from(review)
                .where(review.accommodation.in(
                        JPAExpressions
                                .select(accommodation)
                                .from(accommodation)
                                .where(accommodation.users.userId.eq(userId)))
                ).fetchOne();

log.info("-----------AccommodationAndReviewDTO------------- 리뷰 개수 : {} ", size);
        List<AccommodationAndReviewDTO> reviews = queryFactory
                .select(Projections.constructor(AccommodationAndReviewDTO.class,
                        accommodation.accommodationId,
                        image.acmdImageUrl,
                        accommodation.acmdName,
                        review.users.userId,
                        review.comment,
                        review.users.username,
//                        Expressions.stringTemplate("DATE_FORMAT({0},'%Y년 %c월')", review.createdAt)
                        review.createdAt
                ))
                .from(review)
                .leftJoin(accommodation).on(review.accommodation.eq(accommodation))
                .innerJoin(image).on(
                        image.imageId.eq(
                                JPAExpressions
                                        .select(image.imageId.min())
                                        .from(image)
                                        .where(image.accommodation.eq(accommodation)))
                )
                .where(accommodation.users.userId.eq(userId))
                .orderBy(review.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        size = size == null ? 0:size;
        return new PageImpl<>(reviews,pageable, size);
    }
}
