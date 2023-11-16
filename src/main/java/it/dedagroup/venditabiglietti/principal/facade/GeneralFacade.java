package it.dedagroup.venditabiglietti.principal.facade;

import it.dedagroup.venditabiglietti.principal.dto.request.AggiungiUtenteDTORequest;
import it.dedagroup.venditabiglietti.principal.dto.request.LoginDTORequest;
import it.dedagroup.venditabiglietti.principal.mapper.UtenteMapper;
import it.dedagroup.venditabiglietti.principal.model.Utente;
import it.dedagroup.venditabiglietti.principal.service.UtenteServiceDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GeneralFacade {

    @Autowired
    UtenteServiceDef uServ;

    @Autowired
    UtenteMapper uMap;
    //quando si toglieranno le annotazioni sulle entity, si perderanno anche i vincoli di unicità e si potranno registrare anche utenti con la stessa mail
    //come si risole questa cosa? Edit:quando sposteremo utente su microservizio questa cosa dovrebbe risolversi da sola
    public void registrazioneCliente(AggiungiUtenteDTORequest req){
        Utente uNew =uMap.toUtenteCliente(req);
        uServ.aggiungiUtente(uNew);
    }

    public Utente login(LoginDTORequest request){
        if(request.isRequestNull()){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Nessun utente trovato con queste credenziali");
        } else return uServ.login(request.getEmail(),request.getPassword());
    }
}
