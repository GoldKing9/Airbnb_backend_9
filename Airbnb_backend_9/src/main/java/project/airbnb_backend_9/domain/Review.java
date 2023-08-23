package project.airbnb_backend_9.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.airbnb_backend_9.review.dto.ReviewDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    @Lob
    private String comment;
    private int rating;
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodationId")
    private Accommodation accommodation;


    @Builder
    public Review(String comment, int rating, LocalDateTime createdAt, Users users, Accommodation accommodation) {
        this.comment = comment;
        this.rating = rating;
        this.createdAt = LocalDateTime.now();
        this.users = users;
        this.accommodation = accommodation;
    }

    public void update(String comment){
        this.comment = comment;
    }

    public ReviewDTO toReviewDTO(){
        return ReviewDTO.builder()
                .comment(this.comment)
                .rating(this.rating)
                .build();
    }

    @Builder
    public Review(Long rating, String comment, Users users, Accommodation accommodation){
        this.rating=rating;
        this.comment=comment;
        this.users=users;
        this.accommodation=accommodation;
    }
}
