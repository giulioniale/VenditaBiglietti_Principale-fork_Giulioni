package it.dedagroup.venditabiglietti.principal.mapper;

import it.dedagroup.venditabiglietti.principal.dto.response.*;
import it.dedagroup.venditabiglietti.principal.facade.VenditoreFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class BigliettiMapper {
    private final VenditoreFacade venditoreFacade;
    public StatisticheBigliettiDTOResponse createStatisticheRenditaBigliettiDTOResponse(String nomeManifestazione, String eventoDescrizione, String luogoRiga1, String nomeSettore, int bigliettiComprati, int settoreCapienza, double guadagno){
        StatisticheBigliettiDTOResponse response = new StatisticheBigliettiDTOResponse();
        response.setNomeManifestazione(nomeManifestazione);
        response.setNomeEvento(eventoDescrizione);
        response.setNomeLuogo(luogoRiga1);
        response.setNomeSettore(nomeSettore);
        response.setBigliettiComprati(bigliettiComprati);
        response.setBigliettiTotali(settoreCapienza);
        response.setGuadagno(guadagno);
        return response;
    }

    public StatisticheManifestazioneDTOResponse createStatisticheManifestazioneDTOResponse(String nomeManifestazione,
                                                                                           List<EventoMicroDTO> eventiManifestazione,
                                                                                           List<LuogoMicroDTO> luoghiEvento,
                                                                                           List<PrezzoSettoreEventoMicroDTO> psePerEventi,
                                                                                           List<BigliettoMicroDTO> bigliettiPerPSE,
                                                                                           List<SettoreMicroDTO> settorePerPSE){
        StatisticheManifestazioneDTOResponse response = new StatisticheManifestazioneDTOResponse();
        response.setNomeManifestazione(nomeManifestazione);
        List<StatisticheBigliettiDTOResponse> listaDiRenditaBigliettiDTOResponse = new ArrayList<>();
        for (int i = 0; i < eventiManifestazione.size(); i++){
            int bigliettiComprati = venditoreFacade.quantitaBigliettiComprati(psePerEventi.get(i).getId());
            StatisticheBigliettiDTOResponse renditaBigliettiDTOResponse = createStatisticheRenditaBigliettiDTOResponse(
                    nomeManifestazione,
                    eventiManifestazione.get(i).getDescrizione(),
                    luoghiEvento.get(i).getRiga1(),
                    settorePerPSE.get(i).getNome(),
                    bigliettiComprati,
                    settorePerPSE.get(i).getPosti(),
                    venditoreFacade.guadagnoEventoSettore(psePerEventi.get(i).getPrezzo(),bigliettiPerPSE.get(i).getPrezzo(),bigliettiComprati));
            listaDiRenditaBigliettiDTOResponse.add(renditaBigliettiDTOResponse);
        }
        response.setRenditaEventiDellaManifestazione(listaDiRenditaBigliettiDTOResponse);
        return response;
    }


}
