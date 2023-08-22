package project.airbnb_backend_9.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ReviewInfoDTO {
    private Long userId;
    private String username;
    private String createdAt;
    private String comment;

    public ReviewInfoDTO(Long userId, String username, String createdAt, String comment) {
        this.userId = userId;
        this.username = username;
        this.createdAt = createdAt;
        this.comment = comment;
    }
}
