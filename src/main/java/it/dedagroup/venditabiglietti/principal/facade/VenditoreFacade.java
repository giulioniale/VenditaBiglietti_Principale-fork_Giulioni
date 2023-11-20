package it.dedagroup.venditabiglietti.principal.facade;

import it.dedagroup.venditabiglietti.principal.dto.request.AddEventoRequest;
import it.dedagroup.venditabiglietti.principal.dto.request.AddManifestazioneDTORequest;
import it.dedagroup.venditabiglietti.principal.dto.response.EventoDTOResponse;
import it.dedagroup.venditabiglietti.principal.dto.response.LuogoDtoResponse;
import it.dedagroup.venditabiglietti.principal.dto.response.ManifestazioneDTOResponse;
import it.dedagroup.venditabiglietti.principal.dto.response.VisualizzaEventoManifestazioneDTOResponse;
import it.dedagroup.venditabiglietti.principal.model.Categoria;
import it.dedagroup.venditabiglietti.principal.model.Evento;
import it.dedagroup.venditabiglietti.principal.model.Luogo;
import it.dedagroup.venditabiglietti.principal.model.Manifestazione;
import it.dedagroup.venditabiglietti.principal.model.Utente;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VenditoreFacade implements GeneralCallService {

    private final String pathManifestazione="http://localhost:8084/manifestazione/";
    private final String pathUtente="http://localhost:8085/utente/";
    private final String pathCategoria="http://localhost:8082/categoria/";
    private final String pathLuogo="http://localhost:8088/luogo/";
    private final String pathEvento="http://localhost:8090/evento/";

    public ManifestazioneDTOResponse addManifestazione(AddManifestazioneDTORequest request){
        Utente u=callGet(pathUtente+"findById/"+request.getIdUtente(),null, null, Utente.class);
        if (u==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Utente non esistente!");
        Categoria c=callGet(pathCategoria+"trova/"+request.getIdCategoria(), null, null, Categoria.class);
        if(c==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Categoria non esistente!");
//        Manifestazione m=callGet(pathManifestazione+request.getNome(),null,null, Manifestazione.class);
//        if(m!=null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Manifestazione già esistente!");
        return callPost(pathManifestazione+"add/", null, request, ManifestazioneDTOResponse.class);
    }

    public List<LuogoDtoResponse> findAllLuogo(){
        return callGetForList(pathLuogo+"find/all", null, null, LuogoDtoResponse[].class);
    }
    
    public EventoDTOResponse addEvento(AddEventoRequest request) {
    	Manifestazione m = callGet(pathManifestazione+request.getIdManifestazione(),null,null,Manifestazione.class);
    	if(m==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Manifestazione insesistente");
    	Luogo l = callGet(pathLuogo+"findById/"+request.getIdLuogo(),null,null,Luogo.class);
    	if(l==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Luogo insesistente");
    	return callPost(pathEvento+"salva",null,request,EventoDTOResponse.class);
    }
    
    public EventoDTOResponse deleteEvento(long idEvento) {
    	return callPost(pathEvento+idEvento,null,null,EventoDTOResponse.class);
    }
    
    public VisualizzaEventoManifestazioneDTOResponse visualizzaEventiOrganizzati(long idManifestazione) {
    	Manifestazione m = callGet(pathManifestazione+idManifestazione, null, null, Manifestazione.class);
    	if(m==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Manifestazione inesistente");
    	Utente u=callGet(pathUtente+"findByManifestazioneId"+m.getUtente().getId(),null, null, Utente.class);
        if (u==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Utente non esistente");
        List<Evento> lista = callGetForList("/trovaEventiDiManifestazione"+m.getId(), null, null, Evento[].class);
        VisualizzaEventoManifestazioneDTOResponse vemDTO = new VisualizzaEventoManifestazioneDTOResponse();
        Map<String, String> map = new HashMap<>();
        List<Luogo> list = lista.stream().map(e->callGet(pathLuogo+"/findById"+e.getLuogo().getId(), null, null, Luogo.class)).toList();
        for(int i = 0; i<list.size(); i++) {
        	map.put(lista.get(i).getDescrizione(), list.get(i).getRiga1());
        }
        vemDTO.setNomeManifestazione(m.getNome());
        vemDTO.setNomeOrganizzatore(m.getUtente().getNome());
        return vemDTO;
    }
    
}
