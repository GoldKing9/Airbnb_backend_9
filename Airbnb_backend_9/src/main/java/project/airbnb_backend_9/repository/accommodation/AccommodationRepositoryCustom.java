package project.airbnb_backend_9.repository.accommodation;

import org.springframework.data.domain.PageImpl;
import project.airbnb_backend_9.accommodation.dto.request.SearchRequest;
import project.airbnb_backend_9.accommodation.dto.response.AccommodationDataDto;
import project.airbnb_backend_9.accommodation.dto.response.SingleAcmdResponse;
import org.springframework.data.domain.Pageable;

public interface AccommodationRepositoryCustom {
    SingleAcmdResponse findAccommodation(Long accommodationId);
    PageImpl<AccommodationDataDto> search(Pageable pageable, SearchRequest searchRequest);
}
