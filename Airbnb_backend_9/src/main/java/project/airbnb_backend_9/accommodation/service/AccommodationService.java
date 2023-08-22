package project.airbnb_backend_9.accommodation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.airbnb_backend_9.accommodation.dto.request.AccommodationRequestDTO;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.repository.ImageRepository;
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



    }

}
