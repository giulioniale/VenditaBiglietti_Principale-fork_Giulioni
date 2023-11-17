package it.dedagroup.venditabiglietti.principal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticheBigliettiDTOResponse {
    private String nomeManifestazione;
    private String nomeEvento;
    private String nomeLuogo;
    private String nomeSettore;
    private int bigliettiComprati;
    private int bigliettiTotali;
    private double guadagno;
}
