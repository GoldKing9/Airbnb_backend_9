package project.airbnb_backend_9.repository.reservation;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.airbnb_backend_9.accommodation.dto.response.ImageDto;
import project.airbnb_backend_9.domain.QAccommodation;
import project.airbnb_backend_9.domain.QReservation;
import project.airbnb_backend_9.domain.QUsers;
import project.airbnb_backend_9.reservation.dto.response.SingleResResponse;

import java.time.LocalDateTime;
import java.util.List;

import static project.airbnb_backend_9.domain.QAccommodation.accommodation;
import static project.airbnb_backend_9.domain.QImage.image;
import static project.airbnb_backend_9.domain.QReservation.reservation;
import static project.airbnb_backend_9.domain.QUsers.*;
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public SingleResResponse findSingleReservation(Long reservationId){
        SingleResResponse singleResResponse=queryFactory.select(Projections.constructor(SingleResResponse.class,
                accommodation.accommodationId,
                reservation.checkIn,
                reservation.checkOut,
                users.userId,
                users.username,
                users.userDescription,
                new CaseBuilder()
                        .when(reservation.checkOut.after(LocalDateTime.now()).and(reservation.isDeleted.eq(false))).then("예약중 ")
                        .when(reservation.checkOut.before(LocalDateTime.now()).and(reservation.isDeleted.eq(false))).then("이용완료")
                        .otherwise("예약 취소"),
                reservation.totalPrice))
                .from(reservation)
                .join(users).on(users.userId.eq(reservation.users.userId))
                .join(accommodation).on(accommodation.accommodationId.eq(reservation.accommodation.accommodationId))
                .where(reservation.reservationId.eq(reservationId))
                .fetchOne();
        Long accommodationId = singleResResponse.getAccommodationId();
        List<ImageDto> images = queryFactory.select(Projections.constructor(ImageDto.class,
                        image.acmdImageUrl
                ))
                .from(accommodation)
                .leftJoin(image).on(accommodation.eq(image.accommodation))
                .where(accommodation.accommodationId.eq(accommodationId))
                .fetch();
        singleResResponse.setImages(images);
        return singleResResponse;
    };
}
