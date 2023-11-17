package it.dedagroup.venditabiglietti.principal.controller;

import static it.dedagroup.venditabiglietti.principal.util.UtilPath.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dedagroup.venditabiglietti.principal.dto.request.AggiungiUtenteDTORequest;
import it.dedagroup.venditabiglietti.principal.facade.SuperAdminFacade;
import it.dedagroup.venditabiglietti.principal.security.GestoreToken;
import jakarta.validation.Valid;

@RestController
@RequestMapping(SUPER_ADMIN_PATH)
public class SuperAdminController {
	
	@Autowired
	SuperAdminFacade suFacade;

	@PostMapping(AGGIUNGI_ADMIN)
    public ResponseEntity<Void> registrazioneAdmin(@Valid @RequestBody AggiungiUtenteDTORequest req){
        suFacade.registrazioneAdmin(req);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}