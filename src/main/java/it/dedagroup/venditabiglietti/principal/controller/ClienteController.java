package it.dedagroup.venditabiglietti.principal.controller;

import it.dedagroup.venditabiglietti.principal.dto.request.ModificaUtenteLoggatoRequest;
import it.dedagroup.venditabiglietti.principal.facade.ClienteFacade;
import it.dedagroup.venditabiglietti.principal.model.Biglietto;
import it.dedagroup.venditabiglietti.principal.model.PrezzoSettoreEvento;
import it.dedagroup.venditabiglietti.principal.model.Utente;
import it.dedagroup.venditabiglietti.principal.repository.UtenteRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
@Validated
public class ClienteController {


    private final ClienteFacade clienteFacade;


    @GetMapping("/{id}/cronologia-acquisti")
    public ResponseEntity<List<Biglietto>> cronologiaBiglietti(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(clienteFacade.cronologiaBigliettiAcquistati(id));
    }

    @PutMapping("/disattiva/{id}")
    public ResponseEntity<String> disattivaUtente(@PathVariable Long id){
        clienteFacade.disattivaUtente(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Hai disattivato l'account");
    }

    @PutMapping("/modifica")
    public ResponseEntity<String> modificaUtente(@Valid @RequestBody ModificaUtenteLoggatoRequest request){
        clienteFacade.modificaUtente(request);
        return ResponseEntity.status(HttpStatus.OK).body("La modifica dei dati è avvenuta con successo");
    }
    @PostMapping("/crea-biglietto/{idPrezzoSettoreEvento}")
    public ResponseEntity<Biglietto> acquistaBiglietto(@PathVariable Long idPrezzoSettoreEvento, UsernamePasswordAuthenticationToken token) {
        Utente principal = (Utente)token.getPrincipal();
        long idPrincipal = principal.getId();
      return ResponseEntity.status(HttpStatus.OK).body(clienteFacade.acquistaBiglietto(idPrezzoSettoreEvento, idPrincipal));
    }
}
