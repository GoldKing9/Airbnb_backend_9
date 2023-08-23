package project.airbnb_backend_9.accommodation.dto.response;

import org.springframework.data.domain.PageImpl;
import project.airbnb_backend_9.accommodation.dto.request.SearchRequest;

import java.util.List;

public class SearchResponse {
    private List<AccommodationDataDto> results;
    private int totalPages;
    private int currentPage;

    public SearchResponse(PageImpl<AccommodationDataDto> accommodations){
        this.results=accommodations.getContent();
        this.totalPages=accommodations.getTotalPages();
        this.currentPage=accommodations.getNumber();
    }
}
