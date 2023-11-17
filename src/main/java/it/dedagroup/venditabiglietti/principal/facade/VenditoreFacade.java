package it.dedagroup.venditabiglietti.principal.facade;

import it.dedagroup.venditabiglietti.principal.dto.request.*;
import it.dedagroup.venditabiglietti.principal.dto.response.*;
import it.dedagroup.venditabiglietti.principal.mapper.BigliettiMapper;
import it.dedagroup.venditabiglietti.principal.model.*;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;
import it.dedagroup.venditabiglietti.principal.service.UtenteServiceDef;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class VenditoreFacade implements GeneralCallService{
    //TODO controllare su github i vari url dei microservizi
    //TODO discutere della creazione di un service layer dove utilizzare le chiamate ai microservizi
    private final BigliettiMapper bigliettiMapper;
    private final UtenteServiceDef utenteServiceDef;
    public final String EVENTO_PATH = "http://localhost:8081/evento";
    public final String CATEGORIA_PATH = "http://localhost:8082/categoria";
    public final String SETTORE_PATH = "http://localhost:8083/settore";
    public final String MANIFESTAZIONE_PATH = "http://localhost:8084/manifestazione";
    public final String PREZZO_SETTORE_EVENTO_PATH = "http://localhost:8086/prezzoSettoreEvento";
    public final String BIGLIETTO_PATH = "http://localhost:8087/biglietto";
    public final String LUOGO_PATH = "http://localhost:8088/biglietto";

    public ManifestazioneDTOResponse addManifestazione(AddManifestazioneDTORequest request){
        Utente u= utenteServiceDef.findById(request.getIdUtente());
        if (u==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Utente non esistente!");
        Categoria c=callGet(CATEGORIA_PATH+"trova/"+request.getIdCategoria(), null, null, Categoria.class);
        if(c==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Categoria non esistente!");
//        Manifestazione m=callGetMANIFESTAZIONE_PATH+request.getNome(),null,null, Manifestazione.class);
//        if(m!=null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Manifestazione già esistente!");
        return callPost(MANIFESTAZIONE_PATH+"add/", null, request, ManifestazioneDTOResponse.class);
    }

    public List<LuogoDtoResponse> findAllLuogo(){
        return callGetForList(LUOGO_PATH+"find/all", null, null, LuogoDtoResponse[].class);
    }

    public EventoDTOResponse addEvento(AddEventoRequest request) {
        Manifestazione m = callGet(MANIFESTAZIONE_PATH+request.getIdManufestazione(),null,null,Manifestazione.class);
        if(m==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Manifestazione insesistente");
        Luogo l = callGet(LUOGO_PATH+"findById/"+request.getIdLuogo(),null,null,Luogo.class);
        if(l==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Luogo insesistente");
        return callPost(EVENTO_PATH+"salva",null,request,EventoDTOResponse.class);
    }

    public EventoDTOResponse deleteEvento(long idEvento) {
        return callPost(EVENTO_PATH+idEvento,null,null,EventoDTOResponse.class);
    }

    public VisualizzaEventoManifestazioneDTOResponse visualizzaEventiOrganizzati(long idManifestazione) {
        //TODO rinominare le liste e il map
        //TODO cambiare i ritorni dei .class in MicroDTO.class
        Manifestazione m = callGet(MANIFESTAZIONE_PATH+idManifestazione, null, null, Manifestazione.class);
        if(m==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Manifestazione inesistente");
        //TODO Implementare il findById da utenteServiceDef
        Utente u= callGet("findById"+m.getUtente().getId(),null, null, Utente.class);
        if (u==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Utente non esistente");
        List<Evento> lista = callGetForList("/trovaEventiDiManifestazione"+m.getId(), null, null, Evento[].class);
        VisualizzaEventoManifestazioneDTOResponse vemDTO = new VisualizzaEventoManifestazioneDTOResponse();
        Map<String, String> map = new HashMap<>();
        List<Luogo> list = lista.stream().map(e->callGet(LUOGO_PATH+"/findById"+e.getLuogo().getId(), null, null, Luogo.class)).toList();
        for(int i = 0; i<list.size(); i++) {
            map.put(lista.get(i).getDescrizione(), list.get(i).getRiga1());
        }
        vemDTO.setNomeManifestazione(m.getNome());
        vemDTO.setNomeOrganizzatore(m.getUtente().getNome());
        return vemDTO;
    }

    public StatisticheBigliettiDTOResponse statisticheBigliettiPerEventoSettorePrezzoSettoreEvento(EventoSettorePseDTORequest request){
        try {
            EventoMicroDTO evento = callGet(EVENTO_PATH+"/find/descrizione/"+request.getDescrizioneEvento(),null,null, EventoMicroDTO.class);
            ManifestazioneMicroDTO manifestazioneMicroDTO = callGet(MANIFESTAZIONE_PATH+"/find/id/"+evento.getIdManifestazione(),null,null,ManifestazioneMicroDTO.class);
            LuogoMicroDTO luogo = callGet(LUOGO_PATH+"/find/id/"+evento.getIdLuogo(),null,null, LuogoMicroDTO.class);
            PrezzoSettoreEventoMicroDTO prezzoSettoreEvento = callGet(PREZZO_SETTORE_EVENTO_PATH+"/find/id/"+request.getIdPrezzoSettoreEvento(),null,null, PrezzoSettoreEventoMicroDTO.class);
            SettoreMicroDTO settore = callGet(SETTORE_PATH+"/find/nome/"+ prezzoSettoreEvento.getIdSettore(),null,null, SettoreMicroDTO.class);
            int nBigliettiComprati = quantitaBigliettiComprati(request.getIdPrezzoSettoreEvento());
            double guadagnoPerEventoESettore = guadagnoEventoSettore(prezzoSettoreEvento.getPrezzo(), request.getPrezzoBiglietto(), nBigliettiComprati);
            return bigliettiMapper.createStatisticheRenditaBigliettiDTOResponse(
                    manifestazioneMicroDTO.getNome(),
                    evento.getDescrizione(),
                    luogo.getRiga1(),
                    settore.getNome(),
                    nBigliettiComprati,
                    settore.getPosti(),
                    guadagnoPerEventoESettore);
        } catch (ResponseStatusException e){
            if (e.getStatusCode().is4xxClientError()){
                throw e;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Errore nel calcolare la rendita dell'evento");
    }

    public StatisticheManifestazioneDTOResponse statisticheBigliettiPerManifestazione(ManifestazioneStatisticheDTORequest request){
        ManifestazioneMicroDTO manifestazione = callGet(MANIFESTAZIONE_PATH+"/find/id/"+request.getIdManifestazione(),null,null,ManifestazioneMicroDTO.class);
        List<EventoMicroDTO> eventiManifestazione = callGetForList(EVENTO_PATH+"/find/all/manifestazione/id"+manifestazione.getId(),null,null,EventoMicroDTO[].class);
        List<LuogoMicroDTO> luoghiEvento =  eventiManifestazione.stream().map(evento -> callGet(LUOGO_PATH+"/find/id/"+evento.getIdLuogo(),null,null,LuogoMicroDTO.class)).toList();
        List<PrezzoSettoreEventoMicroDTO> psePerEventi = eventiManifestazione.stream().map(evento -> callGet(PREZZO_SETTORE_EVENTO_PATH+"/find/evento/id"+evento.getId(),null,null, PrezzoSettoreEventoMicroDTO.class)).toList();
        List<BigliettoMicroDTO> bigliettiPerPSE = psePerEventi.stream().map(e -> callGet(BIGLIETTO_PATH+"/find/prezzo-settore-id/"+e.getId(),null,null,BigliettoMicroDTO.class)).toList();
        List<SettoreMicroDTO> settorePerPSE = psePerEventi.stream().map(e -> callGet(SETTORE_PATH+"/find/id/"+e.getIdSettore(),null,null,SettoreMicroDTO.class)).toList();
        return bigliettiMapper.createStatisticheManifestazioneDTOResponse(
                manifestazione.getNome(),
                eventiManifestazione,
                luoghiEvento,
                psePerEventi,
                bigliettiPerPSE,
                settorePerPSE
        );
    }
    //TODO Decidere se far ritornare il PrezzoSettoreEvento con il prezzo modificato
    public void setPrezzoSettoreEvento(PrezzoSettoreEventoDTORequest request){
        callGet(SETTORE_PATH+"/prezzi-settore-evento/modifica-prezzo",null,request,SettoreMicroDTO.class);
    }

    public int quantitaBigliettiComprati(long id_prezzoSettoreEvento){
        return callGet(BIGLIETTO_PATH+"/count/data-acquisto/not-null/prezzo-settore-evento/id/"+id_prezzoSettoreEvento,null,null,Integer.class);
    }

    public double guadagnoEventoSettore(double prezzoSettoreEvento, double prezzoBiglietto, int nBigliettiComprati){
        return (prezzoSettoreEvento + prezzoBiglietto) * nBigliettiComprati;
    }
}
