package it.dedagroup.venditabiglietti.principal.facade;

import it.dedagroup.venditabiglietti.principal.dto.request.*;
import it.dedagroup.venditabiglietti.principal.dto.response.*;
import it.dedagroup.venditabiglietti.principal.mapper.BigliettiMapper;
import it.dedagroup.venditabiglietti.principal.model.*;
import it.dedagroup.venditabiglietti.principal.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static it.dedagroup.venditabiglietti.principal.util.BigliettoUtilPath.*;
import java.util.ArrayList;
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
    private final BigliettoServiceDef bigliettoServiceDef;
    private final CategoriaServiceDef categoriaServiceDef;
    private final LuogoServiceDef luogoServiceDef;

    public ManifestazioneDTOResponse addManifestazione(AddManifestazioneDTORequest request){
        Utente u= utenteServiceDef.findById(request.getIdUtente());
        if (u==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Utente non esistente!");
        Categoria c=categoriaServiceDef.findById(request.getIdCategoria());
        if(c==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Categoria non esistente!");
        Manifestazione m=manifestazioneServiceDef.findByNome(request.getNome());
        if(m!=null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Manifestazione già esistente!");

        return manifestazioneServiceDef.save(request);
    }

    public List<LuogoDtoResponse> findAllLuogo(){
        return luogoServiceDef.findAll();
    }

    public EventoDTOResponse addEvento(AddEventoRequest request) {
    	Manifestazione m=manifestazioneServiceDef.findById(request.getIdManifestazione()) ;
    	if(m==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Manifestazione insesistente");
    	Luogo l = luogoServiceDef.findLuogoById(request.getIdLuogo());
    	if(l==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Luogo insesistente");
    	return eventoServiceDef.save(request);
    }

    public EventoDTOResponse deleteEvento(long idEvento) {
        return eventoServiceDef;
    }

    public VisualizzaEventoManifestazioneDTOResponse visualizzaEventiOrganizzati(long idManifestazione) {
        //TODO rinominare le liste e il map
        //TODO cambiare i ritorni dei .class in MicroDTO.class
        Manifestazione m ;
        if(m==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Manifestazione inesistente");
        //TODO Implementare il findById da utenteServiceDef
        Utente u= m.getUtente();
        if (u==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Utente non esistente");
        List<Evento> lista = m.getEventi();
        VisualizzaEventoManifestazioneDTOResponse vemDTO = new VisualizzaEventoManifestazioneDTOResponse();
        Map<String, String> map = new HashMap<>();
        List<Luogo> list = lista.stream().map(Evento::getLuogo).toList();
        for(int i = 0; i<list.size(); i++) {
            map.put(lista.get(i).getDescrizione(), list.get(i).getRiga1());
        }
        vemDTO.setNomeManifestazione(m.getNome());
        vemDTO.setNomeOrganizzatore(m.getUtente().getNome());
        vemDTO.setEventiManifestazione(map);
        return vemDTO;
    }

    public StatisticheBigliettiDTOResponse statisticheBigliettiPerEventoSettorePrezzoSettoreEvento(EventoSettorePseDTORequest request){
        try {
            EventoMicroDTO evento = callGet(EVENTO_PATH+"/find/descrizione/"+request.getDescrizioneEvento(),null,null, EventoMicroDTO.class);
            ManifestazioneMicroDTO manifestazioneMicroDTO = callGet(MANIFESTAZIONE_PATH+"/find/id/"+evento.getIdManifestazione(),null,null,ManifestazioneMicroDTO.class);
            LuogoMicroDTO luogo = callGet(LUOGO_PATH+"/find/id/"+evento.getIdLuogo(),null,null, LuogoMicroDTO.class);
            PrezzoSettoreEventoMicroDTO prezzoSettoreEvento = callGet(PREZZO_SETTORE_EVENTO_PATH+"/find/id/"+request.getIdPrezzoSettoreEvento(),null,null, PrezzoSettoreEventoMicroDTO.class);
            SettoreMicroDTO settore = callGet(SETTORE_PATH+"/find/nome/"+ prezzoSettoreEvento.getIdSettore(),null,null, SettoreMicroDTO.class);
            int nBigliettiComprati = bigliettoServiceDef.countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNull(request.getIdPrezzoSettoreEvento());
            return bigliettiMapper.createStatisticheRenditaBigliettiDTOResponse(
                    manifestazioneMicroDTO.getNome(),
                    evento.getDescrizione(),
                    luogo.getRiga1(),
                    settore.getNome(),
                    nBigliettiComprati,
                    settore.getPosti(),
                    prezzoSettoreEvento.getPrezzo(), request.getPrezzoBiglietto(), nBigliettiComprati
            );
        } catch (ResponseStatusException e){
            if (e.getStatusCode().is4xxClientError()){
                throw e;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Errore nel calcolare la rendita dell'evento");
    }
    //TODO sistemare l'implementazione delle statistiche dei biglietti per la manifestazione
    public StatisticheManifestazioneDTOResponse statisticheBigliettiPerManifestazione(ManifestazioneStatisticheDTORequest request){
        //Mi riprendo la manifestazione tramite id [x]
        ManifestazioneMicroDTO manifestazione = callGet(MANIFESTAZIONE_PATH+"/find/id/"+request.getIdManifestazione(),null,null,ManifestazioneMicroDTO.class);
        //Mi riprendo ogni evento della manifestazione (tramite l'id della manifestazione associata a essi) [x]
        List<EventoMicroDTO> eventiManifestazione = callGetForList(EVENTO_PATH+"/find/all/manifestazione/id"+manifestazione.getId(),null,null,EventoMicroDTO[].class);
        //Mi ritrovo i vari luoghi a seconda dell'id del singolo evento [x]
        //Mi prendo tutti i pse associati all'id dell'evento
        List<LuogoMicroDTO> luoghiEvento = new ArrayList<>();
        List<PrezzoSettoreEventoMicroDTO> psePerEventi = new ArrayList<>();

        eventiManifestazione.forEach(evento -> {
            luoghiEvento.add(callGet(LUOGO_PATH+"/find/id/"+evento.getIdLuogo(),null,null,LuogoMicroDTO.class));
            psePerEventi.add(callGet(PREZZO_SETTORE_EVENTO_PATH+"/find/evento/id"+evento.getId(),null,null, PrezzoSettoreEventoMicroDTO.class));
        });
        //bigliettiPerPSE.add(callGet(BIGLIETTO_PATH+"/find/prezzo-settore-id/"+prezzoSettoreEvento.getId(),null,null,BigliettoMicroDTO.class));
        //Mi riprendo la lista di settori tramite l'id del singolo evento [x]
        //List<BigliettoMicroDTO> bigliettiPerPSE = new ArrayList<>();
        Map <Long, List<BigliettoMicroDTO>> bigliettiPerPSE = new HashMap<>();
        List<SettoreMicroDTO> settorePerPSE = new ArrayList<>();
        psePerEventi.forEach(prezzoSettoreEvento -> {
            bigliettiPerPSE.put(prezzoSettoreEvento.getIdEvento(), bigliettoServiceDef.findAllByIdPrezzoSettoreEventoOrderByPrezzoAsc(prezzoSettoreEvento.getIdEvento()));
            settorePerPSE.add(callGet(SETTORE_PATH+"/find/id/"+prezzoSettoreEvento.getIdSettore(),null,null,SettoreMicroDTO.class));
        });
        return bigliettiMapper.createStatisticheManifestazioneDTOResponse(manifestazione.getNome(), eventiManifestazione, luoghiEvento, psePerEventi, bigliettiPerPSE, settorePerPSE);
    }
    //TODO Decidere se far ritornare il PrezzoSettoreEvento con il prezzo modificato
    public void setPrezzoSettoreEvento(PrezzoSettoreEventoDTORequest request){
        callGet(SETTORE_PATH+"/prezzi-settore-evento/modifica-prezzo",null,request,SettoreMicroDTO.class);
    }
}
