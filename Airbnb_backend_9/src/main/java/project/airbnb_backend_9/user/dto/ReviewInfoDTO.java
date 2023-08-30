package project.airbnb_backend_9.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
public class ReviewInfoDTO {
    private Long userId;
    private String username;
    private String createdAt;
    private String comment;

    public ReviewInfoDTO(Long userId, String username, LocalDateTime createdAt, String comment) {
        this.userId = userId;
        this.username = username;
        this.createdAt = String.format("%d년 %02d월", createdAt.getYear(), createdAt.getMonthValue());
        this.comment = comment;
    }
}
