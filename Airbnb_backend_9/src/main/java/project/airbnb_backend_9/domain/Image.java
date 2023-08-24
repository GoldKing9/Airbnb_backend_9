package project.airbnb_backend_9.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId; //이미지를 데이터베이스에서 식별
    private String imageKey; //이미지를 Amazon S3 버킷 내에서 식별 (이미지 파일의 저장 경로나 파일 이름을 나타냄. 이미지의 주소나 경로 역할)
    private String acmdImageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodationId")
    private Accommodation accommodation;

    public Image(String imageKey, String acmdImageUrl) {
        this.imageKey = imageKey;
        this.acmdImageUrl = acmdImageUrl;
    }
}
