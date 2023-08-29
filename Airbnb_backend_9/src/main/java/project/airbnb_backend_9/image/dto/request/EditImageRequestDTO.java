package project.airbnb_backend_9.image.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class EditImageRequestDTO {
    private Long imageId;
    private boolean delete;
    private MultipartFile newImageFile;

    public boolean isDelete() {
        return delete;
    }
}

