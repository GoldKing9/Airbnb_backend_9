package project.airbnb_backend_9.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
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
}
