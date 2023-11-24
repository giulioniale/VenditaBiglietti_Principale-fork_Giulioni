package it.dedagroup.venditabiglietti.principal.facade;

import it.dedagroup.venditabiglietti.principal.dto.request.*;
import it.dedagroup.venditabiglietti.principal.dto.response.*;
import it.dedagroup.venditabiglietti.principal.mapper.*;
import it.dedagroup.venditabiglietti.principal.model.*;
import it.dedagroup.venditabiglietti.principal.service.BigliettoServiceDef;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;
import it.dedagroup.venditabiglietti.principal.service.ManifestazioneServiceDef;
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
    private final BigliettoServiceDef bigliettoServiceDef;
    private final ManifestazioneServiceDef manifestazioneServiceDef;

    private final BigliettiMapper bigliettiMapper;
    private final ManifestazioneMapper manifestazioneMapper;
    private final EventoMapper eventoMapper;
    private final LuogoMapper luogoMapper;
    private final PrezzoSettoreEventoMapper prezzoSettoreEventoMapper;
    public final String EVENTO_PATH = "http://localhost:8081/evento";
    public final String CATEGORIA_PATH = "http://localhost:8082/categoria";
    public final String SETTORE_PATH = "http://localhost:8083/settore";
    public final String MANIFESTAZIONE_PATH = "http://localhost:8084/manifestazione";
    public final String PREZZO_SETTORE_EVENTO_PATH = "http://localhost:8086/prezzoSettoreEvento";

    public final String LUOGO_PATH = "http://localhost:8088/biglietto";

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
        //TODO Mi riprendo l'utente e controllo il ruolo
        Manifestazione m = callGet(MANIFESTAZIONE_PATH + idManifestazione, null, null, Manifestazione.class);
        if (m == null) throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Manifestazione inesistente");
        //TODO Implementare il findById da utenteServiceDef
        Utente u = callGet("findById" + m.getUtente().getId(), null, null, Utente.class);
        if (u == null) throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Utente non esistente");
        List<Evento> lista = callGetForList("/trovaEventiDiManifestazione" + m.getId(), null, null, Evento[].class);
        VisualizzaEventoManifestazioneDTOResponse vemDTO = new VisualizzaEventoManifestazioneDTOResponse();
        Map<String, String> map = new HashMap<>();
        List<Luogo> list = lista.stream().map(e -> callGet(LUOGO_PATH + "/findById" + e.getLuogo().getId(), null, null, Luogo.class)).toList();
        for (int i = 0; i < list.size(); i++) {
            map.put(lista.get(i).getDescrizione(), list.get(i).getRiga1());
        }
        vemDTO.setNomeManifestazione(m.getNome());
        vemDTO.setNomeOrganizzatore(m.getUtente().getNome());
        vemDTO.setEventiManifestazione(map);
        return vemDTO;
    }

    //TODO sistemare l'implementazione delle statistiche dei biglietti per la manifestazione
    public StatisticheManifestazioneDTOResponse statisticheBigliettiPerManifestazione(long id_manifestazione, Utente u) {
        if (!u.getRuolo().equals(Ruolo.VENDITORE))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Errore l'utente non ha i permessi");
        ManifestazioneMicroDTO microDTO = callGet(MANIFESTAZIONE_PATH + "/find/id/" + id_manifestazione, null, null, ManifestazioneMicroDTO.class);
        if (u.getId() != microDTO.getIdUtente()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Errore inserire una manifestazione organizzata da: "+u.getEmail());
        Manifestazione manifestazione = manifestazioneMapper.toManifestazione(microDTO, u);
        List<EventoMicroDTO> eventiManifestazione = callGetForList(EVENTO_PATH + "/find/all/microDTO/id" + microDTO.getId(), null, null, EventoMicroDTO[].class);
        if (eventiManifestazione.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun evento presente nella manifestazione: "+manifestazione.getNome());
        List<Long> idLuoghi = eventiManifestazione.stream().map(EventoMicroDTO::getIdLuogo).toList();
        List<LuogoMicroDTO> luoghiMicroDTO = callPostForList(LUOGO_PATH+"/findAllByIds", null, idLuoghi, LuogoMicroDTO[].class);
        if (luoghiMicroDTO.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun luogo assegnato ai singoli eventi");
        List<SettoreMicroDTO> settoreMicroDTO = callPostForList(SETTORE_PATH+"/findAllByListIdLuogo", null, idLuoghi, SettoreMicroDTO[].class);
        if (settoreMicroDTO.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun luogo assegnato ai singoli settori");
        List<Long> idsEventi = eventiManifestazione.stream().map(EventoMicroDTO::getId).toList();
        List<PrezzoSettoreEventoMicroDTO> psePerEventi = callPostForList(PREZZO_SETTORE_EVENTO_PATH+"/prezzi-settore-evento/ids-evento", null, idsEventi, PrezzoSettoreEventoMicroDTO[].class);
        if (psePerEventi.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun evento assegnato ai singoli prezzoSettoreEvento");
        List<Luogo> luoghi = luogoMapper.toLuogoList(luoghiMicroDTO, settoreMicroDTO);
        List<Evento> eventi = eventoMapper.toEventoList(eventiManifestazione, manifestazione, luoghi, psePerEventi);
        List<Long> idsPrezzoSettoreEvento = psePerEventi.stream().map(PrezzoSettoreEventoMicroDTO::getId).toList();
        List<BigliettoMicroDTO> bigliettoMicroDTO = bigliettoServiceDef.findAllByIdPrezzoSettoreEventoIn(idsPrezzoSettoreEvento);
        bigliettiMapper.toBigliettoList(bigliettoMicroDTO, eventi, "");
        return calcolaStatistiche(manifestazione);
    }

    private StatisticheManifestazioneDTOResponse calcolaStatistiche(Manifestazione m) {
        StatisticheManifestazioneDTOResponse response = new StatisticheManifestazioneDTOResponse();
        response.setNomeManifestazione(m.getNome());
        response.setProfittoEventiDellaManifestazione(m.getEventi().stream().map(this::calcolaSingoloEvento).toList());
        return response;
    }

    public DatiEventiDTOResponse statisticheBigliettiPerEvento(long id_evento, Utente u) {
        if (!u.getRuolo().equals(Ruolo.VENDITORE))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Errore l'utente non ha i permessi");
        EventoMicroDTO eventoDTO = callGet(EVENTO_PATH+"/find/id/"+id_evento, null, null, EventoMicroDTO.class);
        ManifestazioneMicroDTO manifestazioneDTO = callGet(MANIFESTAZIONE_PATH+"/find/id/"+eventoDTO.getIdManifestazione(), null, null, ManifestazioneMicroDTO.class);
        if (manifestazioneDTO.getIdUtente() != u.getId())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Errore inserire un id evento corrispondente alla manifestazione: " + manifestazioneDTO.getNome());
        LuogoMicroDTO luogoDTO = callGet(LUOGO_PATH+"/findById/"+eventoDTO.getIdLuogo(), null, null, LuogoMicroDTO.class);
        List<SettoreMicroDTO> settoriDTO = callGetForList(SETTORE_PATH+"/findByIdLuogo?idLuogo="+luogoDTO.getId(), null, null, SettoreMicroDTO[].class);
        if (settoriDTO.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun settore trovato con id luogo: "+luogoDTO.getId());
        List<PrezzoSettoreEventoMicroDTO> pseDTO = callGetForList(PREZZO_SETTORE_EVENTO_PATH+"/prezzi-settore-evento/ids-evento", null, List.of(eventoDTO.getId()), PrezzoSettoreEventoMicroDTO[].class);
        if (pseDTO.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun prezzo settore eventi trovato tramite id evento: "+eventoDTO.getId());
        Luogo luogo = luogoMapper.toLuogo(luogoDTO, settoriDTO);
        Manifestazione manifestazione = manifestazioneMapper.toManifestazione(manifestazioneDTO, u);
        Evento evento = eventoMapper.toEvento(eventoDTO, manifestazione, luogo, pseDTO);
        return calcolaSingoloEvento(evento);
    }

    private DatiEventiDTOResponse calcolaSingoloEvento(Evento e) {
        DatiEventiDTOResponse d = new DatiEventiDTOResponse();
        d.setDescrizione(e.getDescrizione());
        d.setViaLuogo(e.getLuogo().getRiga1());
        d.setSettoriPerLuogo(e.getPrezziSettoreEvento().stream().map(this::calcolaSettoreSingoloLuogo).toList());
        return d;
    }

    private SettoriPerSingoloLuogo calcolaSettoreSingoloLuogo(PrezzoSettoreEvento pse) {
        SettoriPerSingoloLuogo s = new SettoriPerSingoloLuogo();
        s.setBigliettiTotali(pse.getSettore().getCapienza());
        s.setSettore(pse.getSettore().getNome());
        s.setPrezzoAttuale(pse.getPrezzo());
        s.setBigliettiComprati(pse.getBiglietti().size());
        s.setGuadagnoBiglietti(pse.getBiglietti().stream().mapToDouble(Biglietto::getPrezzo).sum());
        return s;
    }

    public PseDTOResponse setPrezzoSettoreEvento(PrezzoSettoreEventoDTORequest request, Utente utente) {
        if (!utente.getRuolo().equals(Ruolo.VENDITORE)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Errore, l'utente non ha i permessi");
        EventoMicroDTO eventoDTO = callPost(EVENTO_PATH+"/find/id" + request.getIdEvento(), null, null, EventoMicroDTO.class);
        ManifestazioneMicroDTO manifestazioneDTO = callPost(MANIFESTAZIONE_PATH+"/find/id"+eventoDTO.getIdManifestazione(), null, null, ManifestazioneMicroDTO.class);
        if (manifestazioneDTO.getIdUtente() != utente.getId()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Errore annullata la modifica del prezzo settore evento");
        SettoreMicroDTO settoreDTO = callGet(SETTORE_PATH + "/find/id" + request.getIdSettore(), null, null, SettoreMicroDTO.class);
        PrezzoSettoreEventoMicroDTO pseDTO = callPost(PREZZO_SETTORE_EVENTO_PATH + "/find/id" + request.getIdPse(), null, null, PrezzoSettoreEventoMicroDTO.class);

        callPost(PREZZO_SETTORE_EVENTO_PATH + "/prezzi-settore-evento/modifica-evento?idPse="+ pseDTO.getId() + "&idEvento="+ eventoDTO.getId(),null,null,Void.class);
        callPost(PREZZO_SETTORE_EVENTO_PATH + "/prezzi-settore-evento/modifica-settore?idPse=" + pseDTO.getId() + "&idSettore=" + settoreDTO.getId(), null, null, Void.class);
        callGet(PREZZO_SETTORE_EVENTO_PATH + "/prezzi-settore-evento/modifica-prezzo", null, request, SettoreMicroDTO.class);

        PrezzoSettoreEventoMicroDTO pseDTOmodificato = callPost(PREZZO_SETTORE_EVENTO_PATH+"/find/id"+pseDTO.getId(),null,null, PrezzoSettoreEventoMicroDTO.class);
        return prezzoSettoreEventoMapper.toPseDTOResponse(pseDTOmodificato,eventoDTO.getDescrizione(),settoreDTO.getNome());
    }
}
