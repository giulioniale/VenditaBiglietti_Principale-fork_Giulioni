package it.dedagroup.venditabiglietti.principal.mapper;

import it.dedagroup.venditabiglietti.principal.dto.response.EventoDTOResponse;
import it.dedagroup.venditabiglietti.principal.dto.response.EventoMicroDTO;
import it.dedagroup.venditabiglietti.principal.dto.response.LuogoMicroDTO;
import it.dedagroup.venditabiglietti.principal.dto.response.PrezzoSettoreEventoMicroDTO;
import it.dedagroup.venditabiglietti.principal.model.Evento;
import it.dedagroup.venditabiglietti.principal.model.Luogo;
import it.dedagroup.venditabiglietti.principal.model.Manifestazione;
import it.dedagroup.venditabiglietti.principal.model.PrezzoSettoreEvento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventoMapper {

    @Autowired
    PrezzoSettoreEventoMapper mapper;

    public EventoDTOResponse toEventoDTOResponse(Evento ev){
        EventoDTOResponse evDTOResp = new EventoDTOResponse();
        evDTOResp.setId(ev.getId());
        evDTOResp.setData(ev.getData());
        evDTOResp.setOra(ev.getOra());
        evDTOResp.setDescrizione(ev.getDescrizione());
        evDTOResp.setIdLuogo(ev.getLuogo().getId());
        evDTOResp.setIdManifestazione(ev.getManifestazione().getId());
        List<Long> listaIdPrezzoSettoreEvento = ev.getPrezziSettoreEvento().stream().map(PrezzoSettoreEvento::getId).toList();
        evDTOResp.setIdPrezzoSettoreEvento(listaIdPrezzoSettoreEvento);
        return evDTOResp;
    }

    public List<EventoDTOResponse> toEventoDTOResponseList(List<Evento> eventi){
        return eventi.stream().map(this::toEventoDTOResponse).toList();
    }

    public List<Evento> toEventoList(List<EventoMicroDTO> eventiManifestazione, Manifestazione m, List<Luogo> luoghi, List<PrezzoSettoreEventoMicroDTO> prezziMicroDTO) {

        return eventiManifestazione.stream().map(evento -> toEvento(evento,
                                                                    m,
                                                                    luoghi.stream().filter(l->l.getId()==evento.getIdLuogo()).findFirst().get()
                                                                    ,prezziMicroDTO.stream().filter(p->p.getIdEvento()==evento.getId()).toList()
                                                                    )).toList();
    }



    public Evento toEvento(EventoMicroDTO request, Manifestazione m, Luogo l, List<PrezzoSettoreEventoMicroDTO> pse){
        Evento e=new Evento();
        e.setId(request.getId());
        e.setData(request.getData());
        e.setOra(request.getOra());
        e.setDescrizione(request.getDescrizione());
        e.setCancellato(request.isCancellato());
        e.setManifestazione(m);
        m.addEvento(e);
        e.setLuogo(l);
        l.addEventi(e);
        List<PrezzoSettoreEvento> p=mapper.toList(pse,List.of(e),l.getSettori());
        e.setPrezziSettoreEvento(p);
        return e;
    }
}
