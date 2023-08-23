package project.airbnb_backend_9.accommodation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.airbnb_backend_9.accommodation.dto.request.SearchRequest;
import project.airbnb_backend_9.accommodation.dto.response.AccommodationDataDto;
import project.airbnb_backend_9.accommodation.dto.response.SearchResponse;
import project.airbnb_backend_9.accommodation.dto.response.SingleAcmdResponse;
import project.airbnb_backend_9.repository.accommodation.AccommodationRepository;



@Service
@RequiredArgsConstructor
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    public SearchResponse search(Pageable pageable, SearchRequest request){
        PageImpl<AccommodationDataDto> accommodations = accommodationRepository.search(pageable, request);
        return new SearchResponse(accommodations);
    }
    public SingleAcmdResponse singleAcmdResponse(Long accommodationId){
        return accommodationRepository.findAccommodation(accommodationId);
    }
}
