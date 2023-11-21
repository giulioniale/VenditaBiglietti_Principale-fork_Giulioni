package it.dedagroup.venditabiglietti.principal.controller;

import it.dedagroup.venditabiglietti.principal.dto.request.ModificaUtenteLoggatoRequest;
import it.dedagroup.venditabiglietti.principal.facade.ClienteFacade;
import it.dedagroup.venditabiglietti.principal.model.Utente;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/cliente")
@Tag (name="Endpoint microservizio Settore",
description = "Questo controller gestisce il microservizio del Settore e interagisce sul database solo sulla tabella Settore")
@AllArgsConstructor
@Validated
public class ClienteController {

 
    private final ClienteFacade clienteFacade;
    
    
    @Operation(summary = "Endpoint che disattiva un utente",description = "Questo metodo serve a disattivare un utente. Passando un un utente leggiamo il suo id, controlliamo se esite nel db e settiamo la variabile isCancellato a true. Se l'utente non esiste viene lanciata un eccezione e il metodo risponderà con codice 404, altrimenti rispondiamo 202")
    @ApiResponses({
    	@ApiResponse(responseCode ="ACCEPTED(202)" ,
    							description = "Cliente disattivato correttamente",
    							content = @Content(mediaType = MediaType.ALL_VALUE)
    			),
    	@ApiResponse(responseCode = "NOTFOUND(404)",
    							description = "Impossibile disattivare questo account: non esiste nessun cliente con questo id",
    							content = @Content(mediaType = MediaType.ALL_VALUE)) 							
    })
    @PutMapping("/disattiva")
    public ResponseEntity<String> disattivaUtente(Utente utente){
        clienteFacade.disattivaUtente(utente);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Hai disattivato l'account di: " + utente.getNome());
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
    @PutMapping("/modifica")
    public ResponseEntity<String> modificaUtente(@Valid @RequestBody ModificaUtenteLoggatoRequest request){
        clienteFacade.modificaUtente(request);
        return ResponseEntity.status(HttpStatus.OK).body("La modifica dei dati è avvenuta con successo");
    }
}
