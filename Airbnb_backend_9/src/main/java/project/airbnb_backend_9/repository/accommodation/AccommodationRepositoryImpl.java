package project.airbnb_backend_9.repository.accommodation;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import lombok.RequiredArgsConstructor;

import project.airbnb_backend_9.user.dto.AccommodationInfoDTO;
import project.airbnb_backend_9.accommodation.dto.request.SearchRequest;
import project.airbnb_backend_9.accommodation.dto.response.AccommodationDataDto;
import project.airbnb_backend_9.accommodation.dto.response.ImageDto;
import project.airbnb_backend_9.accommodation.dto.response.SingleAcmdResponse;
import static project.airbnb_backend_9.domain.QAccommodation.accommodation;
import static project.airbnb_backend_9.domain.QImage.image;
import static project.airbnb_backend_9.domain.QReview.review;
import static project.airbnb_backend_9.domain.QReservation.reservation;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;


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
  
  @Override
    public SingleAcmdResponse findAccommodation(Long accommodationId){
        SingleAcmdResponse singleAcmdResponse = queryFactory.select(Projections.constructor(SingleAcmdResponse.class,
                accommodation.mainAddress,
                accommodation.detailAddress,
                accommodation.price,
                accommodation.users.userId,
                accommodation.users.username,
                accommodation.users.userDescription,
                accommodation.bed,
                accommodation.bedroom,
                accommodation.bathroom,
                accommodation.guest,
                review.rating.avg(),
                review.count()
            ))
                .from(accommodation)
                .leftJoin(review).on(accommodation.eq(review.accommodation))
                .where(accommodation.accommodationId.eq(accommodationId))
                .fetchOne();
        List<ImageDto> images = queryFactory.select(Projections.constructor(ImageDto.class,
                        image.acmdImageUrl
                ))
                .from(accommodation)
                .leftJoin(image).on(accommodation.eq(image.accommodation))
                .where(accommodation.accommodationId.eq(accommodationId))
                .fetch();
        singleAcmdResponse.setImages(images);
        return singleAcmdResponse;
    }
  
    @Override
    public PageImpl<AccommodationDataDto> search(Pageable pageable, SearchRequest request){
        List<AccommodationDataDto> accommodations = queryFactory.select(Projections.constructor(AccommodationDataDto.class,
                accommodation.accommodationId,
                accommodation.mainAddress,
                accommodation.price,
                review.rating.avg()
        ))
                .from(accommodation)
                .leftJoin(review).on(accommodation.eq(review.accommodation))
                .where(
                        containMainAddress(request.getMainAddress()),
                        periodDate(request.getCheckIn(),request.getCheckout()),
                        betweenPrice(request.getMinPrice(),request.getMaxPrice()),
                        goeGuest(request.getGuest()),
                        goeBathroom(request.getBathroom()),
                        goeBedroom(request.getBedroom()),
                        goeBed(request.getBed())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(accommodation.accommodationId)
                .fetch();
        fillAccommodationImages(accommodations);

        Long count = queryFactory.select(
                accommodation.countDistinct()
        )
                .from(accommodation)
                .leftJoin(review).on(accommodation.eq(review.accommodation))
                .where(
                        containMainAddress(request.getMainAddress()),
                        periodDate(request.getCheckIn(),request.getCheckout()),
                        betweenPrice(request.getMinPrice(),request.getMaxPrice()),
                        goeGuest(request.getGuest()),
                        goeBathroom(request.getBathroom()),
                        goeBedroom(request.getBedroom()),
                        goeBed(request.getBed())
                )
                .fetchOne();
        return new PageImpl<>(accommodations, pageable, count);
    }
  
    private void fillAccommodationImages(List<AccommodationDataDto> accommodations){
        List<Long> acmdId = accommodations.stream()
                .map(AccommodationDataDto::getAccommodationId)
                .collect(Collectors.toList());

        Map<Long, List<ImageDto>> imageMap = queryFactory.select(
                accommodation.accommodationId,
                image.acmdImageUrl
                )
                .from(accommodation)
                .leftJoin(image).on(accommodation.eq(image.accommodation))
                .where(accommodation.accommodationId.in(acmdId))
                .transform(GroupBy.groupBy(accommodation.accommodationId)
                        .as(GroupBy.list(Projections.constructor(ImageDto.class,image.acmdImageUrl))));
        accommodations.forEach(a ->
                    a.setImages(imageMap.getOrDefault(a.getAccommodationId(),new ArrayList<>()))
                );
    }
  
    private BooleanExpression periodDate(LocalDate checkIn, LocalDate checkout) {
        if (checkIn == null || checkout == null) {
            return null;
        }
        return accommodation.accommodationId.notIn(
                JPAExpressions.select(
                                reservation.accommodation.accommodationId
                        )
                        .from(reservation)
                        .where(
                                reservation.checkOut.goe(checkIn.atStartOfDay())
                                        .and(reservation.checkIn.loe(checkout.atTime(LocalTime.MAX)))
                        )
        );
    }
  
    //BooleanExpression은 null 반환 시 자동으로 조건절에서 제거 된다. (단, 모든 조건이 null이 발생 시 전체 엔티티를 불러오게 되므로 대장애가 발생할 수 있음)
    private BooleanExpression betweenPrice(Long minPrice, Long maxPrice) {
        BooleanExpression isMinPrice = accommodation.price.goe(minPrice);
        BooleanExpression isMaxPrice = accommodation.price.loe(maxPrice);
        return Expressions.allOf(isMinPrice, isMaxPrice);

    }

    private BooleanExpression goeBed(Long bed) {
        return isEmpty(bed) ? null : accommodation.bed.goe(bed);
    }

    private BooleanExpression goeBedroom(Long bedroom) {
        return isEmpty(bedroom) ? null : accommodation.bedroom.goe(bedroom);
    }

    private BooleanExpression goeBathroom(Long bathroom) {
        return isEmpty(bathroom) ? null : accommodation.bathroom.goe(bathroom);
    }

    private BooleanExpression goeGuest(Long guest) {
        return isEmpty(guest) ? null : accommodation.guest.goe(guest);
    }

    private BooleanExpression containMainAddress(String mainAddress) {
        return isEmpty(mainAddress) ? null : accommodation.mainAddress.contains(mainAddress);
    }
  
}
