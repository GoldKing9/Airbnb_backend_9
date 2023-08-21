package project.airbnb_backend_9.repository.user;

import project.airbnb_backend_9.user.dto.UserDTO;
import project.airbnb_backend_9.user.dto.response.UserProfileDTO;

import java.util.List;

public interface UserRepositoryCustom {
    UserProfileDTO findUserProfile(Long userId);
}
