package it.dedagroup.venditabiglietti.principal.serviceimpl;


import java.util.List;

import org.springframework.stereotype.Service;

import it.dedagroup.venditabiglietti.principal.model.Evento;
import it.dedagroup.venditabiglietti.principal.service.EventoServiceDef;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;

@Service
public class EventoServiceImpl implements EventoServiceDef, GeneralCallService{
	

	@Override
	public void eliminaEvento(long id) {
		callPost(servicePath+"delete/"+id, null, id, String.class);
		
	}



    private String servicePath="http://localhost:8081/evento/";

    @Override
    public List<Evento> trovaEventiFuturi() {
        String mioPath=servicePath+"trovaEventiFuturi";
        Evento[] listaEventiFuturi =callGet(mioPath,null,null, Evento[].class);
        return List.of(listaEventiFuturi);
    }

}
