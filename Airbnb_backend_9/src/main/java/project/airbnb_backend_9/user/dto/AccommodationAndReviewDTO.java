package project.airbnb_backend_9.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

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

    public AccommodationAndReviewDTO(Long accommodationId, String acmdImageUrl, String acmdName, Long userId, String comment, String username, LocalDateTime createdAt) {
        this.accommodationId = accommodationId;
        this.acmdImageUrl = acmdImageUrl;
        this.acmdName = acmdName;
        this.userId = userId;
        this.comment = comment;
        this.username = username;
        this.createdAt = String.format("%d년 %02d월", createdAt.getYear(), createdAt.getMonthValue());
    }
}
