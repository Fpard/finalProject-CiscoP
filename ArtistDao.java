package songs.catalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import songs.catalog.entity.Artist;

public interface ArtistDao extends JpaRepository<Artist, Long> {

}
