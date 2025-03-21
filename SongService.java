package songs.catalog.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import songs.catalog.controller.SongController;
import songs.catalog.controller.model.SongData;
import songs.catalog.controller.model.SongData.SongArtist;
import songs.catalog.controller.model.SongData.SongGenre;
import songs.catalog.controller.model.SongData.SongReview;
import songs.catalog.dao.ArtistDao;
import songs.catalog.dao.GenreDao;
import songs.catalog.dao.ReviewgDao;
import songs.catalog.dao.SongDao;
import songs.catalog.entity.Artist;
import songs.catalog.entity.Genre;
import songs.catalog.entity.Review;
import songs.catalog.entity.Song;

@Slf4j
@Service
public class SongService {
	
	@Autowired
	private SongDao songDao;
	@Autowired
	private ArtistDao artistDao;
	@Autowired
	private GenreDao genreDao;
	@Autowired 
	private ReviewgDao reviewDao;
	
	@Transactional(readOnly = false)
	public SongData saveSong(SongData songData) {
		Long songId = songData.getSongId();
		Song song = findOrCreateSong(songId);
		copySongFields(song, songData);
		
		return  new SongData(songDao.save(song));
	}

	private Song findOrCreateSong(Long songId) {
		Song song;
		
		if(Objects.isNull(songId)) {
			song = new Song();
		}
		else {
			song = findSongById(songId);
			
		}
				
		return song;
	}
	

	private Song findSongById(Long songId) {
		return songDao.findById(songId).orElseThrow(() -> new NoSuchElementException(
				
				"Song with ID=" + songId + " was not found"));
	}


	private void copySongFields(Song song, SongData songData) {
		song.setSongTitle(songData.getSongTitle());
		song.setSongId(songData.getSongId());
		song.setAlbumName(songData.getAlbumName());
		
	}
	
	@Transactional(readOnly = true)	
	public SongData getSongById(Long songId) {
		
		return new SongData(findSongById(songId));
	}
	
	@Transactional(readOnly = true)	
	public List<SongData> retrieveAllSongs() {
		List<Song> songs = songDao.findAll();
		List<SongData> result = new LinkedList<>();
		
		for (Song song: songs) {
			SongData sd = new SongData(song);
			
			sd.getArtists().clear();
			
			sd.getGenres().clear();
			
			sd.getReviews().clear();
			
			result.add(sd);
		}
		
		return result;
		
	}

	
	public void deleteSongById(Long songId) {
		Song song = findSongById(songId);
		songDao.delete(song);
		
	}
	
	@Transactional(readOnly = true)	
	public List<SongData> findSongByGenreId( Long genreId) {
		
		Genre genre = genreDao.findById(genreId).orElseThrow(() -> new NoSuchElementException(
				
				"Genre with ID=" + genreId + " was not found"));
		
		List<Song> songs = songDao.findAll();
		List<SongData> result = new LinkedList<>();
		
		for (Song song: songs) {
			SongData sd = new SongData(song);
			
			sd.getArtists().clear();
			
			//sd.getGenres().clear();
			
			sd.getReviews().clear();
			
			if (sd.getGenres().contains(new SongGenre(genre))) {
				
				result.add(sd);
			}
		}
       
     
       return result;
		
	}

	
	@Transactional(readOnly = true)	
	public List<SongData> findSongByGenreName( String genreName) {
						
		List<Genre> genres = genreDao.findAll();
		Set<String> genreNames = new HashSet<>();
		
		String genreNameUpper = genreName.toUpperCase().trim();
		genreNameUpper = genreNameUpper.replace(" ", "");
		log.info("Looking for genre named: " + genreNameUpper);
		
		for(Genre genre: genres) {
			
			String genreNameStored = genre.getGenreName().toUpperCase().trim();
			genreNameStored = genreNameStored.replace(" ", "");
			genreNames.add(genreNameStored);
			
			log.info("adding genre named: " + genreNameStored);
		}
		
		List<Song> songs = songDao.findAll();
		List<SongData> result = new LinkedList<>();
		
		for (Song song: songs) {
			SongData sd = new SongData(song);
			
			sd.getArtists().clear();
			
			//sd.getGenres().clear();
			
			sd.getReviews().clear();
			
			    
			if (findSubstringInSet(genreNames, genreNameUpper)){
			
				
				Iterator<SongGenre> value = sd.getGenres().iterator();
				
				while (value.hasNext()) {
					
					String aGenre = value.next().getGenreName().toUpperCase();
					aGenre = aGenre.replace(" ", "");
				    if (aGenre.contains(genreNameUpper) || aGenre.equalsIgnoreCase(genreNameUpper)) {
					  
					  log.info("Found : " + genreName);
					  	result.add(sd);
				  } else {
					  
					       log.info("Not Found : " + genreName);
				  }
				}
			 } else {
				  
			       log.info("In else # 2: Not Found : " + genreName);
		  }
		}
    
       return result;
		
	}
	
