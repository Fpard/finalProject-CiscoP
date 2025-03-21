package songs.catalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import songs.catalog.entity.Review;

public interface ReviewgDao extends JpaRepository<Review, Long> {

}
