package project.airbnb_backend_9.accommodation.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImageDto {
    private String acmdImageUrl;
    public ImageDto(String acmdImageUrl){
        this.acmdImageUrl=acmdImageUrl;
    }
}
