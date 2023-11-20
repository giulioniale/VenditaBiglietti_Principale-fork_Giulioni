package it.dedagroup.venditabiglietti.principal.controller;

import it.dedagroup.venditabiglietti.principal.dto.request.ModificaUtenteLoggatoRequest;
import it.dedagroup.venditabiglietti.principal.facade.ClienteFacade;
import it.dedagroup.venditabiglietti.principal.model.Utente;
import it.dedagroup.venditabiglietti.principal.repository.UtenteRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
@Validated
public class ClienteController {


    private final ClienteFacade clienteFacade;

    @PutMapping("/disattiva")
    public ResponseEntity<String> disattivaUtente(Utente utente){
        clienteFacade.disattivaUtente(utente);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Hai disattivato l'account di: " + utente.getNome());
    }

    @PutMapping("/modifica")
    public ResponseEntity<String> modificaUtente(@Valid @RequestBody ModificaUtenteLoggatoRequest request){
        clienteFacade.modificaUtente(request);
        return ResponseEntity.status(HttpStatus.OK).body("La modifica dei dati è avvenuta con successo");
    }
}
