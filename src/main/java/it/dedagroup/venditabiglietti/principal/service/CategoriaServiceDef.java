package it.dedagroup.venditabiglietti.principal.service;

import it.dedagroup.venditabiglietti.principal.dto.request.AggiungiCategoriaDtoRequest;
import it.dedagroup.venditabiglietti.principal.dto.response.CategoriaMicroDTO;

public interface CategoriaServiceDef {
	
	   void aggiungiCategoria(AggiungiCategoriaDtoRequest request); 
	   CategoriaMicroDTO findById(long id);
}
