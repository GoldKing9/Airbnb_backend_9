package project.airbnb_backend_9.accommodation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.airbnb_backend_9.accommodation.dto.request.AccommodationRequestDTO;
import project.airbnb_backend_9.accommodation.service.AccommodationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    // 숙박 등록
    @PostMapping("/api/auth/accommodation")
    public void registerAccommodation(@RequestPart AccommodationRequestDTO dto,
                                      @RequestPart List<MultipartFile> images) {
        accommodationService.saveAccommodation(dto, images);
    }

    // 숙박 수정
    @PutMapping("/api/auth/accommodation/{accommodationId}")
    public void editAccommodation() {
    }

    // 숙박 삭제
    @DeleteMapping("/api/auth/accommodation/{accommodationId}")
    public void deleteAccommodation() {
    }

}