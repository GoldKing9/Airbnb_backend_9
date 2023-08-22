package project.airbnb_backend_9.repository.accommodation;

import project.airbnb_backend_9.user.dto.AccommodationDTO;
import project.airbnb_backend_9.user.dto.ReviewDTO;

import java.util.List;

public interface AccommodationRepositoryCustom {
    List<AccommodationDTO> getAccommodations(Long userId);
}

