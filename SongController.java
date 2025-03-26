package songs.catalog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import songs.catalog.controller.model.SongData;
import songs.catalog.controller.model.SongData.SongArtist;
import songs.catalog.controller.model.SongData.SongGenre;
import songs.catalog.controller.model.SongData.SongReview;
import songs.catalog.entity.Artist;
import songs.catalog.entity.Genre;
import songs.catalog.entity.Review;
import songs.catalog.service.SongService;

@RestController
@RequestMapping("/song_catalog")
@Slf4j
public class SongController {
	
	@Autowired
	private SongService songService;
	

	//*****************************************//////////////POST MAPPING METHODS ***********************************************************
	/**
	 * 
	 * @param songData the song information to be written to the database.
	 * @return	a JSON Responses in a SongData object. It is the result of the Post request.
	 */
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public SongData insertSong(@RequestBody SongData songData) {
	   log.info("Creating song {}", songData);
		   
	   return songService.saveSong(songData);
		   
	}
	
	/*
	 * @param songId  the id of the song to which the artist information is to be added. The artist information is 
	 * in the body of the request.
	 * @return a JSON Response in a SongArtist object.
	 * 
	 */
	@PostMapping("/{songId}/artist")
	 @ResponseStatus(code = HttpStatus.CREATED)
	 public SongArtist addArtist(@PathVariable Long songId,
			 @RequestBody SongArtist songArtist) {
		 log.info(" Adding an artist {} to a song with ID={}", songArtist, songId);
		 
		 return songService.saveArtist(songId, songArtist);
		 
	 }
	
	
	/*
	 * @param songId  the id of the song to which the genre information is to be added. The genrest information is 
	 * in the body of the request.
	 * @return a JSON Response in a SongGenre object.
	 * 
	 */
	@PostMapping("/{songId}/genre")
	 @ResponseStatus(code = HttpStatus.CREATED)
	 public SongGenre addGenre(@PathVariable Long songId,
			 @RequestBody SongGenre songGenre) {
		 log.info(" Adding a genre {} to a song with ID={}", songGenre, songId);
		 
		 return songService.saveGenre(songId, songGenre);
		 
	 }
	
	/*
	 * @param songId  the id of the song to which the review information is to be added. The reviewt information is 
	 * in the body of the request.
	 * @return a JSON Response in a SongReview object.
	 * 
	 */
	
	@PostMapping("/{songId}/review")
	 @ResponseStatus(code = HttpStatus.CREATED)
	 public SongReview addReview(@PathVariable Long songId,
			 @RequestBody SongReview songReview) {
		 log.info(" Adding a review {} to a song with ID={}", songReview, songId);
		 
		 return songService.saveReview(songId, songReview);
		 
	 }
	
	//****************************************PUT MAPPING METHODS**********************************************
	/**
	 * 
	 * @param songId the id of the song information to be modified in the database. The modified 
	 * information is in the request body.
	 * @return	a JSON Responses in a SongData object. It is the result of the Put request.
	 */
	
	
	@PutMapping("/{songId}")
	@ResponseStatus (code = HttpStatus.ACCEPTED)
	public SongData updateSong(@PathVariable Long songId,
		@RequestBody SongData songData) {
			songData.setSongId(songId);
	    log.info("Updating song {}", songData);
	    return songService.saveSong(songData);
		   
	}
	
	/**
	 * 
	 * @param songId the id of the song whose genre information to be modified in the database. The modified 
	 * information is in the request body.
	 * @param genreId the id of the genre to be modified.
	 * @return	a JSON Responses in a SongGenre object. It is the result of the Put request.
	 */
	@PutMapping("/{songId}/genre/genreId")
	@ResponseStatus (code = HttpStatus.ACCEPTED)
	public SongGenre updateSongGenre(@PathVariable Long songId, @PathVariable Long genreId,
		@RequestBody SongGenre songGenre) {
		 log.info("Updating song Id{}", songId);
		 log.info("Genre ID is " + genreId + "And Song Id is "+ songId);
		 
		 return songService.updateGenre(songId, genreId, songGenre);
			   
	}
					
	/**
	 * 
	 * @param reviewId the id of the song whose review information to be modified in the database. The modified 
	 * information is in the request body.
	 * @param revieweId the id of the review to be modified.
	 * @return	a JSON Responses in a SongReview object. It is the result of the Put request.
	 */		
					
