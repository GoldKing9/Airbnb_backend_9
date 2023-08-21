package project.airbnb_backend_9.repository.review;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.airbnb_backend_9.domain.QUsers;
import project.airbnb_backend_9.user.dto.ReviewDTO;
import project.airbnb_backend_9.user.dto.UserDTO;

import java.util.List;

import static project.airbnb_backend_9.domain.QAccommodation.accommodation;
import static project.airbnb_backend_9.domain.QReview.review;
import static project.airbnb_backend_9.domain.QUsers.users;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public List<ReviewDTO> getReviews(Long userId) {
        return queryFactory
                .select(Projections.constructor(ReviewDTO.class,
                        review.users.userId.as("userId"),
                        review.users.username,
                        Expressions.stringTemplate("DATE_FORMAT({0},'%Y년 %c월')", review.createdAt),
                        review.comment
                ))
                .from(review)
                .join(accommodation).on(review.accommodation.eq(accommodation))
                .where(accommodation.users.userId.eq(userId))
                .orderBy(accommodation.accommodationId.asc())
                .fetch();
    }





}
