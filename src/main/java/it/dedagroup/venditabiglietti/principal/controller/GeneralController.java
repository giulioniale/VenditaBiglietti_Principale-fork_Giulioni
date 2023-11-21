package it.dedagroup.venditabiglietti.principal.controller;

import it.dedagroup.venditabiglietti.principal.dto.request.AggiungiUtenteDTORequest;
import it.dedagroup.venditabiglietti.principal.dto.request.LoginDTORequest;
import it.dedagroup.venditabiglietti.principal.dto.response.EventoDTOResponse;
import it.dedagroup.venditabiglietti.principal.facade.GeneralFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/all")
public class GeneralController {

    @Autowired
    GeneralFacade gFac;

    @PostMapping(REGISTRAZIONE)
    public ResponseEntity<Void> registrazioneCliente(@Valid @RequestBody AggiungiUtenteDTORequest req){
        gFac.registrazioneCliente(req);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping(EVENTI_FUTURI_CON_BIGLIETTI)
    public ResponseEntity<List<EventoDTOResponse>> eventiFutConBiglietti(){
        return ResponseEntity.status(HttpStatus.OK).body(gFac.trovaEventiFuturiConBiglietti());
    }


    @PostMapping(LOGIN)
    public ResponseEntity<Void> login (@RequestBody LoginDTORequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).header("Authorization",gFac.login(request)).build();
    }

}
