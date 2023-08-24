package project.airbnb_backend_9.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Long totalPrice;
    private Long guest;
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="accommodationId")
    private Accommodation accommodation;
    @Builder
    public Reservation(LocalDate checkIn, LocalDate checkOut, Long totalPrice, Long guest, boolean isDeleted, Users users, Accommodation accommodation) {
        this.checkIn = checkIn.atStartOfDay();
        this.checkOut = checkOut.atStartOfDay();
        this.totalPrice = totalPrice;
        this.guest = guest;
        this.isDeleted = isDeleted;
        this.users = users;
        this.accommodation = accommodation;
        log.info("뭐가 문제냐 check in : {}", checkIn);
        log.info("뭐가 문제냐 check out : {}", checkOut);
    }
}
