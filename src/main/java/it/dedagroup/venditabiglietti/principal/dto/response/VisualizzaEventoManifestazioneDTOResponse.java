package it.dedagroup.venditabiglietti.principal.dto.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisualizzaEventoManifestazioneDTOResponse {
	
	private String nomeManifestazione;
	private Map<String, String> eventiManifestazione;
	private String nomeOrganizzatore;
	
}


	

	/* 
		manifestazione : Concerto Gem
		
		eventoManifestazione : [
				Festival : Via delle vie 8
		]
		
		organizzatore : m.c@ded.it
		 							



	*/