package project.airbnb_backend_9.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    private Long reviewId;
    private String comment;
    private Long rating;
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodationId")
    private Accommodation accommodation;
    @Builder
    public Review(Long rating, String comment, Users users, Accommodation accommodation){
        this.rating=rating;
        this.comment=comment;
        this.users=users;
        this.accommodation=accommodation;
    }
}
