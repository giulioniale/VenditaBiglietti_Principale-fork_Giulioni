package it.dedagroup.venditabiglietti.principal.controller;

import it.dedagroup.venditabiglietti.principal.dto.request.EventoSettorePseDTORequest;
import it.dedagroup.venditabiglietti.principal.dto.request.ManifestazioneStatisticheDTORequest;
import it.dedagroup.venditabiglietti.principal.dto.response.DatiEventiDTOResponse;
import it.dedagroup.venditabiglietti.principal.dto.response.StatisticheManifestazioneDTOResponse;
import it.dedagroup.venditabiglietti.principal.facade.VenditoreFacade;
import it.dedagroup.venditabiglietti.principal.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dedagroup.venditabiglietti.principal.dto.request.AddEventoRequest;
import it.dedagroup.venditabiglietti.principal.dto.response.EventoDTOResponse;
import it.dedagroup.venditabiglietti.principal.dto.response.VisualizzaEventoManifestazioneDTOResponse;

@RestController
@RequestMapping("/venditore")
public class VenditoreController {

	@Autowired
	VenditoreFacade vendFac;

	@PostMapping("/evento/add")
	public ResponseEntity<EventoDTOResponse> addEvento(@RequestBody AddEventoRequest eventoRequest){
		//TODO L'aggiunta dell'evento si basa su una manifestazione già presente
		//TODO Inserire l'upat per riprendere l'email
		//TODO aggiungere il controllo tramite email per capire se l'aggiunta della manifestazione venga da un venditore
		return ResponseEntity.ok(vendFac.addEvento(eventoRequest));
	}

	@PostMapping("/evento/delete/{id}")
	public ResponseEntity<EventoDTOResponse> deleteEvento(@PathVariable("id") long idManifestazione){
		//TODO Inserire l'upat per riprendere l'email
		//TODO aggiungere il controllo tramite email per capire se l'aggiunta della manifestazione venga da un venditore
		return ResponseEntity.ok(vendFac.deleteEvento(idManifestazione));
	}

	@GetMapping("/evento/visualizza/{idManifestazione}")
	public ResponseEntity<VisualizzaEventoManifestazioneDTOResponse> visualizzaEventiOrganizzati(@PathVariable long idManifestazione){
		//TODO L'aggiunta dell'evento si basa su una manifestazione già presente
		//TODO Inserire l'upat per riprendere l'email
		return ResponseEntity.ok(vendFac.visualizzaEventiOrganizzati(idManifestazione));
	}

	@GetMapping("/manifestazione/stats/biglietti")
	public ResponseEntity<StatisticheManifestazioneDTOResponse> statisticheBigliettiPerManifestazione(@RequestBody ManifestazioneStatisticheDTORequest request,UsernamePasswordAuthenticationToken upat){
		//TODO L'aggiunta dell'evento si basa su una manifestazione già presente
		//TODO Inserire l'upat per riprendere l'email
		return ResponseEntity.ok(vendFac.statisticheBigliettiPerManifestazione(request,((Utente) upat.getPrincipal())));
	}
}
