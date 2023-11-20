package it.dedagroup.venditabiglietti.principal.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.dedagroup.venditabiglietti.principal.dto.request.AggiungiSettoreDtoRequest;

import it.dedagroup.venditabiglietti.principal.model.Ruolo;
import it.dedagroup.venditabiglietti.principal.model.Settore;
import it.dedagroup.venditabiglietti.principal.model.Utente;
import it.dedagroup.venditabiglietti.principal.service.SettoreServiceDef;
import it.dedagroup.venditabiglietti.principal.service.UtenteServiceDef;

@Service
public class AdminFacade {
	
	@Autowired
	UtenteServiceDef utenteService;
	
	@Autowired
	SettoreServiceDef settoreService;
	
	
	
	
	public void eliminaVenditore(long id) {
		Utente u = utenteService.findById(id);
		if(u.getRuolo()!=Ruolo.VENDITORE) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "L'utente con id " + id +  " non è un cliente");
		utenteService.eliminaUtente(id);
	}
	
	public void eliminaCliente(long id) {
		Utente u = utenteService.findById(id);
		if(u.getRuolo()!=Ruolo.CLIENTE) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "L'utente con id " + id +  " non è un cliente");
		utenteService.eliminaUtente(id);
	}
	
	public void aggiungiSettore(AggiungiSettoreDtoRequest dto) {
		 settoreService.aggiungiSettore(dto);
		
	}

}
