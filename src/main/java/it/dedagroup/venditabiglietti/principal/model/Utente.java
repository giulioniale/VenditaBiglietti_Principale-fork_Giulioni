package it.dedagroup.venditabiglietti.principal.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Utente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private long id;
	@Column(nullable = false)
	@NonNull
	private String nome;
	@Column(nullable = false)
	@NonNull
	private String cognome;
	@Column(nullable = false)
	@NonNull
	private LocalDate dataDiNascita;
	@Column(nullable = false)
	@NonNull
	private Ruolo ruolo;
	@Column(nullable = false , unique = true)
	@NonNull
	private String email;
	@Column(nullable = false)
	@NonNull
	private String password;
	@Column(nullable = false, unique = true)
	@NonNull
	private String telefono;
	@Column(nullable = false)
	private boolean isCancellato;
	@OneToMany(mappedBy = "utente")
	private List<Biglietto> biglietti;
	@OneToMany(mappedBy = "utente")
	private List<Manifestazione> manifestazioni;
	@Version
	@Column(nullable = false, columnDefinition = "BIGINT DEFAULT 1")
	private long version;

}
