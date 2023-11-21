package it.dedagroup.venditabiglietti.principal.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Settore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	@NonNull
	private String nome;
	@Column(nullable = false)
	private int capienza;
	@Column(nullable = false)
	private boolean isCancellato;
	@ManyToOne(fetch = FetchType.LAZY)
	private Luogo luogo;
	@OneToMany(mappedBy = "settore")
	List<PrezzoSettoreEvento> prezziSettoreEvento;
	@Version
	
	private long version;

}
