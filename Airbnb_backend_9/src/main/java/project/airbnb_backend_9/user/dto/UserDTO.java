package project.airbnb_backend_9.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserDTO {
    private String username;
    private Long commentCnt;
    private Double hostRating;
    private String userDescription;

    public UserDTO(String username, Long commentCnt, Double hostRating, String userDescription) {
        this.username = username;
        this.commentCnt = commentCnt;
        this.hostRating = hostRating;
        this.userDescription = userDescription;
    }
}

