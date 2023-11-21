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

    private final String pathEvento="http://localhost:8081/evento/";

    @Autowired
    UtenteMapper uMap;
    //quando si toglieranno le annotazioni sulle entity, si perderanno anche i vincoli di unicità e si potranno registrare anche utenti con la stessa mail
    //come si risole questa cosa? Edit:quando sposteremo utente su microservizio questa cosa dovrebbe risolversi da sola
    public void registrazioneCliente(AggiungiUtenteDTORequest req){
        Utente uNew =uMap.toUtenteCliente(req);
        uServ.aggiungiUtente(uNew);
    }

    public Utente login(LoginDTORequest request){
        Utente u = uServ.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if(u.isCancellato()){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Nessun utente trovato con queste credenziali");
        } else return uServ.login(request.getEmail(),request.getPassword());
    }

    public List<EventoDTOResponse> trovaEventiFuturiConBiglietti(){
        List<Evento> eventiFuturi = callGetForList(pathEvento + "/trovaEventiFuturi", null, null, Evento[].class);
        return evMap.toEventoDTOResponseList(eventiFuturi);
    }

}
