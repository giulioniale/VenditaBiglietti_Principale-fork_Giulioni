package it.dedagroup.venditabiglietti.principal.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dedagroup.venditabiglietti.principal.dto.request.AggiungiUtenteDTORequest;
import it.dedagroup.venditabiglietti.principal.mapper.UtenteMapper;
import it.dedagroup.venditabiglietti.principal.model.Utente;
import it.dedagroup.venditabiglietti.principal.security.GestoreToken;
import it.dedagroup.venditabiglietti.principal.service.UtenteServiceDef;

@Service
public class SuperAdminFacade {
	
	@Autowired
	UtenteServiceDef utenteService;
	
	@Autowired
    UtenteMapper uMap;
	
	
	public void registrazioneAdmin(AggiungiUtenteDTORequest req) {
		Utente uNew = uMap.toUtenteAdmin(req);
		utenteService.aggiungiUtente(uNew);
	}

}
