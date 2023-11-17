package it.dedagroup.venditabiglietti.principal.controller;

import it.dedagroup.venditabiglietti.principal.dto.request.EventoSettorePseDTORequest;
import it.dedagroup.venditabiglietti.principal.dto.request.ManifestazioneStatisticheDTORequest;
import it.dedagroup.venditabiglietti.principal.dto.response.StatisticheBigliettiDTOResponse;
import it.dedagroup.venditabiglietti.principal.dto.response.StatisticheManifestazioneDTOResponse;
import it.dedagroup.venditabiglietti.principal.facade.VenditoreFacade;
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

@RestController
@RequestMapping("/venditore")
public class VenditoreController {

	@Autowired
	VenditoreFacade vendFac;

	@PostMapping("/evento/add")
	public ResponseEntity<EventoDTOResponse> addEvento(@RequestBody AddEventoRequest eventoRequest){
		return ResponseEntity.ok(vendFac.addEvento(eventoRequest));
	}

	@PostMapping("/evento/delete/{id}")
	public ResponseEntity<EventoDTOResponse> deleteEvento(@PathVariable("id") long idManifestazione){
		return ResponseEntity.ok(vendFac.deleteEvento(idManifestazione));
	}

	@GetMapping("/evento/visualizza/{idManifestazione}")
	public ResponseEntity<VisualizzaEventoManifestazioneDTOResponse> visualizzaEventiOrganizzati(@PathVariable long idManifestazione){
		return ResponseEntity.ok(vendFac.visualizzaEventiOrganizzati(idManifestazione));
	}
	@GetMapping("/evento/stats/biglietti")
	public ResponseEntity<StatisticheBigliettiDTOResponse> statisticheBigliettiPerEventoSettorePrezzoSettoreEvento(@RequestBody EventoSettorePseDTORequest request){
		return ResponseEntity.ok(vendFac.statisticheBigliettiPerEventoSettorePrezzoSettoreEvento(request));
	}
	@GetMapping("/manifestazione/stats/biglietti")
	public ResponseEntity<StatisticheManifestazioneDTOResponse> statisticheBigliettiPerManifestazione(@RequestBody ManifestazioneStatisticheDTORequest request){
		return ResponseEntity.ok(vendFac.statisticheBigliettiPerManifestazione(request));
	}
}