	public boolean findSubstringInSet(Set<String> aSet, String theString) {
		boolean found = false;
		
		for (String st: aSet) {
			if(st.contains(theString)) {
				found = true;
			}
			
		}
		
		
		return found;
	}
	
	
	@Transactional(readOnly = true)	
	public List<SongData> findSongByArtistName( String artistName) {
						
		List<Artist> artists = artistDao.findAll();
		Set<String> artistNames = new HashSet<>();
		
		String artistNameUpper = artistName.toUpperCase().trim();
		artistNameUpper = artistNameUpper.replace(" ", "");
		log.info("Looking for artist named: " + artistNameUpper);
		
		for(Artist artist: artists) {
			
			String artistNameStored = artist.getArtistName().toUpperCase().trim();
			artistNameStored = artistNameStored.replace(" ", "");
			artistNames.add(artistNameStored);
			
			log.info("adding artist named: " + artistNameStored);
		}
		
		List<Song> songs = songDao.findAll();
		List<SongData> result = new LinkedList<>();
		
		for (Song song: songs) {
			SongData sd = new SongData(song);
			
			//sd.getArtists().clear();
			
			sd.getGenres().clear();
			
			sd.getReviews().clear();
			
			    
			if (findSubstringInSet(artistNames, artistNameUpper)){
			
				
				Iterator<SongArtist> value = sd.getArtists().iterator();
				
				while (value.hasNext()) {
					
					String anArtist = value.next().getArtistName().toUpperCase().trim();
					anArtist = anArtist.replace(" ", "");
					
				    if ((anArtist.contains(artistNameUpper)) || (anArtist.equalsIgnoreCase(artistNameUpper))) {
					  
					  log.info("Found : " + artistName);
					  	result.add(sd);
				  } else {
					  
					       log.info("Not Found : " + artistName);
				  }
				}
			 } else {
				  
			       log.info("In else # 2: Not Found : " + artistName);
		  }
		}
    
       return result;
		
	}
	
	
//**************************************Artists*******************************************************

	@Transactional(readOnly = false)
	public SongArtist saveArtist(Long songId, SongArtist songArtist) {
				Long artistId = songArtist.getArtistId();
				Song song = findSongById(songId);
				Artist artist = findOrCreateArtist(artistId, songId);
				copyArtistFields(artist, songArtist);
				artist.getSongs().add(song);
				song.getArtists().add(artist);
				
				
				return  new SongArtist(artistDao.save(artist));
			}




	private void copyArtistFields(Artist artist, SongArtist songArtist) {
		artist.setArtistId(songArtist.getArtistId());
		artist.setArtistName(songArtist.getArtistName());
				
	}


	private Artist findOrCreateArtist(Long artistId, Long songId) {
		Artist artist;
		
		if(Objects.isNull(artistId)) {
			artist = new Artist();
		}
		else {
			artist = findArtistById(songId, artistId);
			
		}
			
				
		return artist;
		
	}

	private Artist findArtistById(Long songId, Long artistId) {
		Artist artist = artistDao.findById(artistId).orElseThrow(() -> new NoSuchElementException(
					
					"Store artist with ID=" + artistId + " was not found"));
	       
		   Set <Song> songs = artist.getSongs();
		   
		   boolean songFound = false;
		   
		   for (Song song: songs) {
			    if (song.getSongId() == songId) {
			    	songFound = true;
			    	break;
			    }
			}
			   
		   if (!songFound){
	       		throw new IllegalArgumentException(
	  				
	  				"Song with song ID=" + songId + " was not found");
	       		}
		return artist;
	}

	@Transactional(readOnly = false)
	public SongArtist updateArtist(Long songId, Long artistId, SongArtist songArtist) {
	
		Song song = findSongById(songId);
	
		Artist artist  = findOrCreateArtist(artistId, songId);
	
		copyUpdateArtistFields(artist, songArtist);
		artist.getSongs().add(song);
		song.getArtists().add(artist);
	
		log.info("Finish updating artist to song");
			
		return  new SongArtist(artistDao.save(artist));
		
		
	}
	
	
	private void copyUpdateArtistFields(Artist artist, SongArtist songArtist) {
		log.info("Copying artist fields");
		
		artist.setArtistName(songArtist.getArtistName());
		
		
		log.info("Finish Copying genre fields");
	
}

	
	//************************************************Genre*******************************************

	@Transactional(readOnly = false)
	public SongGenre saveGenre(Long songId, SongGenre songGenre) {
		
		String genreName = songGenre.getGenreName().toUpperCase().trim();
		genreName = genreName.replace(" ", "");
		List<Genre> genres = genreDao.findAll();
		 		
		Long genreId = songGenre.getGenreId();
		Song song = findSongById(songId);
		Genre genre  = findOrCreateGenre(genreId, songId);
		copyGenreFields(genre, songGenre);
		genre.setSong(song);
		song.getGenres().add(genre);
				
		return  new SongGenre(genreDao.save(genre));
		
	}


