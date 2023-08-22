package project.airbnb_backend_9.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.airbnb_backend_9.user.dto.AccommodationInfoDTO;
import project.airbnb_backend_9.user.dto.ReviewInfoDTO;

import java.util.List;

@Getter @Setter
@ToString
@NoArgsConstructor
public class UserProfileDTO {
    private String username;
    private Long commentCnt;
    private Double hostRating;
    private String userDescription;
    private List<ReviewInfoDTO> reviews;
    private List<AccommodationInfoDTO> accommodations;


    public UserProfileDTO(String username, Long commentCnt, Double hostRating, String userDescription) {
        this.username = username;
        this.commentCnt = commentCnt;
        this.hostRating = (double)Math.round(hostRating * 100)/100;
        this.userDescription = userDescription;
    }
}