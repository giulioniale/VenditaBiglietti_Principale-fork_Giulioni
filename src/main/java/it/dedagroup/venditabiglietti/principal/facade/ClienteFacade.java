package it.dedagroup.venditabiglietti.principal.facade;

import it.dedagroup.venditabiglietti.principal.dto.request.ModificaUtenteLoggatoRequest;
import it.dedagroup.venditabiglietti.principal.model.Utente;
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

    public void modificaUtente(ModificaUtenteLoggatoRequest request) {
    	Utente utenteDaMod=service.findByEmailAndPassword(request.getEmailAttuale(), request.getPasswordAttuale());
    	 String nuovaEmail = request.getNuovaEmail();
         String nuovaPassword = request.getNuovaPassword();
         String nuovoTelefono = request.getNuovoTelefono();
         if (nuovaEmail != null && !nuovaEmail.isEmpty()) {
        	 utenteDaMod.setEmail(nuovaEmail);
         }
         if (nuovaPassword != null && !nuovaPassword.isEmpty()) {
        	 utenteDaMod.setPassword(nuovaPassword);
         }
         if (nuovoTelefono != null && !nuovoTelefono.isEmpty()) {
        	 utenteDaMod.setTelefono(nuovoTelefono);
         }
    	service.modificaUtente(utenteDaMod);
    }
}
