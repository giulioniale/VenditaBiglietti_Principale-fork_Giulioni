package it.dedagroup.venditabiglietti.principal.controller;
import static it.dedagroup.venditabiglietti.principal.util.UtilPath.ADMIN_PATH;
import static it.dedagroup.venditabiglietti.principal.util.UtilPath.AGGIUNGI_CATEGORIA;
import static it.dedagroup.venditabiglietti.principal.util.UtilPath.AGGIUNGI_SETTORE;
import static it.dedagroup.venditabiglietti.principal.util.UtilPath.AGGIUNGI_UTENTE_VENDITORE;
import static it.dedagroup.venditabiglietti.principal.util.UtilPath.ELIMINA_UTENTE_CLIENTE;
import static it.dedagroup.venditabiglietti.principal.util.UtilPath.ELIMINA_UTENTE_VENDITORE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import it.dedagroup.venditabiglietti.principal.dto.request.AggiungiCategoriaDtoRequest;
import it.dedagroup.venditabiglietti.principal.dto.request.AggiungiSettoreDtoRequest;
import it.dedagroup.venditabiglietti.principal.dto.request.AggiungiUtenteDTORequest;
import it.dedagroup.venditabiglietti.principal.facade.AdminFacade;
import jakarta.validation.Valid;
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
	
	@PostMapping(AGGIUNGI_UTENTE_VENDITORE)
	public ResponseEntity<Void> aggiungiVenditore(
			@Valid @RequestBody AggiungiUtenteDTORequest request){
		facade.aggiungiVenditore(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping(AGGIUNGI_CATEGORIA)
	public ResponseEntity<Void> aggiungiCategoria(
			@Valid @RequestBody AggiungiCategoriaDtoRequest request){
		facade.aggiungiCategoria(request);
		return ResponseEntity.status(HttpStatus.CREATED).build(); 
		
	}

	@PostMapping(AGGIUNGI_SETTORE)
	public ResponseEntity<Void> aggiungiSettore(
		@Valid @RequestBody AggiungiSettoreDtoRequest request) {
		facade.aggiungiSettore(request);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
	

}
