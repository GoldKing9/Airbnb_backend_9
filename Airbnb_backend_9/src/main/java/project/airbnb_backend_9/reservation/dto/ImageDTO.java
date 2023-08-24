package project.airbnb_backend_9.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
@NoArgsConstructor
public class ImageDTO {
    private String acmdImageUrl;

    public ImageDTO(String acmdImageUrl) {
        this.acmdImageUrl = acmdImageUrl;
    }
}
