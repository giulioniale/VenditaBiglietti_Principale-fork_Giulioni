package it.dedagroup.venditabiglietti.principal.serviceimpl;

import it.dedagroup.venditabiglietti.principal.dto.request.AddBigliettoDTORequest;
import it.dedagroup.venditabiglietti.principal.dto.request.ModifyBigliettoDTORequest;
import it.dedagroup.venditabiglietti.principal.dto.response.BigliettoMicroDTO;
import it.dedagroup.venditabiglietti.principal.service.BigliettoServiceDef;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static it.dedagroup.venditabiglietti.principal.util.BigliettoUtilPath.*;

@Service
public class BigliettoServiceImpl implements BigliettoServiceDef, GeneralCallService {
    @Override
    public BigliettoMicroDTO saveBiglietto(AddBigliettoDTORequest biglietto) {
        return callPost("",null,null,BigliettoMicroDTO.class);
    }

    @Override
    public BigliettoMicroDTO modifyBiglietto(ModifyBigliettoDTORequest biglietto) {
        return callPost("",null,null,BigliettoMicroDTO.class);
    }

    @Override
    public void deleteByBiglietto(long id_biglietto) {
        callPost("",null,null,Void.class);
    }

    @Override
    public BigliettoMicroDTO findById(long id_biglietto) {
        return callGet("",null,null,BigliettoMicroDTO.class);
    }

    @Override
    public List<BigliettoMicroDTO> findAll() {
        return callGetForList("",null,null,BigliettoMicroDTO[].class);
    }

    @Override
    public BigliettoMicroDTO findByIdAndIdUtente(long id_biglietto, long id_utente) {
        return callGet("",null,null,BigliettoMicroDTO.class);
    }

    @Override
    public BigliettoMicroDTO findBySeriale(String seriale) {
        return callGet("",null,null,BigliettoMicroDTO.class);
    }

    @Override
    public List<BigliettoMicroDTO> findAllByPrezzoIsGreaterThanEqual(double prezzo) {
        return callGetForList("",null,null,BigliettoMicroDTO[].class);
    }

    @Override
    public List<BigliettoMicroDTO> findAllByPrezzoIsLessThanEqual(double prezzo) {
        return callGetForList("",null,null,BigliettoMicroDTO[].class);
    }

    @Override
    public List<BigliettoMicroDTO> findAllByIdUtente(long id_utente) {
        return callGetForList("",null,null,BigliettoMicroDTO[].class);
    }

    @Override
    public List<BigliettoMicroDTO> findAllByDataAcquisto(LocalDate dataAcquisto) {
        return callGetForList("",null,null,BigliettoMicroDTO[].class);
    }

    @Override
    public BigliettoMicroDTO findByIdAndIdPrezzoSettoreEvento(long id_biglietto, long id_prezzoSettoreEvento) {
        return callGet("",null,null,BigliettoMicroDTO.class);
    }

    @Override
    public List<BigliettoMicroDTO> findAllByIdPrezzoSettoreEventoOrderByPrezzoAsc(long id_prezzoSettoreEvento) {
        return callGetForList(BIGLIETTO_PATH + "/find/all/prezzo-settore-evento/id" + id_prezzoSettoreEvento, null, null, BigliettoMicroDTO[].class);
    }

    @Override
    public int countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNull(long id_prezzoSettoreEvento) {
        return callGet(BIGLIETTO_PATH+"/count/data-acquisto/not-null/prezzo-settore-evento/id/"+id_prezzoSettoreEvento,null,null,Integer.class);
    }

    @Override
    public List<Double> findDistinctPrezzoBigliettoByIdPrezzoSettoreEvento(long id_prezzoSettoreEvento) {
        return callGetForList("",null,null,Double[].class);
    }
    @Override
    public int countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNullAndPrezzo(long id_prezzoSettoreEvento, double prezzo) {
        //return callGet(BIGLIETTO_PATH+"/count/data-acquisto/not-null/prezzo-settore-evento/id/"+id_prezzoSettoreEvento,null,null,Integer.class);
        return 0;
    }

    @Override
    public List<BigliettoMicroDTO> findAllByIdPrezzoSettoreEventoIn(List<Long> idsPrezzoSettoreEvento) {
        return null;
    }
}
