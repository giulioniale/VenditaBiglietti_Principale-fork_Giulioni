package it.dedagroup.venditabiglietti.principal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoSettorePseDTORequest {
    private String username;
    private String descrizioneEvento;
    private long idPrezzoSettoreEvento;
    private double prezzoBiglietto;
}