	private void copyGenreFields(Genre genre, SongGenre songGenre) {
		genre.setGenreId(songGenre.getGenreId());
		genre.setGenreName(songGenre.getGenreName());
		
		
	}


	private Genre findOrCreateGenre(Long genreId, Long songId) {
		Genre genre;
		
		if(Objects.isNull(genreId)) {
			genre = new Genre();
		}
		else {
			genre = findGenreById(songId, genreId);
			
		}
		return genre;
	}


	private Genre findGenreById(Long songId, Long genreId) {
		Genre genre = genreDao.findById(genreId).orElseThrow(() -> new NoSuchElementException(
				
				"Genre with ID=" + genreId + " was not found"));
       
       if (genre.getSong().getSongId() == genreId) {
    	   
    	   return genre;
       }else
       		{genreDao.findById(genreId).orElseThrow(() -> new IllegalArgumentException(
  				
  				"Song with ID=" + songId + " was not found"));
       		}
       return genre;
		
	}
	
	@Transactional(readOnly = false)
	public SongGenre updateGenre(Long songId, Long genreId, SongGenre songGenre) {
	
		Song song = findSongById(songId);
	
		Genre genre  = findOrCreateGenre(genreId, songId);
	
		copyUpdateGenreFields(genre, songGenre);
		genre.setSong(song);
		song.getGenres().add(genre);
	
		log.info("Finish adding reviews to song");
			
		return  new SongGenre(genreDao.save(genre));
		
		
	}
	
	
	private void copyUpdateGenreFields(Genre genre, SongGenre songGenre) {
		log.info("Copying genre fields");
		
		genre.setGenreName(songGenre.getGenreName());
		
		
		log.info("Finish Copying genre fields");
	
}




	//***********************************************Reviews**********************************************************
	
	@Transactional(readOnly = false)
	public SongReview saveReview(Long songId, SongReview songReview) {
		Long reviewId = songReview.getReviewId();
		Song song = findSongById(songId);
		Review review  = findOrCreateReview(reviewId, songId);
		copyReviewFields(review, songReview);
		review.setSong(song);
		song.getReviews().add(review);
				
		return  new SongReview(reviewDao.save(review));
		
	}

	@Transactional(readOnly = false)
	public SongReview updateReview(Long songId, Long reviewId, SongReview songReview) {
		Song song = findSongById(songId);
		
		Review review  = findOrCreateReview(reviewId, songId);
		
		copyUpdateReviewFields(review, songReview);
		review.setSong(song);
		song.getReviews().add(review);
		
		log.info("Finish adding reviews to song");
				
		return  new SongReview(reviewDao.save(review));
		
	}
	private void copyReviewFields(Review review, SongReview songReview) {
		log.info("Copying review fields");
		review.setReviewId(songReview.getReviewId());
		review.setReviewName(songReview.getReviewName());
		review.setReviewContent(songReview.getReviewContent());
		
		log.info("Finish Copying review fields");
			
	}
	
	private void copyUpdateReviewFields(Review review, SongReview songReview) {
		log.info("Copying review fields");
		
		review.setReviewName(songReview.getReviewName());
		review.setReviewContent(songReview.getReviewContent());
		
		log.info("Finish Copying review fields");
			
	}


	private Review findOrCreateReview(Long reviewId, Long songId) {
		Review review;
		
		log.info("Review ID is " + reviewId + " And Song Id is "+ songId);
		
		if(Objects.isNull(reviewId)) {
			review = new Review();
			log.info("Creating new review");
		}
		else {
			review = findReviewById(songId, reviewId);
			
			log.info("Found old review");
			
		}
		return review;
	}


	private Review findReviewById(Long songId, Long reviewId) {
		Review review = reviewDao.findById(reviewId).orElseThrow(() -> new NoSuchElementException(
				
				"Review with ID=" + reviewId + " was not found"));
       
       if (review.getSong().getSongId() == reviewId) {
    	   
    	   return review;
       }else
       		{reviewDao.findById(reviewId).orElseThrow(() -> new IllegalArgumentException(
  				
  				"Song with ID=" + songId + " was not found"));
       		}
       return review;
		
	}



	@Transactional(readOnly = true)	
	public List<Genre> retrieveAllGenres() {
		List<Genre> genres = genreDao.findAll();
		
		return genres;
	}



	@Transactional(readOnly = true)	
	public List<Review> retrieveAllReviews() {
		List<Review> reviews = reviewDao.findAll();
		
		return reviews;
	}

		
		
}
    
	

