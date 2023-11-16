package it.dedagroup.venditabiglietti.principal.facade;

import it.dedagroup.venditabiglietti.principal.dto.request.AggiungiUtenteDTORequest;
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

    public void registrazioneCliente(AggiungiUtenteDTORequest req){
        Utente u = uServ.findByEmail(req.getEmail());
        if(u != null){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Utente con questa email già esistente.");
        }
        uServ.aggiungiUtente(uMap.toUtenteCliente(req));
    }
}
