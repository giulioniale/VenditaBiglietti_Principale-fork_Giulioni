package it.dedagroup.venditabiglietti.principal.facade;

import it.dedagroup.venditabiglietti.principal.DTO.request.AddManifestazioneDTORequest;
import it.dedagroup.venditabiglietti.principal.DTO.response.LuogoDtoResponse;
import it.dedagroup.venditabiglietti.principal.DTO.response.ManifestazioneDTOResponse;
import it.dedagroup.venditabiglietti.principal.model.Categoria;
import it.dedagroup.venditabiglietti.principal.model.Manifestazione;
import it.dedagroup.venditabiglietti.principal.model.Utente;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class VenditoreFacade implements GeneralCallService {

    private final String pathManifestazione="http://localhost:8084/manifestazione/";
    private final String pathUtente="http://localhost:8085/utente/";
    private final String pathCategoria="http://localhost:8082/categoria/";
    private final String pathLuogo="http://localhost:8088/luogo/";

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
}
