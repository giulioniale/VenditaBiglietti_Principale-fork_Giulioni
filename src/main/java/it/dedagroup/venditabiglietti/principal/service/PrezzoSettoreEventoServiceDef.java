package it.dedagroup.venditabiglietti.principal.service;

import it.dedagroup.venditabiglietti.principal.model.PrezzoSettoreEvento;
import org.springframework.stereotype.Service;

@Service
public interface PrezzoSettoreEventoServiceDef {

    PrezzoSettoreEvento findById(Long idPrezzoSettoreEvento);
}
