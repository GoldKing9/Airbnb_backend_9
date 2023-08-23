package project.airbnb_backend_9.accommodation.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.airbnb_backend_9.accommodation.dto.request.AccommodationRequestDTO;
import project.airbnb_backend_9.domain.Accommodation;
import project.airbnb_backend_9.repository.image.ImageRepository;
import project.airbnb_backend_9.repository.accommodation.AccommodationRepository;
import project.airbnb_backend_9.accommodation.dto.request.SearchRequest;
import project.airbnb_backend_9.accommodation.dto.response.AccommodationDataDto;
import project.airbnb_backend_9.accommodation.dto.response.SearchResponse;
import project.airbnb_backend_9.accommodation.dto.response.SingleAcmdResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
// @Transactional(readOnly = true) 이렇게 전체 서비스 위에 붙여줘도 되나? 메소드 단위로 붙여야 하지 않나?
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public void saveAccommodation(AccommodationRequestDTO dto,
                                  List<MultipartFile> images) {
        Accommodation accommodation = Accommodation.builder()
                .mainAddress(dto.getMainAddress())
                .detailAddress(dto.getDetailAddress())
                .guest(dto.getGuest())
                .bedroom(dto.getBedroom())
                .bed(dto.getBed())
                .bathroom(dto.getBathroom())
                .acmdName(dto.getAcmdName())
                .acmdDescription(dto.getAcmdDescription())
                .price(dto.getPrice())
                .build();

        // 이미지 넣어주는 부분도 추가해야 함

        accommodationRepository.save(accommodation);

    }
  
    public SearchResponse search(Pageable pageable, SearchRequest request){
        PageImpl<AccommodationDataDto> accommodations = accommodationRepository.search(pageable, request);
        return new SearchResponse(accommodations);
    }
  
    public SingleAcmdResponse singleAcmdResponse(Long accommodationId){
        return accommodationRepository.findAccommodation(accommodationId);
    }

}
