package project.airbnb_backend_9.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

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
