package project.airbnb_backend_9.review.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter @Getter
public class ValidationErrorDTO {
    private String fieldName;
    private String message;

    public ValidationErrorDTO(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
