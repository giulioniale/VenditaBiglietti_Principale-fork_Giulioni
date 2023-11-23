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
    //TODO sistemare l'implementazione delle statistiche dei biglietti per la manifestazione
    public StatisticheManifestazioneDTOResponse statisticheBigliettiPerManifestazione(ManifestazioneStatisticheDTORequest request,Utente u){
        if (!u.getRuolo().equals(Ruolo.VENDITORE))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Errore l'utente non ha i permessi");
        ManifestazioneMicroDTO microDTO = callGet(MANIFESTAZIONE_PATH+"/find/id/"+request.getIdManifestazione(),null,null,ManifestazioneMicroDTO.class);
        //TODO segna l'errore
        if(u.getId()!=microDTO.getUtente_id())throw new ResponseStatusException(HttpStatus.FORBIDDEN,"dsifsd");
        Manifestazione manifestazione=manifestazioneMapper.toManifestazione(microDTO,u);
        List<EventoMicroDTO> eventiManifestazione = callGetForList(EVENTO_PATH+"/find/all/microDTO/id"+microDTO.getId(),null,null,EventoMicroDTO[].class);
        List<Long> idLuoghi=eventiManifestazione.stream().map(EventoMicroDTO::getIdLuogo).toList();
        //TODO popola
        List<LuogoMicroDTO> luoghiMicroDTO=callPostForList("","£",idLuoghi,null);
        List<SettoreMicroDTO> settoreMicroDTO = callPostForList("",null,"",null);
        List<PrezzoSettoreEventoMicroDTO> psePerEventi = callPostForList("","","",null);
        List<Luogo> luoghi=luogoMapper.toLuogoList(luoghiMicroDTO,settoreMicroDTO);
        List<Evento> eventi=eventoMapper.toEventoList(eventiManifestazione,manifestazione,luoghi,psePerEventi);
        List<Long> idsPrezzoSettoreEvento = psePerEventi.stream().map(PrezzoSettoreEventoMicroDTO::getId).toList();
        List<BigliettoMicroDTO> bigliettoMicroDTO = bigliettoServiceDef.findAllByIdPrezzoSettoreEventoIn(idsPrezzoSettoreEvento);
        bigliettiMapper.toBigliettoList(bigliettoMicroDTO,eventi,"");
        return calcolaStatistiche(manifestazione);
    }

    private StatisticheManifestazioneDTOResponse calcolaStatistiche(Manifestazione m){
        StatisticheManifestazioneDTOResponse response=new StatisticheManifestazioneDTOResponse();
        response.setNomeManifestazione(m.getNome());
        response.setProfittoEventiDellaManifestazione(m.getEventi().stream().map(this::calcolaSingoloEvento).toList());
        return response;
    }

    public DatiEventiDTOResponse statisticheBigliettiPerEvento(long id_evento, Utente u){
        if (!u.getRuolo().equals(Ruolo.VENDITORE))throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Errore l'utente non ha i permessi");
        EventoMicroDTO eventoDTO = callGet("",null,null,EventoMicroDTO.class);
        ManifestazioneMicroDTO manifestazioneDTO = callGet("",null,null,ManifestazioneMicroDTO.class);
        LuogoMicroDTO luogoDTO = callGet("",null,null,LuogoMicroDTO.class);
        List<SettoreMicroDTO> settoriDTO = callGetForList("",null,null,SettoreMicroDTO[].class);
        List<PrezzoSettoreEventoMicroDTO> pseDTO = callGetForList("",null,null,PrezzoSettoreEventoMicroDTO[].class);
        Luogo luogo = luogoMapper.toLuogo(luogoDTO,settoriDTO);
        if (manifestazioneDTO.getUtente_id() != u.getId())throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Errore inserire un id evento corrispondente alla manifestazione: "+ manifestazioneDTO.getNome());
        Manifestazione manifestazione = manifestazioneMapper.toManifestazione(manifestazioneDTO, u);
        Evento evento = eventoMapper.toEvento(eventoDTO,manifestazione,luogo,pseDTO);
        return calcolaSingoloEvento(evento);
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
