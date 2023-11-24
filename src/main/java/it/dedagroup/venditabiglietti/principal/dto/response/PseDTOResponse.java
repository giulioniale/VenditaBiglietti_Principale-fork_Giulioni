package it.dedagroup.venditabiglietti.principal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PseDTOResponse {
    private long id;
    private String descrizioneEvento;
    private String nomeSettore;
    private double prezzo;
}
