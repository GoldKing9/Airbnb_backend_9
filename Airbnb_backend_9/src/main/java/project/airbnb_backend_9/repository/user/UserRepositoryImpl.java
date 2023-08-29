package project.airbnb_backend_9.repository.user;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.airbnb_backend_9.user.dto.AccommodationInfoDTO;
import project.airbnb_backend_9.user.dto.ReviewInfoDTO;
import project.airbnb_backend_9.user.dto.response.UserProfileDTO;

import java.util.List;

import static project.airbnb_backend_9.domain.QAccommodation.accommodation;
import static project.airbnb_backend_9.domain.QImage.image;
import static project.airbnb_backend_9.domain.QReview.review;
import static project.airbnb_backend_9.domain.QUsers.users;


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
                        Expressions.stringTemplate("DATE_FORMAT({0},'%Y년 %c월')", review.createdAt),
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





}
