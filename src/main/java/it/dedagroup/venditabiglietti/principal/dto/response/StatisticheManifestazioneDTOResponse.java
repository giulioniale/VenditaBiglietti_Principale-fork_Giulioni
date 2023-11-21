package it.dedagroup.venditabiglietti.principal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticheManifestazioneDTOResponse {
    private String nomeManifestazione;
    private List<StatisticheBigliettiDTOResponse> renditaEventiDellaManifestazione;

}
