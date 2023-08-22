package project.airbnb_backend_9.repository.accommodation;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.airbnb_backend_9.user.dto.AccommodationInfoDTO;

import java.util.List;

import static project.airbnb_backend_9.domain.QAccommodation.accommodation;
import static project.airbnb_backend_9.domain.QImage.image;
import static project.airbnb_backend_9.domain.QReview.review;

@RequiredArgsConstructor
public class AccommodationRepositoryImpl implements AccommodationRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<AccommodationInfoDTO> getAccommodations(Long userId) {
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




        return queryFactory
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
    }
}
