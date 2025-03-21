package songs.catalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import songs.catalog.entity.Song;

public interface SongDao extends JpaRepository<Song, Long> {

}
