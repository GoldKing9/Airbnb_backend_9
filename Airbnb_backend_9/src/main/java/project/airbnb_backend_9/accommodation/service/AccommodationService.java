package project.airbnb_backend_9.accommodation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.airbnb_backend_9.accommodation.dto.request.AccommodationRequestDTO;
import project.airbnb_backend_9.domain.Accommodation;
import project.airbnb_backend_9.repository.image.ImageRepository;
import project.airbnb_backend_9.repository.accommodation.AccommodationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

}
