package it.dedagroup.venditabiglietti.principal.service;

import it.dedagroup.venditabiglietti.principal.dto.response.ManifestazioneMicroDTO;
import it.dedagroup.venditabiglietti.principal.model.Manifestazione;

public interface ManifestazioneServiceDef {

	void eliminaManifestazione(long id);
	ManifestazioneMicroDTO findById(long idManifestazione);
}
