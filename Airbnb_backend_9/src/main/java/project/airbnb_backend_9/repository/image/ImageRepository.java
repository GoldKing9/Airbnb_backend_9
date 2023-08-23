package project.airbnb_backend_9.repository.image;

import org.springframework.data.jpa.repository.JpaRepository;
import project.airbnb_backend_9.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
