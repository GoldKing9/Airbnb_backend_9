package project.airbnb_backend_9.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import project.airbnb_backend_9.user.dto.AccommodationInfoDTO;
import project.airbnb_backend_9.user.dto.ReviewInfoDTO;

import java.util.List;

@Getter @Setter
@ToString
@NoArgsConstructor
@Slf4j
public class UserProfileDTO {
    private String username;
    private Long commentCnt;
    private double hostRating;
    private String userDescription;
    private List<ReviewInfoDTO> reviews;
    private List<AccommodationInfoDTO> accommodations;


    public UserProfileDTO(String username, Long commentCnt, double hostRating, String userDescription) {
        this.username = username;
        this.commentCnt = commentCnt;
        this.hostRating = (double)Math.round(hostRating * 100)/100;
        this.userDescription = userDescription;
    }


}