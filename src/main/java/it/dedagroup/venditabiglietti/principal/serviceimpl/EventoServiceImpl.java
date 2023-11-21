package it.dedagroup.venditabiglietti.principal.serviceimpl;

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
}
