package project.airbnb_backend_9.reservation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class ReservationRequestDTO {
// requestBody으로 json을 바인딩 받을 때  @JsonFormat 사용하기
//    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;
//    @JsonFormat(pattern = "yyyy-MM-dd") // @JsonFormat은 스프링부트에서 json 파싱에 사용되는 기본 라이브러리인 Jackson에 포함
    @DateTimeFormat(pattern = "yyyy-MM-dd") //Jackson에서 json파싱시 @DateTimeFormat을 바라보지 않는다.(Jackson 라이브러리 내에서 파싱하므로 스프링의 @DateTimeFormat을 알지 못한다.)
    private LocalDate checkOut;
    private Long totalPrice;
    private Long guest;

    @Builder
    public ReservationRequestDTO(LocalDate checkIn, LocalDate checkOut, Long totalPrice, Long guest) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
        this.guest = guest;
    }
}
