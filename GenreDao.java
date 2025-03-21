package songs.catalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import songs.catalog.entity.Genre;

public interface GenreDao extends JpaRepository<Genre, Long> {

}
