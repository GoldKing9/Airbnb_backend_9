package project.airbnb_backend_9.accommodation.service;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;
import project.airbnb_backend_9.accommodation.dto.request.AccommodationEditRequestDTO;
import project.airbnb_backend_9.accommodation.dto.request.AccommodationRequestDTO;
import project.airbnb_backend_9.domain.Accommodation;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.repository.image.ImageRepository;
import project.airbnb_backend_9.repository.accommodation.AccommodationRepository;
import project.airbnb_backend_9.accommodation.dto.request.SearchRequest;
import project.airbnb_backend_9.accommodation.dto.response.AccommodationDataDto;
import project.airbnb_backend_9.accommodation.dto.response.SearchResponse;
import project.airbnb_backend_9.accommodation.dto.response.SingleAcmdResponse;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
// @Transactional(readOnly = true) 이렇게 전체 서비스 위에 붙여줘도 되나? 메소드 단위로 붙여야 하지 않나?
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final ImageRepository imageRepository;

    //숙박 등록
    @Transactional
    public void saveAccommodation(AccommodationRequestDTO request,
                                  List<MultipartFile> images,
                                  Users users) {
        Accommodation accommodation = Accommodation.builder()
                .mainAddress(request.getMainAddress())
                .detailAddress(request.getDetailAddress())
                .guest(request.getGuest())
                .bedroom(request.getBedroom())
                .bed(request.getBed())
                .bathroom(request.getBathroom())
                .acmdName(request.getAcmdName())
                .acmdDescription(request.getAcmdDescription())
                .price(request.getPrice())
                .users(users)
                .build();

        //이미지 등록 부분 추가해야 함

        accommodationRepository.save(accommodation);
    }

    //숙박 수정
    @Transactional
    public void editAccommodation(Long accommodationId, AccommodationEditRequestDTO request, List<MultipartFile> newImages, Users users) {
        //숙소 정보 조회, 없으면 예외 처리
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 숙소가 없습니다. ID : " + accommodationId));

        //숙소 소유자와 현재 사용자가 일치하는지 확인 (편집 권한 검사)
        if (!accommodation.getUsers().getUserId().equals(users.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이 숙소를 편집할 권한이 없습니다."); // 예외 처리 이렇게 해도 되나?
        }

        //숙소 정보 업데이트
        accommodation.updateAccommodation(
                request.getGuest(),
                request.getBedroom(),
                request.getBed(),
                request.getBathroom(),
                request.getAcmdName(),
                request.getAcmdDescription(),
                request.getPrice(),
                users
        );

        //이미지 업데이트 부분 추가해야 함

        //DB에 엔티티 저장
        accommodationRepository.save(accommodation);
    }

    //숙박 삭제
    @Transactional
    public void deleteAccommodation(Long accommodationId, Users users) {
        //숙소 정보 조회, 없으면 예외 처리
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 숙소가 없습니다. ID : " + accommodationId));

        //숙소 소유자와 현재 사용자가 일치하는지 확인 (삭제 권한 검사)
        if (!accommodation.getUsers().getUserId().equals(users.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이 숙소를 삭제할 권한이 없습니다.");
        }

        //이미지 삭제 부분 추가해야 함

        //DB에 엔티티 저장
        accommodationRepository.delete(accommodation);
    }

  
    public SearchResponse search(Pageable pageable, SearchRequest request){
        PageImpl<AccommodationDataDto> accommodations = accommodationRepository.search(pageable, request);
        return new SearchResponse(accommodations);
    }
  
    public SingleAcmdResponse singleAcmdResponse(Long accommodationId){
        return accommodationRepository.findAccommodation(accommodationId);
    }

}
