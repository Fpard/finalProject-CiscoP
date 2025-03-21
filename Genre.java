package songs.catalog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Genre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long genreId;
	
	private String genreName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//********************
	@JsonBackReference
	//*******************
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "song_id")
	private Song song;

}
