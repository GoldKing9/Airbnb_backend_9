package project.airbnb_backend_9.domain;

import lombok.Builder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accommodationId;
    private String mainAddress;
    private String detailAddress;
    private Long guest;
    private Long bedroom;
    private Long bed;
    private Long bathroom;
    private String acmdName;
    private String acmdDescription;
    private Long price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accommodation")
    private List<Image> images = new ArrayList<>();
  

    @Builder
    public Accommodation(String mainAddress, String detailAddress, Long guest, Long bedroom, Long bed, Long bathroom, String acmdName, String acmdDescription, Long price, Users users) {
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.guest = guest;
        this.bedroom = bedroom;
        this.bed = bed;
        this.bathroom = bathroom;
        this.acmdName = acmdName;
        this.acmdDescription = acmdDescription;
        this.price = price;
        this.users = users;
    }

    // 엔티티 자체가 자신의 상태를 수정하는데 필요한 행동을 수행 -> 데이터와 관련된 로직이 객체 내에 캡슐화 -> 객체 지향적
    // 관련된 데이터와 로직이 하나의 단위로 묶임 -> 해당 메서드 내에서 필요한 데이터와 동작에 쉽게 접근하고 조작 -> 코드의 응집도 향상
    public void updateAccommodation(Long guest, Long bedroom, Long bed, Long bathroom, String acmdName, String acmdDescription, Long price, Users users) {
        this.guest = guest;
        this.bedroom = bedroom;
        this.bed = bed;
        this.bathroom = bathroom;
        this.acmdName = acmdName;
        this.acmdDescription = acmdDescription;
        this.price = price;
        this.users = users;
    }

    //질문: 숙소가 부모인 일대다 관계인데 여기에서 이미지를 관리하는 게 맞나?
    public void addImage(Image image) {
        this.images.add(image);
        image.setAccommodation(this); //이미지 엔티티에 대해 숙소 엔티티를 설정(양방향 연관관계)
    }

}