	@PutMapping("/{songId}/review/{reviewId}")
	@ResponseStatus (code = HttpStatus.ACCEPTED)
	public SongReview updateSongReview(@PathVariable Long songId, @PathVariable Long reviewId,
	@RequestBody SongReview songReview) {
	  log.info("Updating review for song Id{}", songId);
	  log.info("Review ID is " + reviewId + "And Song Id is "+ songId);
	
	  return songService.updateReview(songId, reviewId, songReview);
					   
	}
	
	/**
	 * 
	 * @param artistId the id of the song whose artist information to be modified in the database. The modified 
	 * information is in the request body.
	 * @param artistId the id of the artist to be modified.
	 * @return	a JSON Responses in a SongArtist object. It is the result of the Put request.
	 */	
	
	@PutMapping("/{songId}/artist/{artistId}")
	@ResponseStatus (code = HttpStatus.ACCEPTED)
	public SongArtist updateSongArtist(@PathVariable Long songId, @PathVariable Long artistId,
	@RequestBody SongArtist songArtist) {
	  log.info("Updating Artist for song Id{}", songId);
	  log.info("Artist ID is " + artistId + "And Song Id is "+ songId);
	
	  return songService.updateArtist(songId, artistId, songArtist);
					   
	}
				
	
	//****************************************GET MAPPING METHODS**********************************************
	/*
	 * @param songId  the id of the song to be retrieved from the database.
	 * 
	 * @return a JSON Response with complete information about the song to include artists, genres and reviews
	 *  in a SongData object
	 * 
	 */
	@GetMapping("/{songId}")
	public SongData getSongById(@PathVariable Long songId) {
		
		log.info(" Retrieving song with id = {} ", songId);
		return songService.getSongById(songId) ;
	}
	
	
	/*
	 * @return a JSON Response with simple information about the song with artists, genres and reviews
	 * information cleared from the SongData object
	 * 
	 */
	
	@GetMapping
	public List<SongData> listAllSongs(){
		
		log.info("Retrieving all songs");
		return songService.retrieveAllSongs();
	}
	
	/*
	 * @param artistName  the name of the artist that performs songs to be retrieved from the database.
	 * 
	 * @return a JSON Response with all songs performed by the artist in a SongData object
	 * 
	 */
	
	@GetMapping("/artist/artistName/{artistName}")
	public List<SongData> getSongsByArtistName(@PathVariable String artistName) {
		
		log.info(" Retrieving song with artist name = {} ", artistName);
		artistName = artistName.replace(" ", "");
		return songService.findSongByArtistName(artistName) ;
	}
	
	/*
	 *  
	 * @return a JSON Response with all genres of songs in a SongData object
	 * 
	 */
	
	@GetMapping("/genre")
	public List<Genre> listAllGenres(){
		
		log.info("Retrieving all genres");
		return songService.retrieveAllGenres();
	}
	
	/*
	 * @param genreId  the id of a genre to be retrieved from the database.
	 * 
	 * @return a JSON Response with all songs performed in this genre in a SongData object
	 * 
	 */
	@GetMapping("/genre/{genreId}")
	public List<SongData> getSongsByGenreId(@PathVariable Long genreId) {
		
		log.info(" Retrieving song with genre id = {} ", genreId);
		return songService.findSongByGenreId(genreId) ;
	}
	
	/*
	 * @param genreName  the name of a genre to be retrieved from the database.
	 * 
	 * @return a JSON Response with all songs performed in this genre in a SongData object
	 * 
	 */
	
	@GetMapping("/genre/genreName/{genreName}")
	public List<SongData> getSongsByGenreName(@PathVariable String genreName) {
		
		log.info(" Retrieving song with genre name = {} ", genreName);
		return songService.findSongByGenreName(genreName) ;
	}
	
	/*
	 *  
	 * @return a JSON Response with all reviews of songs in a SongData object
	 * 
	 */
	@GetMapping("/review")
	public List<Review> listAllReviews(){
		
		log.info("Retrieving all genres");
		return songService.retrieveAllReviews();
	}
	
	/*
	 *  
	 * @return a JSON Response with all artists in a SongData object
	 * 
	 */
	@GetMapping("/artist")
	public List<Artist> listAllArtists(){
		
		log.info("Retrieving all artists");
		return songService.retrieveAllArtists();
	}
	
	
	//*************************************DELETE MAPPING METHODS**************************************************
	
		@DeleteMapping("/{songId}")
		public Map<String, String> deleteSongById(@PathVariable Long songId) {
			
			log.info(" Deleting song with id = {} ", songId);
			songService.deleteSongById(songId);
			Map<String, String> deleteMessage = new HashMap<>();
			    deleteMessage.put("message", "deletion successfull");
			return deleteMessage;
		}
	
		
		
	
		
	
}
