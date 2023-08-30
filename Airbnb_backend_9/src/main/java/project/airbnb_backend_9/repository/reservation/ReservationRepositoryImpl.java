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
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import project.airbnb_backend_9.domain.*;
import project.airbnb_backend_9.reservation.dto.ImageDTO;
import project.airbnb_backend_9.reservation.dto.ReservationDTO;
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;
import project.airbnb_backend_9.reservation.dto.AccommodationAndImage;
import project.airbnb_backend_9.reservation.dto.response.HostReservationResponseDTO;


import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.querydsl.core.group.GroupBy.*;
import static project.airbnb_backend_9.domain.QUsers.*;
import static project.airbnb_backend_9.domain.QImage.*;
import static project.airbnb_backend_9.domain.QReservation.reservation;
import static project.airbnb_backend_9.domain.QAccommodation.accommodation;



@Slf4j
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<ReservationDTO> findReservations(Long userId, Pageable pageable) {

        // 예약중, 예약취소, 이용 완료 상태
        StringExpression status = new CaseBuilder()
                .when(
                        reservation.checkOut.after(LocalDateTime.now())
                                .and(reservation.isDeleted.eq(false))
                )
                .then("예약 중")
                .when(
                        reservation.checkOut.before(LocalDateTime.now())
                                .and(reservation.isDeleted.eq(false))
                )
                .then("이용완료")
                .otherwise("예약 취소").as("status");


        /**
         * 그룹화를 하게 되면 groupBy 이후에 페이징 처리가 불가능함 따라서 두 단계의 쿼리로 나누어 처리해준다
         * 1. 원하는 조건으로 그룹화된 id들만 페이징 처리
         * 2. 첫번째 쿼리에서 가져온 id를 기반으로 상세데이터 가져옴
         */


        // 1. 그룹화된 accommodation ID만 페이징 처리해서 가져오기
        List<Long> pagedAccommodationIds = queryFactory
                .select(accommodation.accommodationId)
                .from(accommodation)
                .join(reservation).on(reservation.accommodation.eq(accommodation))
                .join(users).on(reservation.users.eq(users))
                .where(users.userId.eq(userId))
                .groupBy(accommodation.accommodationId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 2. 가져온 ID를 기반으로 상세 데이터 가져오기
        List<ReservationDTO> reservations = queryFactory
                .selectFrom(accommodation)
                .join(image).on(image.accommodation.eq(accommodation))
                .join(reservation).on(reservation.accommodation.eq(accommodation))
                .join(users).on(reservation.users.eq(users))
                .where(
                        users.userId.eq(userId)
                                .and(accommodation.accommodationId.in(pagedAccommodationIds))
                )
                .orderBy(reservation.reservationId.desc())
                .transform(groupBy(accommodation.accommodationId).list(Projections.constructor(
                        ReservationDTO.class,
                        accommodation.accommodationId,
                        users.userId,
                        users.username,
                        accommodation.mainAddress.as("city"),
//                        Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d')", reservation.checkIn),
                        reservation.checkIn,
//                        Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d')", reservation.checkOut),
                        reservation.checkOut,
                        status,
                        list(Projections.constructor(ImageDTO.class,
                                image.acmdImageUrl))
                )));

        return new PageImpl<>(reservations, pageable, reservations.size());

    }
    @Override
    public PageImpl<HostReservationResponseDTO> findHostReservation(Pageable pageable, PrincipalDetails principalDetails) {
        /**
         * 1. 어디서 뭘 찾고 어디서 뭘 가져오는지 먼저 생각하기
         * 2. 상태 체크
         * - checkOut > LocalDate.now() && isDeleted == false, 예약중
         * - checkOut < LocalDate.now() && isDeleted == false, 이용완료
         * - isDeleted == true, 취소
         */


        List<HostReservationResponseDTO> hostReservationResponseDto = queryFactory
                .select(Projections.constructor(HostReservationResponseDTO.class,
                        reservation.reservationId,
                        users.userId,
                        users.username,
                        reservation.checkIn,
                        reservation.checkOut,
                        accommodation.accommodationId,
                        accommodation.acmdName,
                        image.acmdImageUrl.min(),
                        new CaseBuilder()
                                .when(reservation.checkOut.after(LocalDateTime.now()).and(reservation.isDeleted.eq(false))).then("예약 중")
                                .when(reservation.checkOut.before(LocalDateTime.now()).and(reservation.isDeleted.eq(false))).then("이용완료")
                                .otherwise("예약 취소"),
                        reservation.totalPrice))
                .from(reservation)
                .leftJoin(users).on(users.userId.eq(reservation.users.userId))
                .leftJoin(accommodation).on(accommodation.accommodationId.eq(reservation.accommodation.accommodationId))
                .leftJoin(image).on(image.accommodation.accommodationId.eq(accommodation.accommodationId))
                .where(accommodation.users.userId.eq(principalDetails.getUsers().getUserId()))
                .groupBy(reservation.reservationId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // accommodation에 있는 users의 userId는 숙소 가진 호스트의 아이디!
        // reservation에 있는 users의 userId는 게스트의 아이디!

        Long total = queryFactory.select(reservation.count())
                .from(reservation)
                .join(accommodation).on(reservation.accommodation.accommodationId.eq(accommodation.accommodationId))
                .where(accommodation.users.userId.eq(principalDetails.getUsers().getUserId()))
                .fetchOne();

        return new PageImpl<HostReservationResponseDTO>(hostReservationResponseDto, pageable, total);

    }

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
                        .when(reservation.checkOut.after(LocalDateTime.now()).and(reservation.isDeleted.eq(false))).then("예약 중")
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
