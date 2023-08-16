package project.airbnb_backend_9.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private String acmdImageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodationId")
    private Accommodation accommodation;
}
