package project.airbnb_backend_9.user.dto;

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
