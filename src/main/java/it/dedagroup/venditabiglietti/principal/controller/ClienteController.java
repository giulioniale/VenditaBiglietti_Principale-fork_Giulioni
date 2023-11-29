package it.dedagroup.venditabiglietti.principal.controller;

import it.dedagroup.venditabiglietti.principal.dto.request.ModificaUtenteLoggatoRequest;
import it.dedagroup.venditabiglietti.principal.dto.response.BigliettoMicroDTO;
import it.dedagroup.venditabiglietti.principal.facade.ClienteFacade;
import it.dedagroup.venditabiglietti.principal.model.Utente;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

import static it.dedagroup.venditabiglietti.principal.util.UtilPath.*;

@RestController
@RequestMapping(CLIENTE_LOGGATO_PATH)
@Tag (name="Endpoint utente loggato",
description = "Questo controller gestisce gli endpoint dell'utente che ha effettuato il login")
@AllArgsConstructor
@Validated
public class ClienteController {


    private final ClienteFacade clienteFacade;


    @GetMapping(CRONOLOGIA_ACQUISTI+"{id}")
    public ResponseEntity<List<BigliettoMicroDTO>> cronologiaBiglietti(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(clienteFacade.cronologiaBigliettiAcquistati(id));
    }

    @PutMapping(DISATTIVA_UTENTE+"{id}")
    public ResponseEntity<String> disattivaUtente(@PathVariable Long id){
        clienteFacade.disattivaUtente(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Hai disattivato l'account");
    }

    @Operation(summary = "Endpoint che permette ad un cliente di modificare i suoi dati",
    		description = "In questo metodo, passando per una request in cui saranno contenuti: i vecchi dati,utili ad autenticare l'utente e verificare che realmente esista; i nuovi dati che potranno essere password, email o numero di telefono"
    							+"risponderà 200 se la odifica viene fatta correttamente, altrimenti 404 se l'utente inserisce dati non validi ")
    @ApiResponses({
    	@ApiResponse(responseCode ="ACCEPTED(202)" ,
    							description = "Cliente disattivato correttamente",
    							content = @Content(mediaType = MediaType.ALL_VALUE)
    				),
    	@ApiResponse(responseCode = "NOTFOUND(404)",
    							description = "Impossibile disattivare questo account: non esiste nessun cliente con questo id",
    							content = @Content(mediaType = MediaType.ALL_VALUE))
    })
    @PutMapping(MODIFICA_UTENTE)
    public ResponseEntity<String> modificaUtente(@Valid @RequestBody ModificaUtenteLoggatoRequest request){
        clienteFacade.modificaUtente(request);
        return ResponseEntity.status(HttpStatus.OK).body("La modifica dei dati è avvenuta con successo");
    }

    @PostMapping(ACQUISTA_BIGLIETTO+"{idPrezzoSettoreEvento}")
    public ResponseEntity<BigliettoMicroDTO> acquistaBiglietto(@PathVariable Long idPrezzoSettoreEvento, UsernamePasswordAuthenticationToken token) {
        Utente principal = (Utente)token.getPrincipal();
        long idPrincipal = principal.getId();
      return ResponseEntity.status(HttpStatus.OK).body(clienteFacade.acquistaBiglietto(idPrezzoSettoreEvento, idPrincipal));
    }

    @GetMapping(FIND_ALL_BIGLIETI_BY_CRITERIA)
    public ResponseEntity<List<BigliettoMicroDTO>> findAllBigliettiCriteria(@NotBlank(message = "Il filtro deve contenere almeno un campo.")
                                                                                @RequestBody Map<String, String> criteria){
        return ResponseEntity.status(HttpStatus.OK).body(clienteFacade.findAllBigliettiCriteria(criteria));
    }
}
