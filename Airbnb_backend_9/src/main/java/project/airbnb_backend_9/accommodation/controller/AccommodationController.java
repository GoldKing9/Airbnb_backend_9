package project.airbnb_backend_9.accommodation.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.airbnb_backend_9.accommodation.dto.request.AccommodationEditRequestDTO;
import project.airbnb_backend_9.accommodation.dto.request.AccommodationRequestDTO;
import project.airbnb_backend_9.accommodation.service.AccommodationService;

import java.util.List;

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
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;


@RestController
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    // 숙박 등록
    @PostMapping("/api/auth/accommodation")
    public void registerAccommodation(@RequestPart AccommodationRequestDTO request,
                                      @RequestPart List<MultipartFile> images,
                                      @AuthenticationPrincipal PrincipalDetails principalDetails) {
        accommodationService.saveAccommodation(request, images, principalDetails.getUsers());
    }

    // 숙박 수정
    @PutMapping("/api/auth/accommodation/{accommodationId}")
    public void editAccommodation(@PathVariable Long accommodationId,
                                  @RequestPart AccommodationEditRequestDTO request,
                                  @RequestPart List<MultipartFile> images,
                                  @AuthenticationPrincipal PrincipalDetails principalDetails) {
        accommodationService.editAccommodation(accommodationId, request, images, principalDetails.getUsers());
    }

    // 숙박 삭제
    @DeleteMapping("/api/auth/accommodation/{accommodationId}")
    public void deleteAccommodation(@PathVariable Long accommodationId,
                                    @AuthenticationPrincipal PrincipalDetails principalDetails) {
        accommodationService.deleteAccommodation(accommodationId, principalDetails.getUsers());
    }


    @GetMapping("/api/accommodation/{accommodationId}")
    public SingleAcmdResponse singleAcmdResponse(@PathVariable Long accommodationId){
        return accommodationService.singleAcmdResponse(accommodationId);
    }
    @GetMapping("/api/accommodation/search")
    public SearchResponse searchResponse(Pageable pageable, @ModelAttribute SearchRequest request){
        return accommodationService.search(pageable, request);
    }

}

