package it.dedagroup.venditabiglietti.principal.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Biglietto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	@NonNull
	private LocalDate dataAcquisto;
	@Column(nullable = false)
	private double prezzo;
	@Column(nullable = false, unique = true)
	@NonNull
	private String nSeriale;
	@Column(nullable = false)
	private boolean isCancellato = true;
	@ManyToOne(fetch = FetchType.LAZY)
	private Utente utente;
	@ManyToOne(fetch = FetchType.LAZY)
	private PrezzoSettoreEvento prezzoSettoreEvento;
	@Version
	
	private long version;

}
