package project.airbnb_backend_9.repository.reservation;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.airbnb_backend_9.reservation.dto.response.HostReservationResponseDTO;
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static project.airbnb_backend_9.domain.QAccommodation.accommodation;
import static project.airbnb_backend_9.domain.QImage.*;
import static project.airbnb_backend_9.domain.QReservation.reservation;
import static project.airbnb_backend_9.domain.QUsers.*;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public PageImpl<HostReservationResponseDTO> findHostReservation(Pageable pageable, PrincipalDetails principalDetails) {
        /**
         * 1. 어디서 뭘 찾고 어디서 뭘 가져오는지 먼저 생각하기
         * 2. 같은 accommodationId 같고 있는 예약 정보 reservation에서 accommodationId로 찾기
         * 3. accommodationId 으로 같은 accommodationId 을 가지고 있는 acmd_image, reservation 찾기
         * 4. 상태 체크
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

        Long count = queryFactory.select(reservation.count())
                .from(reservation)
                .join(accommodation).on(reservation.accommodation.accommodationId.eq(accommodation.accommodationId))
                .where(accommodation.users.userId.eq(principalDetails.getUsers().getUserId()))
                .fetchOne();

        return new PageImpl<HostReservationResponseDTO>(hostReservationResponseDto, pageable, count);
    }
}
