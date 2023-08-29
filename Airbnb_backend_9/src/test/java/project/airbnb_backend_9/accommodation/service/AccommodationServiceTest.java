package project.airbnb_backend_9.accommodation.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import project.airbnb_backend_9.accommodation.dto.request.AccommodationEditRequestDTO;
import project.airbnb_backend_9.accommodation.dto.request.AccommodationRequestDTO;
import project.airbnb_backend_9.domain.Users;
import project.airbnb_backend_9.repository.accommodation.AccommodationRepository;
import project.airbnb_backend_9.repository.image.ImageRepository;
import project.airbnb_backend_9.repository.user.UserRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@SpringBootTest
class AccommodationServiceTest {

    @Autowired
    AccommodationService accommodationService;
    @Autowired
    AccommodationRepository accommodationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageRepository imageRepository;

    @Test
    @DisplayName("숙소 등록")
    void saveAccommodation() throws IOException {

        String fileName = "example.jpg";
        String originalFileName = "example.jpg";
        String contentType = "image/jpeg";
        byte[] content = createMockImageContent(); // 가상의 이미지 파일 내용을 생성하는 메서드 호출
        InputStream inputStream = new ByteArrayInputStream(content);

        MultipartFile mockFile = new MockMultipartFile(fileName, originalFileName, contentType, inputStream);

        List<MultipartFile> images = new ArrayList<>();
        images.add(mockFile);

        Optional<Users> users = userRepository.findById(1L);

        AccommodationRequestDTO request = AccommodationRequestDTO.builder()
                .mainAddress("테스트")
                .detailAddress("테스트")
                .guest(3L)
                .bedroom(3L)
                .bed(3L)
                .bathroom(3L)
                .acmdName("테스트")
                .acmdDescription("테스트")
                .price(100000L)
                .build();

        accommodationService.saveAccommodation(request, images, users.get());


    }

    @Test
    @DisplayName("등록한 숙소 수정")
    void editAccommodation() throws IOException {

        Optional<Users> users = userRepository.findById(10L);

        AccommodationEditRequestDTO request = AccommodationEditRequestDTO.builder()
                .guest(1L)
                .bedroom(1L)
                .bed(1L)
                .bathroom(1L)
                .acmdName("테스트2")
                .acmdDescription("테스트2")
                .price(100000L)
                .build();

        List<MultipartFile> newImages = createMockImages(); // 가상의 이미지 파일 리스트 생성

        accommodationService.editAccommodation(50L, request, newImages, users.get());
    }

    //MockMultipartFile 모의 객체 생성
    private byte[] createMockImageContent() {
        // 가상의 이미지 파일 내용을 생성하여 반환하는 메서드
        // 실제 이미지 파일을 읽어서 byte 배열로 만들거나, 랜덤한 바이트 배열을 생성할 수 있음
        // 여기에서는 가상의 내용으로 1000 바이트의 랜덤 배열을 생성하는 예시를 보여줌
        byte[] content = new byte[1000];
        new Random().nextBytes(content);
        return content;
    }

    private MultipartFile createMockImage(String fileName, String contentType) throws IOException {
        byte[] content = createMockImageContent();
        InputStream inputStream = new ByteArrayInputStream(content);
        return new MockMultipartFile(fileName, fileName, contentType, inputStream);
    }

    private List<MultipartFile> createMockImages() throws IOException {
        List<MultipartFile> images = new ArrayList<>();

        // 가상의 이미지 파일 생성 및 MockMultipartFile 객체로 변환하여 리스트에 추가
        MultipartFile image1 = createMockImage("image1.jpg", "image/jpeg");
        MultipartFile image2 = createMockImage("image2.jpg", "image/jpeg");
        images.add(image1);
        images.add(image2);

        return images;
    }

    @Test
    @DisplayName("등록한 숙소 삭제")
    void deleteAccommodation() {

        Optional<Users> users = userRepository.findById(10L);
        accommodationService.deleteAccommodation(50L, users.get());
    }
}