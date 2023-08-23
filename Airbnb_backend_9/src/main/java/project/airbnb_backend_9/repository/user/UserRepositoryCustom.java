package project.airbnb_backend_9.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.airbnb_backend_9.user.dto.AccommodationAndReviewDTO;
import project.airbnb_backend_9.user.dto.response.HostProfileDTO;
import project.airbnb_backend_9.user.dto.response.UserProfileDTO;

public interface UserRepositoryCustom {
    UserProfileDTO findUserProfile(Long userId);

    Page<AccommodationAndReviewDTO> findHostProfile(Long userId, Pageable pageable);
    Long getReviewCnt(Long userId); // 사용안함 테스트용
}
