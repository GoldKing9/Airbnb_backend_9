package project.airbnb_backend_9.image.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}") //S3 버킷의 이름을 가져와서 bucketName 필드에 주입
    private String bucketName;

    public String uploadFile(MultipartFile file) {
        //업로드 할 파일의 원본 파일 이름을 가져옴
        String fileName = file.getOriginalFilename();

        try { //Amazon S3에 파일을 업로드하는 메서드 puObject()
            //업로드 할 파일의 정보를 나타내는 PutObjectRequest 객체를 생성
            //업로드 할 버킷 이름, 파일 이름, 파일 내용 및 메타데이터 포함
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), new ObjectMetadata())
                    .withCannedAcl(CannedAccessControlList.PublicRead)); // 업로드된 파일이 공개 읽기 권한이 있는 객체로 설정되도록 함

            return fileName;
        } catch (IOException e) {
            //파일 업로드 중에 예외가 발생한 경우, 런타임 예외를 던져서 예외를 상위 호출자에게 전달
            //예외 메시지는 "Failed to upload file to S3"로 설정되고, 원본 예외인 IOException도 함께 전달
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

//    public String editFile(String fileKey, MultipartFile newFile) {
//        // 먼저 기존 파일 삭제
//        deleteFile(fileKey);
//        // 새로운 파일 업로드
//        return uploadFile(newFile);
//    }

    public void deleteFile(String fileKey) {
        try {
            amazonS3.deleteObject(bucketName, fileKey);
        } catch (AmazonServiceException e) {
            throw new RuntimeException("Failed to delete file from S3", e);
        }
    }
}

