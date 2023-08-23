package project.airbnb_backend_9.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter @Setter
@ToString
public class AccommodationAndReviewDTO {
    private Long accommodationId;
    private String acmdImageUrl;
    private String acmdName;
    private Long userId;
    private String comment;
    private String username;
    private String createdAt;

    public AccommodationAndReviewDTO(Long accommodationId, String acmdImageUrl, String acmdName, Long userId, String comment, String username, String createdAt) {
        this.accommodationId = accommodationId;
        this.acmdImageUrl = acmdImageUrl;
        this.acmdName = acmdName;
        this.userId = userId;
        this.comment = comment;
        this.username = username;
        this.createdAt = createdAt;
    }
}
