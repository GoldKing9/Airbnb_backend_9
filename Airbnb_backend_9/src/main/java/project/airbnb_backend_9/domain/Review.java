package project.airbnb_backend_9.domain;

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

}
