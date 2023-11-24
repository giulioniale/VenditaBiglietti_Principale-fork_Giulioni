package it.dedagroup.venditabiglietti.principal.serviceimpl;

import it.dedagroup.venditabiglietti.principal.dto.response.LuogoMicroDTO;
import it.dedagroup.venditabiglietti.principal.model.Luogo;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;
import it.dedagroup.venditabiglietti.principal.service.LuogoServiceDef;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LuogoServiceImpl implements LuogoServiceDef, GeneralCallService {

    private final String pathLuogo = "http://localhost:8088/luogo";
    @Override
    public List<Luogo> trovaTuttiILuoghi() {
        String mioPath = pathLuogo + "/find/all";
        Luogo [] luoghi = callGet(mioPath, null, null, Luogo[].class);
        return List.of(luoghi);
    }

    @Override
    public LuogoMicroDTO findById(long idLuogo) {
        String mioPath=pathLuogo+"/find/id/"+idLuogo;
        return callGet(mioPath,null,idLuogo,LuogoMicroDTO.class);
    }
}

