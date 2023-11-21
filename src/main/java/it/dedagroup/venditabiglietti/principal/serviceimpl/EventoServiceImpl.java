package it.dedagroup.venditabiglietti.principal.serviceimpl;

<<<<<<< HEAD
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

=======
import it.dedagroup.venditabiglietti.principal.model.Evento;
import it.dedagroup.venditabiglietti.principal.service.EventoServiceDef;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;
import jdk.jfr.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventoServiceImpl implements GeneralCallService, EventoServiceDef {

    private String servicePath="http://localhost:8081/evento/";

    @Override
    public List<Evento> trovaEventiFuturi() {
        String mioPath=servicePath+"trovaEventiFuturi";
        Evento[] listaEventiFuturi =callGet(mioPath,null,null, Evento[].class);
        return List.of(listaEventiFuturi);
    }
>>>>>>> 88087fb054f8f6e35b3b9d94775a2abf72058f13
}
