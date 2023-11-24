package it.dedagroup.venditabiglietti.principal.service;

import java.util.List;

import it.dedagroup.venditabiglietti.principal.model.Evento;


public interface EventoServiceDef {

	void eliminaEvento(long id);

    public List<Evento> trovaEventiFuturi();
}
