package it.dedagroup.venditabiglietti.principal.mapper;

import it.dedagroup.venditabiglietti.principal.dto.response.*;
import it.dedagroup.venditabiglietti.principal.model.*;
import it.dedagroup.venditabiglietti.principal.service.BigliettoServiceDef;
import it.dedagroup.venditabiglietti.principal.service.GeneralCallService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class BigliettiMapper {
    private final BigliettoServiceDef bigliettoServiceDef;

    public Biglietto toBiglietto(BigliettoMicroDTO bDTO, List<PrezzoSettoreEvento> prezziSettoreEvento, Utente u){
        Biglietto b=new Biglietto();
        if(u!=null)b.setUtente(u);
        b.setId(bDTO.getId());
        b.setCancellato(bDTO.isCancellato());
        b.setPrezzo(bDTO.getPrezzo());
        b.setVersion(bDTO.getVersion());
        b.setDataAcquisto(bDTO.getDataAcquisto());
        b.setNSeriale(bDTO.getSeriale());
        b.setPrezzoSettoreEvento(prezziSettoreEvento.stream().filter(p->p.getId()== bDTO.getIdPrezzoSettoreEvento()).findFirst().orElse(null));
        b.getPrezzoSettoreEvento().addBiglietto(b);
        return b;
    }

    public List<Biglietto> toBigliettoList(List<BigliettoMicroDTO> bDTO,List<PrezzoSettoreEvento>prezzoSettoreEventos,List<Utente> clienti){
        return bDTO.stream().map(b->toBiglietto(b,prezzoSettoreEventos,clienti==null||clienti.isEmpty()?null:clienti.stream().filter(c->c.getId()==b.getIdUtente()).findFirst().orElse(null))).toList();
    }

    public List<Biglietto> toBigliettoList(List<BigliettoMicroDTO> bDTO,List<PrezzoSettoreEvento> prezzoSettoreEventos) {
        return toBigliettoList(bDTO,prezzoSettoreEventos,null);
    }

    public List<Biglietto> toBigliettoList(List<BigliettoMicroDTO>bDTO, List<Evento> eventi,String s){
        List<PrezzoSettoreEvento> appoggio=new ArrayList<>();
        eventi.forEach(e->appoggio.addAll(e.getPrezziSettoreEvento()));
        return toBigliettoList(bDTO,appoggio);
    }
    /*
    public StatisticheManifestazioneDTOResponse createStatisticheManifestazioneDTOResponse(String nomeManifestazione,
                                                                                           List<EventoMicroDTO> eventiManifestazione,
                                                                                           List<LuogoMicroDTO> luoghiEvento,
                                                                                           List<PrezzoSettoreEventoMicroDTO> psePerEventi,
                                                                                           Map<Long,List<BigliettoMicroDTO>> bigliettiPerPSE,
                                                                                           List<SettoreMicroDTO> settorePerPSE){

        StatisticheManifestazioneDTOResponse response = new StatisticheManifestazioneDTOResponse();
        response.setNomeManifestazione(nomeManifestazione);

        List<StatisticheBigliettiDTOResponse> statisticheBigliettiDegliEventiDellaManifestazione = new ArrayList<>();
        for (int i = 0; i < eventiManifestazione.size(); i++) {
            StatisticheBigliettiDTOResponse statisticaPerSingoloSettore = new StatisticheBigliettiDTOResponse();

            statisticaPerSingoloSettore.setNomeManifestazione(nomeManifestazione);
            statisticaPerSingoloSettore.setNomeEvento(eventiManifestazione.get(i).getDescrizione());

            List<SettoriPerSingoloLuogo> profittoDeiSettoriPerEvento = settoriLuogo(luoghiEvento.get(i),psePerEventi,settorePerPSE);



            long bigliettiComprati = profittoDeiSettoriPerEvento.stream().mapToLong(SettoriPerSingoloLuogo::getBigliettiComprati).sum();
            long bigliettiUtimaOraComprati = profittoDeiSettoriPerEvento.stream().mapToLong(SettoriPerSingoloLuogo::getBigliettiUtimaOra).sum();
            double guadagnoBiglietti = profittoDeiSettoriPerEvento.stream().mapToDouble(SettoriPerSingoloLuogo::getGuadagnoBiglietti).sum();
            double guadagnoBigliettiUltimaOra = profittoDeiSettoriPerEvento.stream().mapToDouble(SettoriPerSingoloLuogo::getGuadagnoBigliettiUltimaOra).sum();

            statisticaPerSingoloSettore.setSettoriPerLuogo(profittoDeiSettoriPerEvento);
            statisticaPerSingoloSettore.setBigliettiComprati(bigliettiComprati);
            statisticaPerSingoloSettore.setBigliettiUtimaOra(bigliettiUtimaOraComprati);
            statisticaPerSingoloSettore.setBigliettiTotali(settorePerPSE.get(i).getPosti());
            statisticaPerSingoloSettore.setGuadagno(guadagnoBiglietti);
            statisticaPerSingoloSettore.setGuadagnoUltimaOra(guadagnoBigliettiUltimaOra);
            //TODO cambiare l'add
            statisticheBigliettiDegliEventiDellaManifestazione.add(statisticaPerSingoloSettore);
        }
        response.setNomeManifestazione(nomeManifestazione);
        response.setProfittoEventiDellaManifestazione(statisticheBigliettiDegliEventiDellaManifestazione);
        return response;
    }

    public SettoriPerSingoloLuogo toSettoriPerSingoloLuogo(Luogo luogoEvento , List<PrezzoSettoreEventoMicroDTO> pseLista, List<SettoreMicroDTO> settori){
        SettoriPerSingoloLuogo settoriPerSingoloLuogo = new SettoriPerSingoloLuogo();
        settoriPerSingoloLuogo.setViaLuogo(luogoEvento.getRiga1());
        settoriPerSingoloLuogo.setBigliettiTotali(bigliettoServiceDef.countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNullAndPrezzo(psePerEvento.getId(),prezziBigliettoPerSettore.get(0)));
        pseLista.forEach(psePerEvento ->{
            settoriPerSingoloLuogo.setViaLuogo(luogoEvento.getRiga1());
            settoriPerSingoloLuogo.setSettore(settoreNelLuogo.getNome());
            List<Double> prezziBigliettoPerSettore = bigliettoServiceDef.findDistinctPrezzoBigliettoByIdPrezzoSettoreEvento(psePerEvento.getId());
            int quantitaBiglietti = ;
            int quantitaBigliettiUltimaOra = bigliettoServiceDef.countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNullAndPrezzo(psePerEvento.getId(),prezziBigliettoPerSettore.get(1));
            double guadagnoBiglietti = (prezziBigliettoPerSettore.get(0) + psePerEvento.getPrezzo()) * quantitaBiglietti;
            double guadagnoBigliettiUltimaOra = (prezziBigliettoPerSettore.get(1) + psePerEvento.getPrezzo()) * quantitaBiglietti;
            settoriPerSingoloLuogo.setBigliettiComprati(quantitaBiglietti);
            settoriPerSingoloLuogo.setBigliettiUtimaOra(quantitaBigliettiUltimaOra);
            settoriPerSingoloLuogo.setGuadagnoBiglietti(guadagnoBiglietti+guadagnoBigliettiUltimaOra);
            listaDiSettori.add(settoriPerSingoloLuogo);
        }
    }

    private int calcolaBiglietti(List<PrezzoSettoreEventoMicroDTO> pseLista,List<SettoreMicroDTO> settori){
        int prezzo=0;
        for(PrezzoSettoreEventoMicroDTO p:pseLista){
            settori.stream().filter(s->s.getIdLuogo()==p.ge)
        }
    }

    private double guadagnoEventoSettore(double prezzoSettoreEvento, double prezzoBiglietto, long nBigliettiComprati){
        return (prezzoSettoreEvento + prezzoBiglietto) * nBigliettiComprati;
    }

    private List<SettoriPerSingoloLuogo> settoriLuogo(LuogoMicroDTO luogoEvento ,List<PrezzoSettoreEventoMicroDTO> pseLista, List<SettoreMicroDTO> settore){
        List<SettoriPerSingoloLuogo> listaDiSettori = new ArrayList<>();
        List<SettoreMicroDTO> settoreNelLuogoEvento = settore.stream().filter(s -> s.getIdLuogo() == luogoEvento.getId()).toList();
        settoreNelLuogoEvento.forEach(settoreNelLuogo ->{
            );
        });
        return listaDiSettori;
    }

     */
}
