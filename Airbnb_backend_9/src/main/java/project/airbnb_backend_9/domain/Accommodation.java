package project.airbnb_backend_9.domain;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@ToString
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
}
