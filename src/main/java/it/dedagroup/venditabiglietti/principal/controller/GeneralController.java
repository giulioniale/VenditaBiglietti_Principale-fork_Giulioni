package it.dedagroup.venditabiglietti.principal.controller;

import it.dedagroup.venditabiglietti.principal.dto.request.AggiungiUtenteDTORequest;
import it.dedagroup.venditabiglietti.principal.facade.GeneralFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/all")
public class GeneralController {

    @Autowired
    GeneralFacade gFac;

    @PostMapping("/registrazioneCliente")
    public ResponseEntity<Void> registrazioneCliente(@RequestBody AggiungiUtenteDTORequest req){
        gFac.registrazioneCliente(req);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
