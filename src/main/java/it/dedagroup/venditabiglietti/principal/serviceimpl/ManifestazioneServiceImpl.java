package it.dedagroup.venditabiglietti.principal.serviceimpl;

import it.dedagroup.venditabiglietti.principal.dto.response.ManifestazioneMicroDTO;
import org.springframework.stereotype.Service;

import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;
import it.dedagroup.venditabiglietti.principal.service.ManifestazioneServiceDef;

@Service
public class ManifestazioneServiceImpl implements ManifestazioneServiceDef, GeneralCallService{

	String pathManifestazione="http://localhost:8084/";
	
	@Override
	public void eliminaManifestazione(long id) {
		callPost(pathManifestazione+"manifestazione/delete/"+id, null, id, String.class);
		
	}

	@Override
	public ManifestazioneMicroDTO findById(long idManifestazione) {
		String mioPath=pathManifestazione+"manifestazione/find/"+idManifestazione;
		return callGet(mioPath,null,idManifestazione,ManifestazioneMicroDTO.class);
	}


}
