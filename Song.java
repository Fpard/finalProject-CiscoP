package songs.catalog.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import songs.catalog.controller.model.SongData.SongArtist;
import songs.catalog.controller.model.SongData.SongGenre;
import songs.catalog.controller.model.SongData.SongReview;

@Entity
@Data
public class Song {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long songId;
	
	private String songTitle;
	private String albumName;
	//private String songArtistName;
	
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "song_artist",
	joinColumns = @JoinColumn(name = "song_id"),
	inverseJoinColumns = @JoinColumn(name = "artist_id"))
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Artist> artists = new HashSet<> ();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//******************
    @JsonManagedReference
    //*********************
	@OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Genre> genres = new HashSet<> ();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//******************
    @JsonManagedReference
    //*********************
	@OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Review> reviews = new HashSet<>();
	
	
	
}
