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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrezzoSettoreEvento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne(fetch = FetchType.LAZY)
	private Evento evento;
	@ManyToOne(fetch = FetchType.LAZY)
	private Settore settore;
	@Column(nullable = false)
	private double prezzo;
	@Column(nullable = false)
	private boolean isCancellato;
	@OneToMany(mappedBy = "prezzoSettoreEvento")
	private List<Biglietto> biglietti;
	@Version
	private long version;

}
