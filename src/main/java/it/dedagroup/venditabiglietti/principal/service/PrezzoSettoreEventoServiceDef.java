package it.dedagroup.venditabiglietti.principal.service;

import it.dedagroup.venditabiglietti.principal.model.PrezzoSettoreEvento;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PrezzoSettoreEventoServiceDef {

    PrezzoSettoreEvento findById(Long idPrezzoSettoreEvento);

    List<PrezzoSettoreEvento> findByEventiIds(List<Long> ids);
    List<PrezzoSettoreEvento> findAllByIdEvento(long idEvento);

}
