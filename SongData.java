package songs.catalog.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import songs.catalog.entity.Artist;
import songs.catalog.entity.Genre;
import songs.catalog.entity.Review;
import songs.catalog.entity.Song;


@Data
@NoArgsConstructor
public class SongData {
	
	Long songId;
	
	private String songTitle;
	private String albumName;
	private Set<SongArtist> artists = new HashSet<> ();
	private Set<SongGenre> genres = new HashSet<> ();
	private Set<SongReview> reviews = new HashSet<>();
	
	
	public SongData(Song song) {
		
		songId = song.getSongId();
		songTitle = song.getSongTitle();
		albumName = song.getAlbumName();
		//songArtistName = song.getSongArtistName();
		
		for (Artist artist: song.getArtists()){
			
			 artists.add(new SongArtist(artist));
		}
		
		for (Genre genre: song.getGenres()){
			
			 genres.add(new SongGenre(genre));
		}
		
		for (Review review: song.getReviews()){
			
			 reviews.add(new SongReview(review));
		}
	}//End Constructor

	@Data
	@NoArgsConstructor
	
	public static class SongArtist {
		private Long artistId;
		private String artistName;
		
		public SongArtist(Artist artist) {
			
			artistId = artist.getArtistId();
			artistName = artist.getArtistName();
			
		}
		
		
	}

	@Data
	@NoArgsConstructor
	
	public static class SongGenre{
		private Long genreId;
		private String genreName;
		
		public SongGenre(Genre genre) {
			
			genreId = genre.getGenreId();
			genreName = genre.getGenreName();
			
		}
		
	}
	
	
	@Data
	@NoArgsConstructor
	public static class SongReview{
		private Long reviewId;
		private String reviewName;
		private String reviewContent;
		
		public SongReview(Review review) {
			reviewId = review.getReviewId();
			reviewName = review.getReviewName();
			reviewContent = review.getReviewContent();
			
			
		}
	}
	
}
