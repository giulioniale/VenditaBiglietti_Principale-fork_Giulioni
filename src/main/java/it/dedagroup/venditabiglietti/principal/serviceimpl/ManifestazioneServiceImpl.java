package it.dedagroup.venditabiglietti.principal.serviceimpl;

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

	
	
}
