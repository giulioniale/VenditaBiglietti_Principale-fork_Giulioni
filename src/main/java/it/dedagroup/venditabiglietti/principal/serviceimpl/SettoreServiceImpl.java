package it.dedagroup.venditabiglietti.principal.serviceimpl;

import org.springframework.stereotype.Service;

import it.dedagroup.venditabiglietti.principal.dto.request.AggiungiSettoreDtoRequest;
import it.dedagroup.venditabiglietti.principal.model.Settore;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;
import it.dedagroup.venditabiglietti.principal.service.SettoreServiceDef;

@Service
public class SettoreServiceImpl implements SettoreServiceDef, GeneralCallService {

	@Override
	public void aggiungiSettore(AggiungiSettoreDtoRequest dto) {
		String path="http://localhost:8083/settore/aggiungiSettore";
		callPost(path, null, dto, Void.class);
	}

}
