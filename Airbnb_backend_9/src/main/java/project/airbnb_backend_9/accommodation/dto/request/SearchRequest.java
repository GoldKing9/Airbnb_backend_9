package project.airbnb_backend_9.accommodation.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Getter
@Setter
public class SearchRequest {
    private String mainAddress;
    //2023-07-04 00:00:00
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkout;
    private Long minPrice;
    private Long maxPrice;
    private Long guest;
    private Long bed;
    private Long bedroom;
    private Long bathroom;
    public SearchRequest(
            String mainAddress,
            LocalDate checkIn,
            LocalDate checkout,
            Long minPrice,
            Long maxPrice,
            Long guest,
            Long bathroom,
            Long bedroom,
            Long bed
    ) {
        this.mainAddress = mainAddress;
        this.checkIn = checkIn;
        this.checkout = checkout;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.guest = guest;
        this.bathroom = bathroom;
        this.bedroom = bedroom;
        this.bed = bed;
    }
    }
