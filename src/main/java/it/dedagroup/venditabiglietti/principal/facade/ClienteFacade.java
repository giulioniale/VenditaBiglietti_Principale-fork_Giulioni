package it.dedagroup.venditabiglietti.principal.facade;

import it.dedagroup.venditabiglietti.principal.model.Utente;
import it.dedagroup.venditabiglietti.principal.repository.UtenteRepository;
import it.dedagroup.venditabiglietti.principal.serviceimpl.UtenteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteFacade {
    @Autowired
    private UtenteServiceImpl service;

    public void disattivaUtente(Utente utente){
        service.eliminaUtente(utente.getId());
    }

    public void modificaUtente(Utente utente) {
    }
}
