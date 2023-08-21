package project.airbnb_backend_9.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@ToString
@NoArgsConstructor
public class ReviewDTO {
    private Long userId;
    private String username;
    private String createdAt;
    private String comment;

    public ReviewDTO(Long userId, String username, String createdAt, String comment) {
        this.userId = userId;
        this.username = username;
        this.createdAt = createdAt;
        this.comment = comment;
    }
}
