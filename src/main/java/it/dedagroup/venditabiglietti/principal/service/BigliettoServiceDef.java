package it.dedagroup.venditabiglietti.principal.service;

import it.dedagroup.venditabiglietti.principal.model.Biglietto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BigliettoServiceDef{
    public List<Biglietto> findBigliettiByIdUtente(Long idUtente);
}
