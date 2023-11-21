package it.dedagroup.venditabiglietti.principal.serviceimpl;

import org.springframework.stereotype.Service;

import it.dedagroup.venditabiglietti.principal.service.EventoServiceDef;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;

@Service
public class EventoServiceImpl implements EventoServiceDef, GeneralCallService{
	
	String pathEvento="http://localhost:8081";

	@Override
	public void eliminaEvento(long id) {
		callPost(pathEvento+"manifestazione/delete/"+id, null, id, String.class);
		
	}

}
