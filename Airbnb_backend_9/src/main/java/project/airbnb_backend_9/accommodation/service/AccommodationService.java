package project.airbnb_backend_9.accommodation.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
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
import project.airbnb_backend_9.domain.Image;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.exception.GlobalException;
import project.airbnb_backend_9.image.dto.request.EditImageRequestDTO;
import project.airbnb_backend_9.image.service.S3Service;
import project.airbnb_backend_9.repository.image.ImageRepository;
import project.airbnb_backend_9.repository.accommodation.AccommodationRepository;
import project.airbnb_backend_9.accommodation.dto.request.SearchRequest;
import project.airbnb_backend_9.accommodation.dto.response.AccommodationDataDto;
import project.airbnb_backend_9.accommodation.dto.response.SearchResponse;
import project.airbnb_backend_9.accommodation.dto.response.SingleAcmdResponse;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
// @Transactional(readOnly = true) 이렇게 전체 서비스 위에 붙여줘도 되나? 메소드 단위로 붙여야 하지 않나?
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final ImageRepository imageRepository;
    private final S3Service s3Service;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

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

        //이미지 등록
        for (MultipartFile imageFile : images) {
            //이미지 파일을 Amazon S3에 업로드하면서 이미지의 고유한 키를 생성 -> 버킷에서 이미지 식별
            String imageKey = s3Service.uploadFile(imageFile);
            //업로드 된 이미지의 공용 URL을 생성(업로드된 이미지에 접근할 수 있는 주소)
            String acmdImageUrl = amazonS3.getUrl(bucketName, imageKey).toExternalForm(); //toExternalForm(): 생성한 URL 객체를 문자열로 변환. 생성된 이미지 파일의 접근 가능한 주소를 문자열 형태로 얻을 수 있음
            //생성된 이미지의 URL과 키를 사용하여 Image 엔티티 객체를 생성
            Image image = new Image(imageKey, acmdImageUrl);
            //Image 엔티티 객체를 accommodation 엔티티에 추가 -> 숙소 정보에 해당 이미지 연결
            accommodation.addImage(image); //cascade가 없으면 이미지 저장 안 됨(ImageRepository에 저장이 안 됐기 때문)
        }

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
            throw new GlobalException("이 숙소를 편집할 권한이 없습니다.");
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


        //S3 버킷에서 기존 이미지 파일 삭제
        for (Image oldImage : accommodation.getImages()) {
            s3Service.deleteFile(oldImage.getImageKey());
        }
        //데이터베이스에서 기존 이미지 파일 삭제
        imageRepository.deleteAllInBatch(accommodation.getImages());

        //이미지 업데이트
        //받아온 새 이미지들을 우선 리스트에 저장
        List<Image> updateImage = new ArrayList<>();
        if (newImages != null && !newImages.isEmpty()) {
            for (MultipartFile newImageFile : newImages) {
                String newImageKey = s3Service.uploadFile(newImageFile);
                String newImageUrl = amazonS3.getUrl(bucketName, newImageKey).toExternalForm();
                Image newImage = new Image(newImageKey, newImageUrl);
                updateImage.add(newImage);
            }
        }

        //새로운 Image 엔티티 객체를 accommodation 엔티티에 추가
        for (Image newImage : updateImage) {
            accommodation.addImage(newImage);
        }

        //DB에 엔티티 저장 (이 부분 없어도 됨)
//        accommodationRepository.save(accommodation);
    }

//    //숙박 수정2 (1. 컨트롤러랑 테스트에 List<MultipartFile> newImages 이 부분 수정 2. 프론트에서 delete에 false, true 할당하는 부분 생성 필요)
//    @Transactional
//    public void editAccommodation(Long accommodationId, AccommodationEditRequestDTO request, List<EditImageRequestDTO> editImages, Users users) {
//        //숙소 정보 조회, 없으면 예외 처리
//        Accommodation accommodation = accommodationRepository.findById(accommodationId)
//                .orElseThrow(() -> new EntityNotFoundException("해당하는 숙소가 없습니다. ID : " + accommodationId));
//
//        //숙소 소유자와 현재 사용자가 일치하는지 확인 (편집 권한 검사)
//        if (!accommodation.getUsers().getUserId().equals(users.getUserId())) {
//            throw new GlobalException("이 숙소를 편집할 권한이 없습니다.");
//        }
//
//        // 원할 때만 이미지 업데이트
//        for (EditImageRequestDTO editImageRequest : editImages) {
//            Long imageId = editImageRequest.getImageId();
//            Image image = imageRepository.findById(imageId)
//                    .orElseThrow(() -> new EntityNotFoundException("해당하는 이미지가 없습니다. ID : " + imageId));
//
//            if (editImageRequest.isDelete()) {
//                // 이미지 삭제 요청이 있을 경우
//                s3Service.deleteFile(image.getImageKey());
//                imageRepository.delete(image);
//            } else if (editImageRequest.getNewImageFile() != null) {
//                // 이미지 업데이트 요청이 있을 경우
//                s3Service.deleteFile(image.getImageKey());
//
//                MultipartFile newImageFile = editImageRequest.getNewImageFile();
//                String newImageKey = s3Service.uploadFile(newImageFile);
//                String newImageUrl = amazonS3.getUrl(bucketName, newImageKey).toExternalForm();
//
//                image.setImageKey(newImageKey);
//                image.setAcmdImageUrl(newImageUrl);
//                imageRepository.save(image);
//            }
//        }
//
//        //DB에 엔티티 저장
//        accommodationRepository.save(accommodation);
//    }


    //숙박 삭제
    @Transactional
    public void deleteAccommodation(Long accommodationId, Users users) {
        //숙소 정보 조회, 없으면 예외 처리
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 숙소가 없습니다. ID : " + accommodationId));

        //숙소 소유자와 현재 사용자가 일치하는지 확인 (삭제 권한 검사)
        if (!accommodation.getUsers().getUserId().equals(users.getUserId())) {
            throw new GlobalException("이 숙소를 삭제할 권한이 없습니다.");
        }

        //이미지 삭제
        for (Image image : accommodation.getImages()) {
            s3Service.deleteFile(image.getImageKey());
        }

        //DB에서 엔티티 삭제
        //Accommodation 엔티티 내의 Image 엔티티에 cascade 설정함 -> Accommodation 엔티티에 대한 변경 작업이 Image 엔티티에도 적용됨
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
