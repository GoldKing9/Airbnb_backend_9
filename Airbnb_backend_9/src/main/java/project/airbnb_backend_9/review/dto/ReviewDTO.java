package project.airbnb_backend_9.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.airbnb_backend_9.domain.Accommodation;
import project.airbnb_backend_9.domain.Review;
import project.airbnb_backend_9.domain.Users;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class ReviewDTO {

    @Size(max = 1000, message = "댓글 수는 최대 1000자 입니다")
    private String comment;

    @Min(value = 1, message = "별점은 최소 1점을 줘야합니다")
    @Max(value = 5, message = "별점은 최대 5점까지 가능합니다")
    private int rating;

    @Builder
    public ReviewDTO(String comment, int rating) {
        this.comment = comment;
        this.rating = rating;
    }


    public Review toEntity(Users users, Accommodation accommodation){

        return Review.builder()
                .comment(this.comment)
                .rating(this.rating)
                .users(users)
                .accommodation(accommodation)
                .build();
    }
}
