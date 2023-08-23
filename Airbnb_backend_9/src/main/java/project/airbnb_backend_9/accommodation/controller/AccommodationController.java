package project.airbnb_backend_9.accommodation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.airbnb_backend_9.accommodation.dto.request.SearchRequest;
import project.airbnb_backend_9.accommodation.dto.response.SearchResponse;
import project.airbnb_backend_9.accommodation.dto.response.SingleAcmdResponse;
import project.airbnb_backend_9.accommodation.service.AccommodationService;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping("/api/accommodation/{accommodationId}")
    public SingleAcmdResponse singleAcmdResponse(@PathVariable Long accommodationId){
        return accommodationService.singleAcmdResponse(accommodationId);
    }
    @GetMapping("/api/accommodation/search")
    public SearchResponse searchResponse(Pageable pageable, @ModelAttribute SearchRequest request){
        return accommodationService.search(pageable, request);
    }

}
