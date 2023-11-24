package it.dedagroup.venditabiglietti.principal.facade;

import it.dedagroup.venditabiglietti.principal.dto.request.AggiungiUtenteDTORequest;
import it.dedagroup.venditabiglietti.principal.dto.request.LoginDTORequest;
import it.dedagroup.venditabiglietti.principal.dto.response.*;
import it.dedagroup.venditabiglietti.principal.mapper.EventoMapper;
import it.dedagroup.venditabiglietti.principal.mapper.UtenteMapper;
import it.dedagroup.venditabiglietti.principal.model.*;
import it.dedagroup.venditabiglietti.principal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneralFacade implements GeneralCallService{

    @Autowired
    ManifestazioneServiceDef mServ;
    @Autowired
    UtenteServiceDef uServ;
    @Autowired
    EventoServiceDef eServ;
    @Autowired
    LuogoServiceDef lServ;
    @Autowired
    EventoMapper evMap;
    @Autowired
    UtenteMapper uMap;
    @Autowired
    PrezzoSettoreEventoServiceDef pseServ;
    @Autowired
    BigliettoServiceDef bServ;
    @Autowired
    SettoreServiceDef settServ;

    private final String pathEvento="http://localhost:8081/evento";

    public void registrazioneCliente(AggiungiUtenteDTORequest req){
        Utente uNew =uMap.toUtenteCliente(req);
        uServ.aggiungiUtente(uNew);
    }
    // TODO valutare sui cicli nestati se fare le lambda
    public List<MostraEventiFuturiDTOResponse> trovaEventiFuturiConBiglietti(){
        List<EventoMicroDTO> eventiFuturi = eServ.trovaEventiFuturi();
        List<MostraEventiFuturiDTOResponse> listaEventiFuturiResponse=new ArrayList<>();
        for (EventoMicroDTO eventoDto :eventiFuturi) {
            MostraEventiFuturiDTOResponse response=new MostraEventiFuturiDTOResponse();
            List<PrezzoSettoreEvento> listaPsePerEvento =pseServ.findAllByIdEvento(eventoDto.getId());
            List<SettoreMicroDTO> listaSettoriPerEvento=settServ.findAllByIdLuogo(lServ.findById(eventoDto.getIdLuogo()).getId());
            int capienzaSettore=0;
            int bigliettiVenduti=0;
            response=evMap.mostraEventiFuturiResponseBuilder(eventoDto,
                    lServ.findById(eventoDto.getIdLuogo()),
                    mServ.findById(eventoDto.getIdManifestazione()));
            capienzaSettore=listaSettoriPerEvento.stream().mapToInt(SettoreMicroDTO::getPosti).sum();
            bigliettiVenduti=listaPsePerEvento.stream().mapToInt(s->bServ.countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNull(s.getId())).sum();
            response.setPostiDisponibili(capienzaSettore-bigliettiVenduti);
            listaEventiFuturiResponse.add(response);
        }
        return listaEventiFuturiResponse;

    }
    public List<Luogo> trovaTuttiILuoghi(){
        List<Luogo> luoghi = lServ.trovaTuttiILuoghi();
        return luoghi;
    }


    public String login(LoginDTORequest request){
        return uServ.login(request);
    }

}
