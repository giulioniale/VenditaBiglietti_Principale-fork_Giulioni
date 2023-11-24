package it.dedagroup.venditabiglietti.principal.mapper;

import it.dedagroup.venditabiglietti.principal.dto.request.AddManifestazioneDTORequest;
import it.dedagroup.venditabiglietti.principal.dto.response.ManifestazioneDTOResponse;
import it.dedagroup.venditabiglietti.principal.dto.response.ManifestazioneMicroDTO;
import it.dedagroup.venditabiglietti.principal.model.Manifestazione;
import it.dedagroup.venditabiglietti.principal.model.Utente;
import org.springframework.stereotype.Component;

@Component
public class ManifestazioneMapper {

    public Manifestazione toManifestazione(ManifestazioneMicroDTO request, Utente venditore){
        Manifestazione m=new Manifestazione();
        m.setCancellato(request.isCancellato());
        m.setNome(request.getNome());
        m.setUtente(venditore);
        venditore.addManifestazione(m);
        m.setVersion(request.getVersion());
        m.setId(request.getId());
        return m;
    }
    
    public ManifestazioneMicroDTO fromAddManifestazioneDTORequesttoMicroDTO(AddManifestazioneDTORequest request, Utente venditore ) {
    	ManifestazioneMicroDTO m = new ManifestazioneMicroDTO();
    	m.setNome(request.getNome());
    	m.setIdCategoria(request.getIdCategoria());
    	m.setIdUtente(request.getIdUtente());
    	m.setCancellato(false);
    	return m;
    }
    
    public ManifestazioneDTOResponse fromMicroDTOtoManifestazioneDTOResponse(ManifestazioneMicroDTO request, Utente venditore) {
    	ManifestazioneDTOResponse m = new ManifestazioneDTOResponse();
    	m.setId(request.getId());
    	m.setIdCategoria(request.getIdCategoria());
    	m.setIdUtente(request.getIdUtente());
    	m.setNome(request.getNome());
    	return m;
    }
}
