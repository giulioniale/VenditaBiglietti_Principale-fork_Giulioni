package it.dedagroup.venditabiglietti.principal.facade;

import it.dedagroup.venditabiglietti.principal.dto.request.AggiungiUtenteDTORequest;
import it.dedagroup.venditabiglietti.principal.dto.request.LoginDTORequest;
import it.dedagroup.venditabiglietti.principal.dto.response.EventoDTOResponse;
import it.dedagroup.venditabiglietti.principal.mapper.EventoMapper;
import it.dedagroup.venditabiglietti.principal.mapper.UtenteMapper;
import it.dedagroup.venditabiglietti.principal.model.Evento;
import it.dedagroup.venditabiglietti.principal.model.Utente;
import it.dedagroup.venditabiglietti.principal.service.UtenteServiceDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;

import java.util.List;

@Service
public class GeneralFacade implements GeneralCallService{

    @Autowired
    UtenteServiceDef uServ;

    @Autowired
    EventoMapper evMap;

    @Autowired
    UtenteMapper uMap;

    private final String pathEvento="http://localhost:8081/evento";

    public void registrazioneCliente(AggiungiUtenteDTORequest req){
        Utente uNew =uMap.toUtenteCliente(req);
        uServ.aggiungiUtente(uNew);
    }

    public Utente login(LoginDTORequest request){
        Utente u = uServ.login(request.getEmail(), request.getPassword());
        return u;
    }

    public List<EventoDTOResponse> trovaEventiFuturiConBiglietti(){
        List<Evento> eventiFuturi = callGetForList(pathEvento + "/trovaEventiFuturi", null, null, Evento[].class);
        return evMap.toEventoDTOResponseList(eventiFuturi);
    }

}
