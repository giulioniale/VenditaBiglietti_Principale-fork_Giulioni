package it.dedagroup.venditabiglietti.principal.model;

import java.time.LocalDateTime;
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
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	@NonNull
	private LocalDateTime dataOra;
	@Column(nullable = false)
	@NonNull
	private String descrizione;
	@Column(nullable = false)
	private boolean isCancellato = true;
	@ManyToOne(fetch = FetchType.LAZY)
	private Manifestazione manifestazione;
	@ManyToOne(fetch = FetchType.LAZY)
	private Luogo luogo;
	@OneToMany(mappedBy = "evento")
	private List<PrezzoSettoreEvento> prezziSettoreEvento;
	@Version
	private long version;
}
