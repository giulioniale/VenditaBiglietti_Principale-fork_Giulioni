package it.dedagroup.venditabiglietti.principal.dto.response;

import it.dedagroup.venditabiglietti.principal.model.Luogo;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class MostraEventiFuturiDTOResponse {


    private String nomeManifestazione;
    private String nomeEvento;
    private LocalDate dataEvento;
    private String luogoEventoRiga1;
    private String riga2;
    private String comune;
    private String cap;
    private String provincia;
    private LocalTime orarioEvento;
    private int PostiDisponibili;

}
