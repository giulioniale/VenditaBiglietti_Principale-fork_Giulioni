package it.dedagroup.venditabiglietti.principal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dedagroup.venditabiglietti.principal.dto.request.AddEventoRequest;
import it.dedagroup.venditabiglietti.principal.dto.response.EventoDTOResponse;
import it.dedagroup.venditabiglietti.principal.dto.response.VisualizzaEventoManifestazioneDTOResponse;
import it.dedagroup.venditabiglietti.principal.facade.VenditoreFacade;

@RestController
@RequestMapping("/venditore")
public class VenditoreController {
	
	@Autowired
	VenditoreFacade vendFac;
	
	@PostMapping("/evento/add")
	public ResponseEntity<EventoDTOResponse> addEvento(@RequestBody AddEventoRequest eventoRequest){
		return ResponseEntity.ok(vendFac.addEvento(eventoRequest));
	}
	
	@PostMapping("/evento/delete")
	public ResponseEntity<EventoDTOResponse> deleteEvento(@PathVariable long idManifestazione){
		return ResponseEntity.ok(vendFac.deleteEvento(idManifestazione));
	}
	
	@GetMapping("/evento/visualizza/{idManifestazione}")
	public ResponseEntity<VisualizzaEventoManifestazioneDTOResponse> visualizzaEventiOrganizzati(@PathVariable long idManifestazione){
		return ResponseEntity.ok(vendFac.visualizzaEventiOrganizzati(idManifestazione));
	}
}	
