package it.dedagroup.venditabiglietti.principal.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EventoDTOResponse {
	
	private long id;
	private LocalDate data;
	private LocalTime ora;
	private String descrizione;
	private long idManufestazione;
	private long idLuogo;
	private List<Long> idPrezzoSettoreEvento;
}
