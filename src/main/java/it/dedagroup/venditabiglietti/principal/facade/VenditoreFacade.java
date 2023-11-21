package it.dedagroup.venditabiglietti.principal.facade;

import it.dedagroup.venditabiglietti.principal.dto.request.*;
import it.dedagroup.venditabiglietti.principal.dto.response.*;
import it.dedagroup.venditabiglietti.principal.mapper.BigliettiMapper;
import it.dedagroup.venditabiglietti.principal.mapper.EventoMapper;
import it.dedagroup.venditabiglietti.principal.mapper.LuogoMapper;
import it.dedagroup.venditabiglietti.principal.mapper.ManifestazioneMapper;
import it.dedagroup.venditabiglietti.principal.model.*;
import it.dedagroup.venditabiglietti.principal.service.BigliettoServiceDef;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;
import it.dedagroup.venditabiglietti.principal.service.UtenteServiceDef;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    private final ManifestazioneMapper manifestazioneMapper;
    private final EventoMapper eventoMapper;
    private final LuogoMapper luogoMapper;
    public final String EVENTO_PATH = "http://localhost:8081/evento";
    public final String CATEGORIA_PATH = "http://localhost:8082/categoria";
    public final String SETTORE_PATH = "http://localhost:8083/settore";
    public final String MANIFESTAZIONE_PATH = "http://localhost:8084/manifestazione";
    public final String PREZZO_SETTORE_EVENTO_PATH = "http://localhost:8086/prezzoSettoreEvento";

    public final String LUOGO_PATH = "http://localhost:8088/biglietto";
    //TODO Aggiungere il controllo del ruolo dell'utente
    public ManifestazioneDTOResponse addManifestazione(AddManifestazioneDTORequest request){
        Utente u= utenteServiceDef.findById(request.getIdUtente());
        if (u==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Utente non esistente!");
        Categoria c=callGet(CATEGORIA_PATH+"trova/"+request.getIdCategoria(), null, null, Categoria.class);
        if(c==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Categoria non esistente!");
//        Manifestazione m=callGetMANIFESTAZIONE_PATH+request.getNome(),null,null, Manifestazione.class);
//        if(m!=null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Manifestazione già esistente!");
        return callPost(MANIFESTAZIONE_PATH+"add/", null, request, ManifestazioneDTOResponse.class);
    }
    //TODO Aggiungere il controllo del ruolo dell'utente
    public List<LuogoDtoResponse> findAllLuogo(){
        return callGetForList(LUOGO_PATH+"find/all", null, null, LuogoDtoResponse[].class);
    }
    //TODO Aggiungere il controllo del ruolo dell'utente
    //TODO Aggiungere l'id della manifestazione, evento è presente se manifestazione è presente
    public EventoDTOResponse addEvento(AddEventoRequest request) {
    	Manifestazione m = callGet(MANIFESTAZIONE_PATH+request.getIdManifestazione(),null,null,Manifestazione.class);
    	if(m==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Manifestazione insesistente");
    	Luogo l = callGet(LUOGO_PATH+"findById/"+request.getIdLuogo(),null,null,Luogo.class);
    	if(l==null) throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Luogo insesistente");
    	return callPost(EVENTO_PATH+"salva",null,request,EventoDTOResponse.class);
    }
    //TODO Aggiungere il controllo del ruolo dell'utente
    //TODO Aggiungere l'id della manifestazione, evento è presente se manifestazione è presente
    public EventoDTOResponse deleteEvento(long idEvento) {
        return callPost(EVENTO_PATH+idEvento,null,null,EventoDTOResponse.class);
    }
    //TODO Aggiungere il controllo del ruolo dell'utente
    public VisualizzaEventoManifestazioneDTOResponse visualizzaEventiOrganizzati(long idManifestazione) {
        //TODO rinominare le liste e il map
        //TODO cambiare i ritorni dei .class in MicroDTO.class
        //TODO Mi riprendo l'utente e controllo il ruolo
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
        vemDTO.setEventiManifestazione(map);
        return vemDTO;
    }
    //TODO Possibile rimozione del metodo statisticheBigliettiPerEventoSettorePrezzoSettoreEvento
    /*
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
     */
    //TODO sistemare l'implementazione delle statistiche dei biglietti per la manifestazione
    //TODO Aggiungere il controllo del ruolo dell'utente

    public StatisticheManifestazioneDTOResponse statisticheBigliettiPerManifestazione(ManifestazioneStatisticheDTORequest request,Utente u){
        if (!u.getRuolo().equals(Ruolo.VENDITORE))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Errore l'utente non ha i permessi");
        ManifestazioneMicroDTO microDTO = callGet(MANIFESTAZIONE_PATH+"/find/id/"+request.getIdManifestazione(),null,null,ManifestazioneMicroDTO.class);
        //TODO segna l'errore
        if(u.getId()!=microDTO.getUtente_id())throw new ResponseStatusException(HttpStatus.FORBIDDEN,"dsifsd");
        Manifestazione manifestazione=manifestazioneMapper.toManifestazione(microDTO,u);
        List<EventoMicroDTO> eventiManifestazione = callGetForList(EVENTO_PATH+"/find/all/microDTO/id"+microDTO.getId(),null,null,EventoMicroDTO[].class);
        List<Long> idLuoghi=eventiManifestazione.stream().map(EventoMicroDTO::getIdLuogo).toList();
        //TODO popola
        List<LuogoMicroDTO> luoghiMicroDTO=callPostForList("","£","",null);
        List<SettoreMicroDTO> settoreMicroDTO = callPostForList("",null,"",null);
        List<PrezzoSettoreEventoMicroDTO> psePerEventi = callPostForList("","","",null);
        List<Luogo> luoghi=luogoMapper.toLuogoList(luoghiMicroDTO,settoreMicroDTO);
        List<Evento> eventi=eventoMapper.toEventoList(eventiManifestazione,manifestazione,luoghi,psePerEventi);
        List<BigliettoMicroDTO> bigliettoMicroDTO = callPostForList("","","",null);
        List<Biglietto> biglietti=bigliettiMapper.toBigliettoList(bigliettoMicroDTO,eventi,"");
        return calcolaStatistiche(manifestazione);
    }

    private StatisticheManifestazioneDTOResponse calcolaStatistiche(Manifestazione m){
        StatisticheManifestazioneDTOResponse response=new StatisticheManifestazioneDTOResponse();
        response.setNomeManifestazione(m.getNome());
        response.setProfittoEventiDellaManifestazione(m.getEventi().stream().map(this::calcolaSingoloEvento).toList());
        return response;
    }

    private DatiEventiDTOResponse calcolaSingoloEvento(Evento e){
        DatiEventiDTOResponse d=new DatiEventiDTOResponse();
        d.setDescrizione(e.getDescrizione());
        d.setViaLuogo(e.getLuogo().getRiga1());
        d.setSettoriPerLuogo(e.getPrezziSettoreEvento().stream().map(this::calcolaSettoreSingoloLuogo).toList());
        return d;
    }

    private SettoriPerSingoloLuogo calcolaSettoreSingoloLuogo(PrezzoSettoreEvento pse){
        SettoriPerSingoloLuogo s=new SettoriPerSingoloLuogo();
        s.setBigliettiTotali(pse.getSettore().getCapienza());
        s.setSettore(pse.getSettore().getNome());
        s.setPrezzoAttuale(pse.getPrezzo());
        s.setBigliettiComprati(pse.getBiglietti().size());
        s.setGuadagnoBiglietti(pse.getBiglietti().stream().mapToDouble(Biglietto::getPrezzo).sum());
        return s;
    }


    //TODO Aggiungere il controllo del ruolo dell'utente
    //TODO Decidere se far ritornare il PrezzoSettoreEvento con il prezzo modificato
    public void setPrezzoSettoreEvento(PrezzoSettoreEventoDTORequest request, Utente utente){
        try{
            if (!utente.getRuolo().equals(Ruolo.VENDITORE))throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Errore, l'utente non ha i permessi");
            callGet(SETTORE_PATH+"/prezzi-settore-evento/modifica-prezzo",null,request,SettoreMicroDTO.class);
            //"/prezzi-settore-evento/modifica-settore"
            //callGet()
            //TODO Cambio dell'id settore
            //"/prezzi-settore-evento/modifica-evento"
            //TODO Cambio dell'id evento
            //TODO Richiedere il cambio del prezzo
            //TODO Cambio del prezzo
            //"/prezzi-settore-evento/elimina-by-id"
            //TODO Cambio isCancellato
        } catch (ResponseStatusException e){
            throw e;
        }

    }
}
