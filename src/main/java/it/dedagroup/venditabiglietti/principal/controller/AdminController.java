package it.dedagroup.venditabiglietti.principal.controller;

import static it.dedagroup.venditabiglietti.principal.util.UtilPath.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.dedagroup.venditabiglietti.principal.facade.AdminFacade;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(ADMIN_PATH)
@Validated
public class AdminController {
	
	@Autowired
	AdminFacade facade;
	
	@PostMapping(ELIMINA_UTENTE_VENDITORE)
	public ResponseEntity<Void> eliminaVenditore(
			@RequestParam @Min(value = 1,message = "id non valido per eliminare l'utente venditore") long id){
		facade.eliminaVenditore(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
	
	@PostMapping(ELIMINA_UTENTE_CLIENTE)
	public ResponseEntity<Void> eliminaCliente(
			@RequestParam @Min(value = 1,message = "id non valido per eliminare l'utente cliente") long id){
		facade.eliminaCliente(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

}
