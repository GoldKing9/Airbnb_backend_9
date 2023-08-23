package project.airbnb_backend_9.repository.accommodation;

import project.airbnb_backend_9.user.dto.AccommodationDTO;
import project.airbnb_backend_9.user.dto.ReviewDTO;
import project.airbnb_backend_9.accommodation.dto.request.SearchRequest;
import project.airbnb_backend_9.accommodation.dto.response.AccommodationDataDto;
import project.airbnb_backend_9.accommodation.dto.response.SingleAcmdResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public interface AccommodationRepositoryCustom {
    List<AccommodationDTO> getAccommodations(Long userId);
  
    SingleAcmdResponse findAccommodation(Long accommodationId);
    PageImpl<AccommodationDataDto> search(Pageable pageable, SearchRequest searchRequest);
  
}




