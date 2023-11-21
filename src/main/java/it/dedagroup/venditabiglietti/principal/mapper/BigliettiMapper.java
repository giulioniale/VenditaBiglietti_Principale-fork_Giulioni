package it.dedagroup.venditabiglietti.principal.mapper;

import it.dedagroup.venditabiglietti.principal.dto.response.*;
import it.dedagroup.venditabiglietti.principal.service.BigliettoServiceDef;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class BigliettiMapper implements GeneralCallService {
    private final BigliettoServiceDef bigliettoServiceDef;

    public StatisticheBigliettiDTOResponse createStatisticheRenditaBigliettiDTOResponse(String nomeManifestazione, String eventoDescrizione, String luogoRiga1, String nomeSettore, int bigliettiComprati, int settoreCapienza, double prezzoPSE, double prezzoBiglietto, int nBigliettiComprati){
        StatisticheBigliettiDTOResponse response = new StatisticheBigliettiDTOResponse();
        response.setNomeManifestazione(nomeManifestazione);
        response.setNomeEvento(eventoDescrizione);
        response.setNomeLuogo(luogoRiga1);
        response.setNomeSettore(nomeSettore);
        response.setBigliettiComprati(bigliettiComprati);
        response.setBigliettiTotali(settoreCapienza);
        response.setGuadagno(guadagnoEventoSettore(prezzoPSE,prezzoBiglietto,nBigliettiComprati));
        return response;
    }

    public StatisticheManifestazioneDTOResponse createStatisticheManifestazioneDTOResponse(String nomeManifestazione,
                                                                                           List<EventoMicroDTO> eventiManifestazione,
                                                                                           List<LuogoMicroDTO> luoghiEvento,
                                                                                           List<PrezzoSettoreEventoMicroDTO> psePerEventi,
                                                                                           Map<Long,List<BigliettoMicroDTO>> bigliettiPerPSE,
                                                                                           List<SettoreMicroDTO> settorePerPSE){
        StatisticheManifestazioneDTOResponse response = new StatisticheManifestazioneDTOResponse();
        List<StatisticheBigliettiDTOResponse> statisticheBigliettiDegliEventiDellaManifestazione = new ArrayList<>();
        for (int i = 0; i < eventiManifestazione.size(); i++) {
            StatisticheBigliettiDTOResponse statisticaPerSingoloSettore = new StatisticheBigliettiDTOResponse();
            //assegno il nome della manifestazione
            statisticaPerSingoloSettore.setNomeManifestazione(nomeManifestazione);
            //assegno il nome dell'evento
            statisticaPerSingoloSettore.setNomeEvento(eventiManifestazione.get(i).getDescrizione());

            //assegno il nome della via
            if (luoghiEvento.get(i).getId() == eventiManifestazione.get(i).getIdLuogo()){
                statisticaPerSingoloSettore.setNomeLuogo(luoghiEvento.get(i).getRiga1());
            }

            //assegno il nome del settore
            if (settorePerPSE.get(i).getId() == psePerEventi.get(i).getIdSettore()){
                statisticaPerSingoloSettore.setNomeSettore(settorePerPSE.get(i).getNome());
            }

            statisticaPerSingoloSettore.setBigliettiComprati(
                    bigliettoServiceDef.countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNull(psePerEventi.get(i).getId())
            );
            //setto i biglietti totali che corrispondono alla capienza del settore
            statisticaPerSingoloSettore.setBigliettiTotali(settorePerPSE.get(i).getPosti());
            //mi riprendo i due prezzi dei biglietti cioe' il prezzo normale e il prezzo "biglietti ultima ora"
            List<Double> prezziBigliettoPerSettore = bigliettoServiceDef.findDistinctPrezzoBigliettoByIdPrezzoSettoreEvento(psePerEventi.get(i).getId());
            //mi calcolo i biglietti comprati per il singolo prezzoSettoreEvento
            long bigliettiComprati = bigliettiPerPSE.get(psePerEventi.get(i).getId()).stream().filter(biglietto -> biglietto.getPrezzo() == prezziBigliettoPerSettore.get(1)).count();
            //mi calcolo i biglietti dell'ultima ora comprati per il singolo prezzoSettoreEvento
            long bigliettiUtimaOraComprati = bigliettiPerPSE.get(psePerEventi.get(i).getId()).stream().filter(biglietto -> biglietto.getPrezzo() == prezziBigliettoPerSettore.get(2)).count();
            //guadagno Biglietti per prezzoSettoreEvento
            double guadagnoBiglietti = guadagnoEventoSettore(psePerEventi.get(i).getPrezzo(), prezziBigliettoPerSettore.get(1),bigliettiComprati);
            //guadagno Biglietti ultima ora per prezzoSettoreEvento
            double guadagnoBigliettiUltimaOra = guadagnoEventoSettore(psePerEventi.get(i).getPrezzo(), prezziBigliettoPerSettore.get(2),bigliettiUtimaOraComprati);
            statisticaPerSingoloSettore.setGuadagno(
                    guadagnoBiglietti + guadagnoBigliettiUltimaOra
            );
            statisticheBigliettiDegliEventiDellaManifestazione.add(statisticaPerSingoloSettore);
        }
        response.setNomeManifestazione(nomeManifestazione);
        response.setRenditaEventiDellaManifestazione(statisticheBigliettiDegliEventiDellaManifestazione);
        return response;
    }

    private double guadagnoEventoSettore(double prezzoSettoreEvento, double prezzoBiglietto, long nBigliettiComprati){
        return (prezzoSettoreEvento + prezzoBiglietto) * nBigliettiComprati;
    }
}
