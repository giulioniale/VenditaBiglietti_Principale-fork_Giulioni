package it.dedagroup.venditabiglietti.principal.serviceimpl;

import it.dedagroup.venditabiglietti.principal.model.PrezzoSettoreEvento;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;
import it.dedagroup.venditabiglietti.principal.service.PrezzoSettoreEventoServiceDef;

public class PrezzoSettoreEventoServiceImpl implements PrezzoSettoreEventoServiceDef, GeneralCallService {
    @Override
    public PrezzoSettoreEvento findById(Long idPrezzoSettoreEvento) {
        String path = "http://localhost:8086/prezzi-settore-evento/"+ idPrezzoSettoreEvento;
        return callGet(path, idPrezzoSettoreEvento, PrezzoSettoreEvento.class);
    }
}
