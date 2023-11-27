package it.dedagroup.venditabiglietti.principal.facade;

import it.dedagroup.venditabiglietti.principal.dto.request.*;
import it.dedagroup.venditabiglietti.principal.dto.response.*;
import it.dedagroup.venditabiglietti.principal.dto.response.EventiFiltratiDTOResponse;
import it.dedagroup.venditabiglietti.principal.dto.response.EventoDTOResponse;
import it.dedagroup.venditabiglietti.principal.mapper.EventoMapper;
import it.dedagroup.venditabiglietti.principal.mapper.UtenteMapper;
import it.dedagroup.venditabiglietti.principal.model.*;
import it.dedagroup.venditabiglietti.principal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneralFacade implements GeneralCallService {

    @Autowired
    ManifestazioneServiceDef mServ;
    @Autowired
    UtenteServiceDef uServ;
    @Autowired
    EventoServiceDef eServ;
    @Autowired
    LuogoServiceDef lServ;
    @Autowired
    EventoMapper evMap;
    @Autowired
    UtenteMapper uMap;
    @Autowired
    PrezzoSettoreEventoServiceDef pseServ;
    @Autowired
    BigliettoServiceDef bServ;
    @Autowired
    SettoreServiceDef settServ;

    private final String pathEvento = "http://localhost:8081/evento";
    private final String pathLuogo = "http://localhost:8088/luogo";
    private final String pathCategoria = "http://localhost:8082/categoria";
    private final String pathManifestazione = "http://localhost:8084/manifestazione";

    public void registrazioneCliente(AggiungiUtenteDTORequest req) {
        Utente uNew = uMap.toUtenteCliente(req);
        uServ.aggiungiUtente(uNew);
    }

    // TODO valutare sui cicli nestati se fare le lambda
    public List<MostraEventiFuturiDTOResponse> trovaEventiFuturiConBiglietti() {
        List<EventoMicroDTO> eventiFuturi = eServ.trovaEventiFuturi();
        List<MostraEventiFuturiDTOResponse> listaEventiFuturiResponse = new ArrayList<>();
        for (EventoMicroDTO eventoDto : eventiFuturi) {
            MostraEventiFuturiDTOResponse response = new MostraEventiFuturiDTOResponse();
            List<PrezzoSettoreEvento> listaPsePerEvento = pseServ.findAllByIdEvento(eventoDto.getId());
            List<SettoreMicroDTO> listaSettoriPerEvento = settServ.findAllByIdLuogo(lServ.findLuogoById(eventoDto.getIdLuogo()).getId());
            int capienzaSettore = 0;
            int bigliettiVenduti = 0;
            response = evMap.mostraEventiFuturiResponseBuilder(eventoDto,
                    lServ.findLuogoById(eventoDto.getIdLuogo()),
                    mServ.findById(eventoDto.getIdManifestazione()));
            capienzaSettore = listaSettoriPerEvento.stream().mapToInt(SettoreMicroDTO::getCapienza).sum();
            bigliettiVenduti = listaPsePerEvento.stream().mapToInt(s -> bServ.countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNull(s.getId())).sum();
            response.setPostiDisponibili(capienzaSettore - bigliettiVenduti);
            listaEventiFuturiResponse.add(response);
        }
        return listaEventiFuturiResponse;

    }

    public String login(LoginDTORequest request) {
        return uServ.login(request);
    }

    public Utente findByEmail(String email) {
        Utente u = uServ.findByEmail(email);
        if (u == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Nessun utente con email " + email);
        }
        return u;
    }

    public Utente findById(long id) {
        return uServ.findById(id);
    }

    public List<Utente> findByAllId(List<Long> ids) {
        return uServ.findByAllId(ids);
    }

    public List<EventiFiltratiDTOResponse> eventiFiltrati(EventiFiltratiDTORequest request) {
        List<Evento> eventiCriteria = callPostForList(pathEvento + "/filtraEventi", request.getRequestEventi(), Evento[].class);
        List<Luogo> luoghiCriteria = callPostForList(pathLuogo + "/filtroLuogo", request.getRequestLuoghi(), Luogo[].class);
        List<Categoria> categorieCriteria = callPostForList(pathCategoria + "/filtraCategorie", request.getRequestCategoria(), Categoria[].class);
        List<Manifestazione> manifestazioneCriteria = callPostForList(pathManifestazione + "/filtraManifestazioni", request.getRequestManifestazione(), Manifestazione[].class);

        // Creare una lista di EventiFiltratiDTOResponse
        List<EventiFiltratiDTOResponse> eventiFiltrati = new ArrayList<>();

        // Creare un EventiFiltratiDTOResponse parziale
        EventiFiltratiDTOResponse eventiFiltratiDTOResponse = new EventiFiltratiDTOResponse();

        // Chiamare la funzione ricorsiva con gli indici iniziali
        filtraEventiFiltratiRicorsivo(eventiCriteria, luoghiCriteria, categorieCriteria, manifestazioneCriteria, 0, 0, 0, 0, eventiFiltrati, eventiFiltratiDTOResponse);

        // Restituire la lista di EventiFiltratiDTOResponse
        return eventiFiltrati;
    }

    // Funzione ricorsiva che esplora tutte le combinazioni possibili delle 4 request
    public void filtraEventiFiltratiRicorsivo(List<Evento> eventiCriteria, List<Luogo> luoghiCriteria, List<Categoria> categorieCriteria, List<Manifestazione> manifestazioneCriteria, int indiceEvento, int indiceLuogo, int indiceCategoria, int indiceManifestazione, List<EventiFiltratiDTOResponse> eventiFiltrati, EventiFiltratiDTOResponse eventiFiltratiDTOResponse) {
        // Se il EventiFiltratiDTOResponse parziale è completo, lo aggiunge alla lista finale
        if (eventiFiltratiDTOResponse.getNomeManifestazione() != null && eventiFiltratiDTOResponse.getNomeCategoria() != null && eventiFiltratiDTOResponse.getDescrizioneEvento() != null && eventiFiltratiDTOResponse.getDataEvento() != null && eventiFiltratiDTOResponse.getOraEvento() != null && eventiFiltratiDTOResponse.getComune() != null && eventiFiltratiDTOResponse.getProvincia() != null) {
            eventiFiltrati.add(eventiFiltratiDTOResponse);
            return;
        }

        // Itera su ogni lista di entità e aggiunge l'elemento corrispondente al EventiFiltratiDTOResponse parziale
        for (int i = indiceEvento; i < eventiCriteria.size(); i++) {
            Evento evento = eventiCriteria.get(i);
            // Controlla che gli id non siano nulli o zero
            if (evento.getManifestazione().getId() != 0 && evento.getManifestazione().getCategoria().getId() != 0) {
                // Trova la manifestazione e la categoria corrispondenti agli id
                Manifestazione manifestazione = manifestazioneCriteria.stream().filter(m -> m.getId()==(evento.getManifestazione().getId())).findFirst().orElse(null);
                Categoria categoria = categorieCriteria.stream().filter(c -> c.getId()==(evento.getManifestazione().getCategoria().getId())).findFirst().orElse(null);
                // Se la manifestazione e la categoria esistono, aggiunge i loro nomi al EventiFiltratiDTOResponse parziale
                if (manifestazione != null && categoria != null) {
                    eventiFiltratiDTOResponse.setNomeManifestazione(manifestazione.getNome());
                    eventiFiltratiDTOResponse.setNomeCategoria(categoria.getNome());
                }
            }
            eventiFiltratiDTOResponse.setDescrizioneEvento(evento.getDescrizione());
            eventiFiltratiDTOResponse.setDataEvento(evento.getData());
            eventiFiltratiDTOResponse.setOraEvento(evento.getOra());
            // Chiama se stessa con l'indice incrementato e il EventiFiltratiDTOResponse parziale aggiornato
            filtraEventiFiltratiRicorsivo(eventiCriteria, luoghiCriteria, categorieCriteria, manifestazioneCriteria, i + 1, indiceLuogo, indiceCategoria, indiceManifestazione, eventiFiltrati, eventiFiltratiDTOResponse);
        }

        for (int j = indiceLuogo; j < luoghiCriteria.size(); j++) {
            Luogo luogo = luoghiCriteria.get(j);
            eventiFiltratiDTOResponse.setComune(luogo.getComune());
            eventiFiltratiDTOResponse.setProvincia(luogo.getProvincia());
            // Chiama se stessa con l'indice incrementato e il EventiFiltratiDTOResponse parziale aggiornato
            filtraEventiFiltratiRicorsivo(eventiCriteria, luoghiCriteria, categorieCriteria, manifestazioneCriteria, indiceEvento, j + 1, indiceCategoria, indiceManifestazione, eventiFiltrati, eventiFiltratiDTOResponse);
        }

        for (int k = indiceCategoria; k < categorieCriteria.size(); k++) {
            Categoria categoria = categorieCriteria.get(k);
            // Controlla che il nome della categoria non sia nullo
            if (categoria.getNome() != null) {
                eventiFiltratiDTOResponse.setNomeCategoria(categoria.getNome());
            }
            // Chiama se stessa con l'indice incrementato e il EventiFiltratiDTOResponse parziale aggiornato
            filtraEventiFiltratiRicorsivo(eventiCriteria, luoghiCriteria, categorieCriteria, manifestazioneCriteria, indiceEvento, indiceLuogo, k + 1, indiceManifestazione, eventiFiltrati, eventiFiltratiDTOResponse);
        }

        for (int l = indiceManifestazione; l < manifestazioneCriteria.size(); l++) {
            Manifestazione manifestazione = manifestazioneCriteria.get(l);
            // Controlla che il nome della manifestazione non sia nullo
            if (manifestazione.getNome() != null) {
                eventiFiltratiDTOResponse.setNomeManifestazione(manifestazione.getNome());
            }
            // Chiama se stessa con l'indice incrementato e il EventiFiltratiDTOResponse parziale aggiornato
            filtraEventiFiltratiRicorsivo(eventiCriteria, luoghiCriteria, categorieCriteria, manifestazioneCriteria, indiceEvento, indiceLuogo, indiceCategoria, l + 1, eventiFiltrati, eventiFiltratiDTOResponse);
        }
    }
}