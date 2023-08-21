package project.airbnb_backend_9.repository.accommodation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.airbnb_backend_9.user.dto.AccommodationDTO;

import java.util.List;

@SpringBootTest
class AccommodationRepositoryTest {
    @Autowired
    AccommodationRepository accommodationRepository;


    @Test
    @DisplayName("숙소 조회하기")
    public void  getAccommodations() throws Exception{
        List<AccommodationDTO> accommodations = accommodationRepository.getAccommodations(1L);

        for (AccommodationDTO accommodation : accommodations) {
            System.out.println(accommodation.toString());
        }

    }

}