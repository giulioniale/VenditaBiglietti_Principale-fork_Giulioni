package it.dedagroup.venditabiglietti.principal.model;

import java.util.ArrayList;
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
public class Luogo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	@NonNull
	private String riga1;
	@Column
	private String riga2;
	@Column(nullable = false)
	@NonNull
	private String provincia;
	@Column(nullable = false)
	@NonNull
	private String cap;
	@Column(nullable = false)
	@NonNull
	private String comune;
	@Column(nullable = false)
	@NonNull
	private String nazionalita;
	@Column(nullable = false)
	private boolean isCancellato;
	@OneToMany(mappedBy = "luogo")
	private List<Evento> eventi;
	@OneToMany(mappedBy = "luogo")
	private List<Settore> settori;
	@Version
	private long version;

    public void addEventi(Evento e) {
		if(eventi==null)eventi=new ArrayList<>();
		eventi.add(e);
    }
}
