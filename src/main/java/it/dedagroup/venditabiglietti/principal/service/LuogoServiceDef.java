package it.dedagroup.venditabiglietti.principal.service;

import it.dedagroup.venditabiglietti.principal.dto.response.LuogoMicroDTO;
import it.dedagroup.venditabiglietti.principal.model.Luogo;

import java.util.List;

public interface LuogoServiceDef {

    List<Luogo> trovaTuttiILuoghi();

    LuogoMicroDTO findById(long idLuogo);
}
