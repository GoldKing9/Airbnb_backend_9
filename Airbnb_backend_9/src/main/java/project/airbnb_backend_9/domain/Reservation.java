package project.airbnb_backend_9.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE Reservation SET is_deleted = true WHERE reservation_id = ?")
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
    public Reservation(LocalDateTime checkIn, LocalDateTime checkOut, Long totalPrice, Long guest, boolean isDeleted, Users users, Accommodation accommodation) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
        this.guest = guest;
        this.isDeleted = isDeleted;
        this.users = users;
        this.accommodation = accommodation;
    }
}
