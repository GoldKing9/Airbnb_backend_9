package project.airbnb_backend_9.repository.user;

import project.airbnb_backend_9.user.dto.response.UserProfileDTO;

public interface UserRepositoryCustom {
    UserProfileDTO findUserProfile(Long userId);
}
