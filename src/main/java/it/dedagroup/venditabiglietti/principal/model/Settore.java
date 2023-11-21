package it.dedagroup.venditabiglietti.principal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

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

    public void addPrezzoSettoreEvento(PrezzoSettoreEvento pse) {
		if(prezziSettoreEvento==null)prezziSettoreEvento= new ArrayList<>();
		prezziSettoreEvento.add(pse);
    }
}
