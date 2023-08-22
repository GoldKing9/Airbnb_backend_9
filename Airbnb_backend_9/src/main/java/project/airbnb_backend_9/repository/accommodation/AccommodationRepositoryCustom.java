package project.airbnb_backend_9.repository.accommodation;

import project.airbnb_backend_9.user.dto.AccommodationInfoDTO;

import java.util.List;

public interface AccommodationRepositoryCustom {
    List<AccommodationInfoDTO> getAccommodations(Long userId);
}
