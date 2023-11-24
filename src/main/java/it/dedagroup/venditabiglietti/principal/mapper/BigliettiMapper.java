package it.dedagroup.venditabiglietti.principal.mapper;

import it.dedagroup.venditabiglietti.principal.dto.response.*;
import it.dedagroup.venditabiglietti.principal.model.*;
import it.dedagroup.venditabiglietti.principal.service.BigliettoServiceDef;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class BigliettiMapper {
    public Biglietto toBiglietto(BigliettoMicroDTO bDTO, List<PrezzoSettoreEvento> prezziSettoreEvento, Utente u){
        Biglietto b=new Biglietto();
        if(u!=null)b.setUtente(u);
        b.setId(bDTO.getId());
        b.setCancellato(bDTO.isCancellato());
        b.setPrezzo(bDTO.getPrezzo());
        b.setVersion(bDTO.getVersion());
        b.setDataAcquisto(bDTO.getDataAcquisto());
        b.setNSeriale(bDTO.getSeriale());
        b.setPrezzoSettoreEvento(prezziSettoreEvento.stream().filter(p->p.getId()== bDTO.getIdPrezzoSettoreEvento()).findFirst().orElse(null));
        b.getPrezzoSettoreEvento().addBiglietto(b);
        return b;
    }

    public List<Biglietto> toBigliettoList(List<BigliettoMicroDTO> bDTO,List<PrezzoSettoreEvento>prezzoSettoreEventos,List<Utente> clienti){
        return bDTO.stream().map(b->toBiglietto(b,prezzoSettoreEventos,clienti==null||clienti.isEmpty()?null:clienti.stream().filter(c->c.getId()==b.getIdUtente()).findFirst().orElse(null))).toList();
    }

    public List<Biglietto> toBigliettoList(List<BigliettoMicroDTO> bDTO,List<PrezzoSettoreEvento> prezzoSettoreEventos) {
        return toBigliettoList(bDTO,prezzoSettoreEventos,null);
    }

    public List<Biglietto> toBigliettoList(List<BigliettoMicroDTO>bDTO, List<Evento> eventi,String s){
        List<PrezzoSettoreEvento> appoggio=new ArrayList<>();
        eventi.forEach(e->appoggio.addAll(e.getPrezziSettoreEvento()));
        return toBigliettoList(bDTO,appoggio);
    }
}
