package it.dedagroup.venditabiglietti.principal.serviceimpl;

import it.dedagroup.venditabiglietti.principal.model.Biglietto;
import it.dedagroup.venditabiglietti.principal.service.BigliettoServiceDef;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BigliettoServiceImpl implements BigliettoServiceDef, GeneralCallService {

    @Override
    public List<Biglietto> findBigliettiByIdUtente(Long idUtente) {
        String path = "http://localhost:8087/find/all/utente/id/"+ idUtente;
        return callGetForList(path, idUtente, Biglietto[].class);
    }
}
