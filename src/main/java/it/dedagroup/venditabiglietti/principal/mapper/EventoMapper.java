package it.dedagroup.venditabiglietti.principal.mapper;

import it.dedagroup.venditabiglietti.principal.dto.response.EventoDTOResponse;
import it.dedagroup.venditabiglietti.principal.model.Evento;
import it.dedagroup.venditabiglietti.principal.model.PrezzoSettoreEvento;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventoMapper {

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
}
